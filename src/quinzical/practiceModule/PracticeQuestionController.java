package quinzical.practiceModule;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import quinzical.functionality.BackgroundTask;
import quinzical.setting.SettingsController;
import quinzical.functionality.checkAnswer;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static quinzical.main.Main.*;

/**
 * This class handles the display/controlling functions for the
 * user to answer the chosen clue for in the practice module.
 * @author Wayne and Kayla
 */
public class PracticeQuestionController implements Initializable {
    @FXML
    private Button homeIcon;
    @FXML
    private Button speakerIcon;
    @FXML
    private Button settingIcon;
    @FXML
    private Button submitBtn;
    @FXML
    private Text questionStarterText;
    @FXML
    private Text firstAttemptErrorText;
    @FXML
    private Text secondAttemptErrorText;
    @FXML
    private TextArea questionText;
    @FXML
    private TextField response;
    @FXML
    private Button macronA;
    @FXML
    private Button macronE;
    @FXML
    private Button macronI;
    @FXML
    private Button macronO;
    @FXML
    private Button macronU;
    @FXML
    private ImageView attemptCoverOne;
    @FXML
    private ImageView attemptCoverTwo;

    private Boolean correct = false;
    private String clue;
    private String ans;
    private String bracket;
    private int counter;

    /**
     * On initialization of the practice question screen, will set up resets the attempt data
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        counter = 0;
        firstAttemptErrorText.setOpacity(0);
        secondAttemptErrorText.setOpacity(0);
        attemptCoverOne.setOpacity(0);
        attemptCoverTwo.setOpacity(0);
    }

    /** Changes to particular screen dependent on if correct or not
     * to be completed on click action for SUBMIT button
     * @throws IOException  If screen cannot load
     */
    public void setSubmitBtn() throws IOException {
        counter++;

        // Retrieves their answer and checks it against the correct answer
        String theirAnswer = response.getText().toLowerCase();
        checkAnswer check;
        check = new checkAnswer();
        correct = check.isAnsCorrect(theirAnswer, ans);

        // If answer matched the clue then will change to Correct Screen
        Stage window = (Stage)(submitBtn.getScene().getWindow());
        if(correct) {
            window.setScene(getCorrectScreen());
            window.show();
        }

        // If wrong with check the attempt from the counter and act appropriately
        if(counter < 3){
            attemptChanges();
        } else {
            window.setScene(getIncorrectScreen());
            window.show();
        }
    }

    /**
     * Changes the screen variable to match the attempt the user is on
     */
    private void attemptChanges(){
        if(counter == 1){
            attemptCoverOne.setOpacity(1);
            firstAttemptErrorText.setOpacity(1);
            response.setText("");
        } else if(counter == 2){
            attemptCoverOne.setOpacity(1);
            attemptCoverTwo.setOpacity(1);
            firstAttemptErrorText.setOpacity(0);
            secondAttemptErrorText.setOpacity(1);
            response.setText(ans.substring(0,1));
        }
    }

    /**
     * Lead to correct screen and initialise the data needed for that screen
     * @return              The correct screen scene
     * @throws IOException  If screen cannot be loaded
     */
    private Scene getCorrectScreen() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/quinzical/practiceModule/PracticeCorrectScreen.fxml"));

        Parent tableViewScreen = loader.load();
        Scene correctScreen = new Scene(tableViewScreen);

        PracticeAnswerController controller = loader.getController();
        controller.correctInitData(counter);

        return correctScreen;
    }

    /**
     * Lead to incorrect screen and initialise the data needed for that screen
     * @return              The incorrect screen scene
     * @throws IOException  If screen cannot be loaded
     */
    public Scene getIncorrectScreen() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        // If answer does not match the clue then will change to Incorrect Screen
        loader.setLocation(getClass().getResource("/quinzical/practiceModule/PracticeIncorrectScreen.fxml"));

        Parent tableViewScreen = loader.load();
        Scene incorrectScene = new Scene(tableViewScreen);

        // Need to send through the clue and answer
        PracticeAnswerController controller = loader.getController();
        controller.incorrectInitData(clue, ans);
        return incorrectScene;
    }

    /**
     * Change to menu screen
     * @param event On click action for GO BACK button
     * @throws IOException Catch any failures when loading screen
     */
    public void setHomeIcon(ActionEvent event) throws IOException {
        Parent tableViewScreen = FXMLLoader.load(getClass().getResource("/quinzical/other/MenuScreen.fxml"));
        Scene menuScene = new Scene(tableViewScreen);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(menuScene);
        window.show();
    }

    /**
     * Change to setting screen
     * @param event On click action for setting icon button
     * @throws IOException Catch any failures when loading screen
     */
    public void setSettingIcon(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/quinzical/setting/SettingsScreen.fxml"));

        Parent tableViewScreen = loader.load();
        Scene settingsScene = new Scene(tableViewScreen);

        SettingsController controller = loader.getController();
        String scene = "PracticeQuestionScreen";
        controller.initPracticeQuestionData(scene, clue, ans, bracket, counter);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(settingsScene);
        window.show();
    }

    /**
     * If ENTER key is pressed, will call setSubmitBtn()
     * @param k A key event which indicates a keystroke has occurred
     * @throws IOException
     */
    @FXML
    private void setOnKeyPressed(KeyEvent k) throws IOException {
        if (k.getCode().toString().equals("ENTER")){
            setSubmitBtn();
        }
    }

    /**
     * On click action for speaker button
     * Repeats the clue speech
     */
    public void speak() {
        BackgroundTask task = new BackgroundTask(
                "(Parameter.set 'Duration_Stretch " + voiceSpeed + ")", "(SayText \"" + clue + "\")");
        Thread helper = new Thread(task);
        helper.start();
    }

    /**
     * For each macron button, input macron letter accordingly into the text field
     */
    public void setMacron(ActionEvent event){
        String input = response.getText(); //get the current text that is in the text field
        String macronLetter = ((Button)event.getSource()).getText(); //get the macron letter according to button clicked
        response.setText(input + macronLetter); //update text field
        response.requestFocus(); //place the cursor back in the text field
        response.end();
    }

    /**
     * Initialise the practice question screen with the information of the
     * clue randomly selected from the chosen category
     * @param clue      The current clue text
     * @param ans       The current clue's answer
     * @param bracket   The question starter text
     */
    public void initData(String clue, String ans, String bracket){
        this.clue = clue;
        this.ans = ans;
        this.bracket = bracket;
        questionStarterText.setText(bracket);
        questionText.setPromptText(this.clue);

        // Read the clue as the screen is loaded
        speak();
    }

    /** Initialises the screen back to the same clue and number of attempts
     * when continuing back from the settings screen
     * @param clue      The current clue text
     * @param ans       The current clue's answer
     * @param bracket   The question starter text
     * @param attempts  The number of attempts tried already
     */
    public void initDataFromSettings(String clue, String ans, String bracket,int attempts){
        counter = attempts;
        this.clue = clue;
        this.ans = ans;
        this.bracket = bracket;
        questionStarterText.setText(bracket);
        questionText.setPromptText(this.clue);

        // Read the clue as the screen is loaded
        speak();

        // Changes display of attempts accordingly
        if(counter == 1){
            attemptCoverOne.setOpacity(1);
            firstAttemptErrorText.setOpacity(1);
            response.setText("");
        } else if(counter == 2){
            attemptCoverOne.setOpacity(1);
            attemptCoverTwo.setOpacity(1);
            firstAttemptErrorText.setOpacity(0);
            secondAttemptErrorText.setOpacity(1);
            response.setText(this.ans.substring(0,1));
        }
    }

}