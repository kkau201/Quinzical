package quinzical.playModule;

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
import quinzical.functionality.FlagArray;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static quinzical.main.Main.*;

/**
 * This class handles the display/controlling functions for the Answer screen where the
 * user is informed that they are either correct or incorrect in the Play module.
 * @author Wayne and Kayla
 */
public class PlayAnswerController implements Initializable {
    @FXML
    private Button homeIcon;
    @FXML
    private Button nextBtn;
    @FXML
    private Text winningsText;
    @FXML
    private Text money;
    @FXML
    private Text answerText;

    private String totalWinnings;
    private FlagArray flags;

    /**
     * On initialization will set up the winnings in top right
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        money.setText(getWinningsAmount());
    }

    /**
     * Switches screen appropriately according to progress of game (method for the Next button)
     * @throws IOException  If screen cannot load
     */
    public void setNextBtn() throws IOException {
        Stage window = (Stage)(nextBtn.getScene().getWindow());

        flags.checkInternational();
        if(flags.isFirstInternational()) {
            // Check if international section is unlocked
            window.setScene(loadInternational());
        } else if(!flags.containTrue()) {
            // Checks if all clues are complete and loads reward screen
            window.setScene(loadReward());
        } else {
            // If not all completed, takes back to category screen to choose another clue
            Parent tableViewScreen = FXMLLoader.load(getClass().getResource("/quinzical/playModule/PlayCategoriesScreen.fxml"));
            Scene catScene = new Scene(tableViewScreen);
            window.setScene(catScene);
        }
        window.show();
    }

    /**
     * Loads reward screen
     * @return              The reward scene
     * @throws IOException  If screen cannot load
     */
    private Scene loadReward() throws IOException {
        Parent tableViewScreen = FXMLLoader.load(getClass().getResource("/quinzical/other/RewardScreen.fxml"));
        Scene rewardScene = new Scene(tableViewScreen);
        return rewardScene;
    }

    /**
     * Loads international screen
     * @return              The international scene
     * @throws IOException  If screen cannot load
     */
    private Scene loadInternational() throws IOException {
        Parent tableViewScreen = FXMLLoader.load(getClass().getResource("/quinzical/playModule/InternationalScreen.fxml"));
        Scene unlockScene = new Scene(tableViewScreen);
        return unlockScene;
    }

    /**
     * Returns to the menu screen
     * @param event         On click action for the home icon button
     * @throws IOException  If screen cannot load
     */
    public void setHomeIcon(ActionEvent event) throws IOException {
        Parent tableViewScreen = FXMLLoader.load(getClass().getResource("/quinzical/other/MenuScreen.fxml"));
        Scene menuScene = new Scene(tableViewScreen);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(menuScene);
        window.show();
    }

    /**
     * Initialises the screen to pass through the amount won and the flags of what questions have been answered
     * @param winnings          The amount won from the clue just attempted
     * @param currentWinnings   The current winnings of the user
     * @param flags             Tracks progress of which clues have been completed and are able to be answered next
     * */
    public void initCorrectData(String winnings, String currentWinnings, FlagArray flags){
        //Display the winnings of that clue
        winningsText.setText("+" + winnings + "!");
        //Update global variables
        totalWinnings = currentWinnings;
        this.flags = flags;
        //Update winnings txt file to the current amount the user has won
        runCommand("echo " + totalWinnings + " > ./winnings/currentWinnings.txt");

    }

    /**
     * Initialise the screen to pass through data from question screen which updates that the question has been completed
     * @param flags Tracks the progress of which clues have been completed and are able to be answered next
     * @param ans   The answer to the clue just attempted
     */
    public void initIncorrectData(FlagArray flags, String ans) {
        this.flags = flags;
        // Display the answer to the clue (ensures if the clue has multiple answers, it displays only one)
        ans = ans.toUpperCase();
        if(ans.contains("/")){
            String ansSplit = ans.split("/")[0];
            answerText.setText(ansSplit);
        } else {
            answerText.setText(ans);
        }
    }

}
