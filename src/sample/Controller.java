package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.TextFieldTreeCell;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Locale;

public class Controller{

    @FXML
    private Text status1;

    @FXML
    private Text status2;

    @FXML
    private Text status3;

    @FXML
    private TextField impnamei;

    @FXML
    private TextField modhostid;

    @FXML
    private TextField hostid;

    @FXML
    private TextField sn;

    @FXML
    private TextField model;

    @FXML
    private TextField norder;

    @FXML
    private TextField pvselect;

    @FXML
    private TextField hostidselect;

    @FXML
    private TextArea insertlic;

    @FXML
    private TreeView<String> treepv;

    @FXML
    void keyPressed(KeyEvent event) throws SQLException {
        switch (event.getCode()){
            case ENTER:
                treeEnterPressed();
                break;
            case SPACE:
                treeSpacePressed();
                break;
        }
    }

    private final String[] args = new String[8];
    private final MySQLConn sqlconnection = new MySQLConn();

    public void configButtonClicked(){
        args[0] = impnamei.getText().toUpperCase(Locale.ROOT);
        args[1] = hostid.getText().toUpperCase(Locale.ROOT);
        args[2] = sn.getText().toUpperCase(Locale.ROOT);
        args[3] = model.getText().toUpperCase(Locale.ROOT);
        args[4] = insertlic.getText().toUpperCase(Locale.ROOT);
        args[5] = norder.getText();
        if(!args[0].isEmpty() && !args[1].isEmpty()) {
            System.out.println("Clicked !!");
            String[] licenses = args[4].split("\n");
            sqlconnection.insertDispositivo(args[1], args[2], args[3], args[0]);
            if (!args[4].isEmpty()&&!args[5].isEmpty()) {
                for (String str : licenses) {
                    sqlconnection.insertLicenza(args[1], str, args[5]);
                }
            } else {
                ErrorsController errorsController = new ErrorsController();
                errorsController.display("License field is empty ! The device was inserted without licenses");
            }
            status1.setText("Fatto !");
        } else {
            ErrorsController errorsController = new ErrorsController();
            errorsController.display("Necessary fields are empty !");
        }
    }
    public void insertButtonClicked(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("insert.fxml"));
            Parent child1 = loader.load();
            InsertController insertcontroller = loader.getController();
            insertcontroller.setInshostid(modhostid.getText());
            insertcontroller.setSqlconnection(sqlconnection);
            Stage stage =new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Inserimento E Cancellazione");
            stage.setScene(new Scene(child1));
            stage.getIcons().add(new Image(getClass().getResourceAsStream("software-license.png")));
            stage.setResizable(false);
            status2.setText(" Ineserimento e cancellazione le licenze");
            stage.showAndWait();
        }catch (Exception e){System.out.println("Insert does not open");}
    }

    public void modifyDeviceButtonClicked(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("modifydevice.fxml"));
            Parent child2 = loader.load();
            ModifyDeviceController moddevcontroller = loader.getController();
            moddevcontroller.setHostidmodwin(modhostid.getText());
            moddevcontroller.setSqlconnection(sqlconnection);
            Stage stage =new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Modifia Dispositivo");
            stage.setScene(new Scene(child2));
            stage.getIcons().add(new Image(getClass().getResourceAsStream("software-license.png")));
            stage.setResizable(false);
            status2.setText("Modificare i dispositivi");
            stage.showAndWait();
        }catch (Exception e){System.out.println("modifyDev does not open");}
    }

    public void cancellaDispositivoClicked() {
        String tmphostid = modhostid.getText().toUpperCase(Locale.ROOT);
        if (!tmphostid.isEmpty()) {
            sqlconnection.deleteDispositivo(tmphostid);
            status2.setText("Dispositivo cancellato !");
        } else {
            ErrorsController errorsController = new ErrorsController();
            errorsController.display("HostID field is empty !");
        }
    }

    public void vaiPvClicked() throws SQLException {
        TreeItem<String> root = new TreeItem<>();
        treepv.setShowRoot(false);
        treepv.setEditable(true);
        treepv.setCellFactory(TextFieldTreeCell.forTreeView());
        treepv.setRoot(root);
        ResultSet resultSet = sqlconnection.selectByPV(pvselect.getText().toUpperCase(Locale.ROOT));
        String hostid = "";
        String data = "";
        if(resultSet.next()) {
            hostid = resultSet.getString("HostID");
            data = resultSet.getString("CodL") + " | " + resultSet.getString("NOrdine");
        }
            TreeItem<String> branch = makeBranch(hostid, root);
            makeBranch(data, branch);
        while (resultSet.next()){
            String newhostid = resultSet.getString("HostID");
            if (!newhostid.equals(hostid)) {
                hostid = newhostid;
                branch = makeBranch(hostid, root);
            }
            data = resultSet.getString("CodL") + " | " + resultSet.getString("NOrdine");
            makeBranch(data, branch);
        }
        status3.setText("Inserito i dati (Punto Vendita)");
    }

    public void vaiHostIDClicked () throws SQLException {
        TreeItem<String> root = new TreeItem<>();
        TreeItem<String> licenses;
        treepv.setShowRoot(false);
        treepv.setEditable(true);
        treepv.setCellFactory(TextFieldTreeCell.forTreeView());
        treepv.setRoot(root);
        String hostidtoupper = hostidselect.getText().toUpperCase(Locale.ROOT);
        ResultSet resultSet = sqlconnection.selectByHostID(hostidtoupper);
        if (resultSet.next()) {
            String serialn = "Serial Number : " + resultSet.getString("SerialN");
            String model = "Model : " + resultSet.getString("Model");
            String puntov = "Punto Vendita : " + resultSet.getString("PuntoV");
            makeBranch(serialn, root);
            makeBranch(model, root);
            makeBranch(puntov, root);

        }
        licenses = makeBranch("Licenze Associate", root);
        resultSet = sqlconnection.selectByHostIDLicenses(hostidtoupper);
        while(resultSet.next()) {
            String codl = resultSet.getString("CodL");
            String nordine = resultSet.getString("NOrdine");
            makeBranch(codl + " | "+nordine, licenses);
        }
        status3.setText("Inserito i dati (HostID)");
    }

        public void treeEnterPressed () throws SQLException {
            TreeItem<String> root = new TreeItem<>();
            treepv.setShowRoot(false);
            treepv.setEditable(true);
            treepv.setCellFactory(TextFieldTreeCell.forTreeView());
            treepv.setRoot(root);
            ResultSet resultSet = sqlconnection.selectDistinctPV();
            while(resultSet.next()) {
                String pv = resultSet.getString("PuntoV");
                makeBranch(pv, root);

            }
            status3.setText("I punti di vendita salvati");

    }

    public void treeSpacePressed()throws SQLException {
        TreeItem<String> root = new TreeItem<>();
        treepv.setShowRoot(false);
        treepv.setEditable(true);
        treepv.setCellFactory(TextFieldTreeCell.forTreeView());
        treepv.setRoot(root);
        ResultSet resultSet = sqlconnection.selectAllHostIDs();
        while(resultSet.next()) {
            String pv = resultSet.getString("HostID");
            makeBranch(pv, root);

        }
        status3.setText("Gli HostID salvati");

    }

    public TreeItem<String> makeBranch(String title, TreeItem<String> parent){
        TreeItem<String> item = new TreeItem<>(title);
        parent.getChildren().add(item);
        return item;
    }
}
