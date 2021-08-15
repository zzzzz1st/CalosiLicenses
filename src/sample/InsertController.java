package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.util.Locale;

public class InsertController {
    @FXML
    private Text status;

    @FXML
    private TextField inscodel;

    @FXML
    private TextField insnumord;

    @FXML
    private Label inshostid;

    @FXML
    private TextField dellic;

    private MySQLConn sqlconnection = null;

    public void setInshostid(String str) {
        inshostid.setText(str);
    }

    public void setSqlconnection(MySQLConn sql) {
        sqlconnection = sql;
    }

    public void inserisciButtonClicked() {
        String hostid = inshostid.getText().toUpperCase(Locale.ROOT);
        if (!hostid.isEmpty()) {
            sqlconnection.insertLicenza(hostid,
                    inscodel.getText().toUpperCase(Locale.ROOT),
                    insnumord.getText());
            status.setText("Licenza inserita !");
        } else {
            ErrorsController errorsController = new ErrorsController();
            errorsController.display("HostID field is empty !");
        }
    }

    public void cancellaButtonClicked() {
        String hostid = inshostid.getText().toUpperCase(Locale.ROOT);
        if (!hostid.isEmpty()) {
            sqlconnection.deleteLicenza(hostid,
                    dellic.getText().toUpperCase(Locale.ROOT));
            status.setText("Licenza cancellata !");
        } else {
            ErrorsController errorsController = new ErrorsController();
            errorsController.display("HostID field is empty !");
        }
    }
}
