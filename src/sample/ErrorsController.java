package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ErrorsController {
    @FXML
    private Text errortext;

    @FXML
    private Button okbutton;

    public void display(String message) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("errors.fxml"));
            Parent child3 = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Error");
            stage.setScene(new Scene(child3));
            stage.initModality(Modality.APPLICATION_MODAL);
            ErrorsController errorsController = loader.getController();
            errorsController.errortext.setText(message);
            errorsController.okbutton.setOnAction(actionEvent -> stage.close());
            stage.getIcons().add(new Image(getClass().getResourceAsStream("software-license.png")));
            stage.setResizable(false);
            stage.showAndWait();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
