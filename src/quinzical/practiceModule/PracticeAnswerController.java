package quinzical.practiceModule;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * This class handles the display/controlling functions for the Answer screen where the
 * user is informed that they are either correct or incorrect in the Practice module.
 * @author Wayne and Kayla
 */
public class PracticeAnswerController {
    //Shared elements
    @FXML
    private Button homeIcon;
    @FXML
    private Button continueBtn;
    @FXML
    private Button backHomeBtn;

    //Correct screen elements
    @FXML
    private ImageView attemptCoverOne;
    @FXML
    private ImageView attemptCoverTwo;

    // Incorrect screen elements
    @FXML
    private Text answerText;
    @FXML
    private TextArea questionText;

    /**
     * Changes back to the categories screen
     * On click action for CONTINUE button
     * @throws IOException     If screen cannot be loaded
     */
    public void setContinueBtn() throws IOException {
        Parent tableViewScreen = FXMLLoader.load(getClass()
                .getResource("/quinzical/practiceModule/PracticeCategoriesScreen.fxml"));
        Scene practiceScene = new Scene(tableViewScreen);

        Stage window = (Stage)(continueBtn.getScene().getWindow());
        window.setScene(practiceScene);
        window.show();
    }

    /**
     * Returns to the menu screen
     * @param event         On click action for the home icon and BACK HOME button
     * @throws IOException  If screen cannot load
     */
    public void changeScreenHome(ActionEvent event) throws IOException {
        Parent tableViewScreen = FXMLLoader.load(getClass().getResource("/quinzical/other/MenuScreen.fxml"));
        Scene menuScene = new Scene(tableViewScreen);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(menuScene);
        window.show();
    }

    /**
     * On initialization of the correct screen, will display the number of attempts they tried to answer
     * @param attempts The current attempt the user is on for answering the clue
     */
    public void correctInitData(int attempts){
        attemptCoverOne.setOpacity(1);
        attemptCoverTwo.setOpacity(0);
        if(attempts == 2) {
            attemptCoverTwo.setOpacity(1);
        }
    }

    /**
     * On start up, will be passed the clue and ans to the question they played in order to display to user
     * @param clue  The clue just attempted
     * @param ans   The answer to the clue just attempted
     */
    public void incorrectInitData(String clue, String ans){
        String answer = ans.toUpperCase();
        if(answer.contains("/")){
            String ansSplit = ans.split("/")[0];
            answerText.setText(ansSplit);
        } else {
            answerText.setText(answer);
        }
        questionText.setPromptText(clue);
    }

}