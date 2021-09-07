package quinzical.setting;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import quinzical.functionality.BackgroundTask;
import quinzical.functionality.ControllerHolder;
import quinzical.functionality.FlagArray;
import quinzical.playModule.PlayQuestionController;
import quinzical.practiceModule.PracticeQuestionController;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static java.lang.Integer.parseInt;
import static quinzical.main.Main.voiceSpeed;

/**
 * This class handles the display/controlling functions for the
 * Settings Screen which is able to change the speed of festival
 */
public class SettingsController implements Initializable {
    @FXML
    private Button backBtn;
    @FXML
    private Button plusBtn;
    @FXML
    private Button minusBtn;
    @FXML
    private Button testVoiceBtn;
    @FXML
    private TextField speed;

    private String prevLocation;
    private String clue;
    private String ans;
    private String bracket;
    private String lines;
    private int attempts;
    private String clueWinnings;
    private FlagArray flags;
    private ControllerHolder holder;
    private String category;
    private int timeLeft;


    /**
     * On click action for GO BACK button, return to the previous screen
     * @param event
     * @throws IOException
     */
    public void setBackBtn(ActionEvent event) throws IOException {
        Scene scene;
        FXMLLoader loader = new FXMLLoader();
        if(prevLocation.equals("PracticeQuestionScreen")){
            loader.setLocation(getClass().getResource("/quinzical/practiceModule/PracticeQuestionScreen.fxml"));

            Parent tableViewScreen = loader.load();
            scene = new Scene(tableViewScreen);

            PracticeQuestionController controller = loader.getController();
            controller.initDataFromSettings(clue, ans, bracket, attempts);

        } else {
            loader.setLocation(getClass().getResource("/quinzical/playModule/PlayQuestionScreen.fxml"));

            Parent tableViewScreen = loader.load();
            scene = new Scene(tableViewScreen);

            PlayQuestionController controller = loader.getController();
            controller.initData(lines, clueWinnings, flags, holder, category);
            controller.initTimer(timeLeft);

        }
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();

    }

    /**
     * On click action for plus button, increases the speed
     */
    public void setPlusBtn(){
        //Get the current speed set in text field
        int currentSpeed = parseInt(speed.getText());

        if(currentSpeed<9){
            //Increase this speed by a certain amount and display to new speed value
            voiceSpeed = voiceSpeed - 0.2;
            speed.setText(String.valueOf(currentSpeed + 1));
        }
    }

    /**
     * On click action for minus button, decreases the speed
     */
    public void setMinusBtn(){

        //Get the current speed set in text field
        int currentSpeed = parseInt(speed.getText());

        //Checks for if decreasing would produce an invalid speed amount
        if(currentSpeed > 1) {
            //Decrease the speed by a certain amount and display to new speed value
            voiceSpeed = voiceSpeed + 0.2;
            speed.setText(String.valueOf(currentSpeed - 1));
        }
    }


    /**
     * On click action for Test button, plays a sample running the new voice speed
     */
    public void setTestVoiceBtn() {
        BackgroundTask task = new BackgroundTask("(Parameter.set 'Duration_Stretch " + voiceSpeed + ")",
                "(SayText \"Hello, this is the voice sample\")");
        Thread helper = new Thread(task);
        helper.start();
    }

    /**
     * Initialises data in the class
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        speed.setDisable(true);
        double value = (1 - (voiceSpeed / 0.2)/10) * 10; // Use voiceSpeed to calculate the current value
        int speedText = (int)value;
        speed.setText(String.valueOf(speedText));
    }

    /**
     * Initiates the Setting screen with the data from the previous screen in order to set the values to the same state
     * when returning back to the Practice screen.
     * @param prevLocation  The location of the previous screen
     * @param clue          The clue being asked to the user in the Practice module
     * @param ans           The correct answer to the clue
     * @param bracket       The question type of the clue
     * @param attempts      The number of attempts the user has had at the clue
     */
    public void initPracticeQuestionData(String prevLocation, String clue, String ans, String bracket, int attempts){
        this.prevLocation = prevLocation;
        this.clue = clue;
        this.ans = ans;
        this.bracket = bracket;
        this.attempts = attempts;
    }

    /**
     * Initiates the Settings screen with the data from the previous screen in order to set the values to the same state
     * when returning back to the Play screen.
     * @param prevLocation  The location of the previous screen
     * @param winning       The amount of money the clue is worth
     * @param flags         The clues that have been completed
     * @param holder        The
     * @param line          The entire line for the clue from the txt file
     * @param category      The category the clue is sourced from
     * @param timeLeft      The amount of time the user has left for answering the clue
     */
    public void initPlayQuestionData(String prevLocation, String winning, FlagArray flags, ControllerHolder holder,
                                     String line, String category, int timeLeft){
        this.prevLocation = prevLocation;
        clueWinnings = winning;
        this.flags = flags;
        this.holder = holder;
        lines = line;
        this.category = category;
        this.timeLeft = timeLeft;
    }
}