package quinzical.playModule;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * This class handles the display/controlling functions for the International screen where the
 * user is alerted that they have unlocked the bonus category.
 * @author Wayne and Kayla
 */
public class InternationalController {
    @FXML
    private Button continueBtn;

    /**
     * Return to the categories screen in Play module
     * @param event         On click action for continue button
     * @throws IOException  If screen cannot load
     */
    public void setContinueBtn(ActionEvent event) throws IOException {
        Parent tableViewScreen = FXMLLoader.load(getClass().getResource("/quinzical/playModule/PlayCategoriesScreen.fxml"));
        Scene menuScene = new Scene(tableViewScreen);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(menuScene);
        window.show();
    }
}