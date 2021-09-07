package quinzical.other;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

import static quinzical.main.Main.runCommand;

public class QuitController {
    @FXML
    private Button yesBtn;
    @FXML
    private Button noBtn;

    /**
     * Exit window for when yes button is clicked on
     */
    public void setYes() {
        // Gets handle of the stage and closes it
        runCommand("^C");
        Stage stage = (Stage) yesBtn.getScene().getWindow();
        stage.close();
    }

     /** Returns to menu screen
     * @param event         On click action for no button
     * @throws IOException  If screen cannot be loaded
     */
    public void setNo(ActionEvent event) throws IOException {
        Parent tableViewScreen = FXMLLoader.load(getClass().getResource("/quinzical/other/MenuScreen.fxml"));
        Scene menuScene = new Scene(tableViewScreen);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(menuScene);
        window.show();
    }
}