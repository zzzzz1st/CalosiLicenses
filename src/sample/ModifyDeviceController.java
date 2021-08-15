package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

public class ModifyDeviceController implements Initializable {
    @FXML
    private Text status;

    @FXML
    private RadioButton checkpv;

    @FXML
    private RadioButton checksn;

    @FXML
    private RadioButton checkmn;

    @FXML
    private RadioButton checkhostid;

    @FXML
    private TextField modn;

    @FXML
    private TextField hostn;

    @FXML
    private TextField snn;

    @FXML
    private TextField pvn;

    @FXML
    private Label hostidmodwin;

    @FXML
    private Button cpv;

    @FXML
    private Button csn;

    @FXML
    private Button cmodn;

    @FXML
    private Button chostn;

    private MySQLConn sqlconnection = null;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        radioBinder();
    }

    public void setSqlconnection(MySQLConn sql){
        sqlconnection = sql;
    }

    public void setHostidmodwin(String str){
        hostidmodwin.setText(str);
    }

    public void radioBinder (){
        pvn.disableProperty().bind(checkpv.selectedProperty().not());
        snn.disableProperty().bind(checksn.selectedProperty().not());
        modn.disableProperty().bind(checkmn.selectedProperty().not());
        hostn.disableProperty().bind(checkhostid.selectedProperty().not());
        cpv.disableProperty().bind(checkpv.selectedProperty().not());
        csn.disableProperty().bind(checksn.selectedProperty().not());
        cmodn.disableProperty().bind(checkmn.selectedProperty().not());
        chostn.disableProperty().bind(checkhostid.selectedProperty().not());

    }

    public void cpvClicked (){
        sqlconnection.updateDispositivo("puntoV", pvn.getText().toUpperCase(Locale.ROOT), hostidmodwin.getText().toUpperCase(Locale.ROOT));
        status.setText("Punto vendita cambiato !");
    }

    public void csnClicked (){
        sqlconnection.updateDispositivo("serialN", snn.getText().toUpperCase(Locale.ROOT), hostidmodwin.getText().toUpperCase(Locale.ROOT));
        status.setText("Serial number cambiato !");
    }

    public void cmodnClicked (){
        sqlconnection.updateDispositivo("Model", modn.getText().toUpperCase(Locale.ROOT), hostidmodwin.getText().toUpperCase(Locale.ROOT));
        status.setText("Modello cambiato !");
    }

    public void chostnClicked (){
        sqlconnection.updateDispositivo("HostID", modn.getText().toUpperCase(Locale.ROOT), hostidmodwin.getText().toUpperCase(Locale.ROOT));
        status.setText("HostID cambiato !");
    }

}
