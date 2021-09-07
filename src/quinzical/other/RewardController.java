package quinzical.other;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static quinzical.main.Main.getWinningsAmount;

/**
 * This class handles the display/controlling functions for the Reward screen where the
 * user is able to see their winning score and rest the play module.
 * @author Wayne and Kayla
 */
public class RewardController implements Initializable {
    @FXML
    private Button settingIcon;
    @FXML
    private Button resetBtn;
    @FXML
    private Text winningsText;

    /**
     * On initialization of the category screen, will set up buttons from the set categories list
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Sets the text 'money' to the amount in the file
        winningsText.setText("$" + getWinningsAmount());
    }

    /**
     * Switches to the reset screen
     * @param event         On click action for RESET button
     * @throws IOException  If screen cannot be loaded
     */
    public void setResetBtn(ActionEvent event) throws IOException {
        Parent tableViewScreen = FXMLLoader.load(getClass().getResource("/quinzical/other/ResetScreen.fxml"));
        Scene resetScene = new Scene(tableViewScreen);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(resetScene);
        window.show();
    }
}
