package quinzical.playModule;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import quinzical.functionality.BackgroundTask;
import quinzical.functionality.ControllerHolder;
import quinzical.functionality.FlagArray;
import quinzical.functionality.checkAnswer;
import quinzical.setting.SettingsController;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

import static quinzical.main.Main.*;

/**
 * This class handles the display/controlling functions for the
 * user to answer the chosen clue for in the games module.
 * @author Wayne and Kayla
 */
public class PlayQuestionController implements Initializable {
    @FXML
    private Button homeIcon;
    @FXML
    private Button settingIcon;
    @FXML
    private Button speakerIcon;
    @FXML
    private Button submitBtn;
    @FXML
    private Button dontKnowBtn;
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
    private Text questionStarterText;
    @FXML
    private Text money;
    @FXML
    private TextField response;
    @FXML
    private Text clueCategory;
    @FXML
    private Text clueValue;
    @FXML
    private Label timerValue;

    private String clue;
    private String ans;
    private String bracket;
    private String clueWinnings;
    private ControllerHolder holder;
    private FlagArray flags;
    private String lines;
    private String category;
    private boolean isTimerStarted = false;
    private BackgroundTask speech;
    private SimpleStringProperty seconds = new SimpleStringProperty("60");
    private Timer timer = new Timer();
    private int timeLeft = 60;
    private TimerTask task;

    /**
     * Controls the timer counting down in seconds
     */
    private void timer() {
        task = new TimerTask() {
            public void run() {
                Platform.runLater(() -> {
                    timeLeft--;
                    seconds.setValue(String.valueOf(timeLeft));

                    if (timeLeft == 0) {
                        try {
                            timeUp();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        };
    }

    /**
     * On initialization of the category screen, will display money and set up timer
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        timer();
        // Sets the text 'money' to the amount in the file
        money.setText(getWinningsAmount());
        //Creates a connection between the timerValue displayed on the GUI and the seconds counting down
        timerValue.textProperty().bind(seconds);
    }

    /**
     * Cancels all background tasks for when the screen is about to change
     * @throws IOException catch any failures when attempting to stop the speech
     */
    public void stopEverything() throws IOException {
        // Stop the timer
        timer.cancel();
        task.cancel();
        // Stop speech if running
        speech.stop();
    }

    /**
     * When anything is typed into the text input, timer begins
     */
    public void clickOnInput(){
        // Check if it hasn't already begun
        if(!isTimerStarted) {
            // Counts for every 1 second
            timer.scheduleAtFixedRate(task, 0, 1000);
            isTimerStarted = true;
        }
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
     * On action for Submit button, changes to particular screen dependent on if correct or not
     * @throws IOException Catch any failures when loading screen
     */
    public void setSubmitBtn() throws IOException {
        // Stop the timer and speech if speaking
        stopEverything();

        // Check if their answer matches the real answer
        String theirAnswer = response.getText();
        checkAnswer check = new checkAnswer();
        Boolean correct = check.isAnsCorrect(theirAnswer, ans);

        // Display the appropriate screen to match if their answer was correct or not
        Stage window = (Stage)(submitBtn.getScene().getWindow());
        if(correct){
            window.setScene(getCorrectScreen());
        } else {
            window.setScene(getIncorrectScreen());
        }
        window.show();
    }

    /**
     * In order to lead to the correct screen and initialise the data needed for that screen
     * @return The scene for the correct screen
     * @throws IOException Catch any failures when loading screen
     */
    private Scene getCorrectScreen() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/quinzical/playModule/PlayCorrectScreen.fxml"));

        Parent tableViewScreen = loader.load();
        Scene correctScreen = new Scene(tableViewScreen);

        String money = getWinningsAmount();
        money = "" + (Integer.parseInt(money) + Integer.parseInt(clueWinnings));
        holder.setText(money);

        PlayAnswerController controller = loader.getController();
        controller.initCorrectData(clueWinnings, money, flags);

        return correctScreen;
    }

    /**
     * In order to lead to incorrect screen and initialise the data needed for that screen
     * @return The scene for the correct screen
     * @throws IOException Catch any failures when loading screen
     */
    public Scene getIncorrectScreen() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/quinzical/playModule/PlayIncorrectScreen.fxml"));

        Parent tableViewScreen = loader.load();
        Scene incorrectScene = new Scene(tableViewScreen);

        PlayAnswerController controller = loader.getController();
        controller.initIncorrectData(flags, ans);
        return incorrectScene;
    }

    /** On click action for Setting Icon button, change to incorrect screen
     * @throws IOException Catch any failures when loading screen
     */
    public void setDontKnowBtn() throws IOException {
        // Stop the timer and speech if speaking
        stopEverything();

        Stage window = (Stage)(dontKnowBtn.getScene().getWindow());
        window.setScene(getIncorrectScreen());
        window.show();
    }

    /**
     * Change to menu screen
     * @param event On click action for GO BACK button
     * @throws IOException Catch any failures when loading screen
     */
    public void setHomeIcon(ActionEvent event) throws IOException {
        // Stop the timer and speech if speaking
        stopEverything();

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/quinzical/playModule/LeavePageScreen.fxml"));

        Parent tableViewScreen = loader.load();
        Scene leaveScene = new Scene(tableViewScreen);

        LeavePageController controller = loader.getController();
        controller.initData(clueWinnings, flags, holder, lines, category, timeLeft);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(leaveScene);
        window.show();
    }

    /**
     * Change to setting screen
     * @param event On click action for setting icon button
     * @throws IOException Catch any failures when loading screen
     */
    public void setSettingIcon(ActionEvent event) throws IOException {
        // Stop the timer
        timer.cancel();
        task.cancel();
        //Stop speech if running
        speech.stop();

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/quinzical/setting/SettingsScreen.fxml"));

        Parent tableViewScreen = loader.load();
        Scene settingsScene = new Scene(tableViewScreen);

        SettingsController controller = loader.getController();
        String scene = "PlayQuestionScreen";
        controller.initPlayQuestionData(scene, clueWinnings, flags, holder, lines, category, timeLeft);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(settingsScene);
        window.show();
    }

    /**
     * On click action for speaker button
     * Repeats the clue speech
     */
    public void speak() {
        speech = new BackgroundTask("(Parameter.set 'Duration_Stretch " + voiceSpeed + ")",
                "(SayText \"" + clue + "\")");
        Thread helper = new Thread(speech);
        helper.start();
    }

    /**
     * For each macron button, input macron letter accordingly into the text field
     */
    public void setMacron(ActionEvent event){
        String input = response.getText(); //get the current text that is in the text field
        String macronLetter = ((Button)event.getSource()).getText(); //retrieve macron letter according to btn clicked
        response.setText(input + macronLetter); //update text field
        response.requestFocus(); //place the cursor back in the text field
        response.end();
    }

    /**
     * Initialise the play question screen with the information of the clue chosen
     * @param line      The line from the text file of this clue
     * @param winning   The value of the clue is worth
     * @param flags     The current tracking of completed clues
     * @param holder    The Play Categories holder
     * @param category  The current category being played
     */
    public void initData(String line, String winning, FlagArray flags, ControllerHolder holder, String category){
        lines = line;
        select();
        clueWinnings = winning;
        questionStarterText.setText(bracket + "...");
        this.flags = flags;
        this.holder = holder;
        this.category = category;

        //Set the clue and category text
        clueValue.setText(winning);
        clueCategory.setText(category);

        //Speaks the clue
        speak();
        if(!isTimerStarted) {
            // Counts for every 1 second
            timer.scheduleAtFixedRate(task, 0, 1000);
            isTimerStarted = true;
        }

    }

    /**
     * Initialises the timer if it has begun but is paused to do another task first
     * @param timeLeft The time remaining to answer question
     */
    public void initTimer(int timeLeft){
        seconds.setValue(String.valueOf(timeLeft));
        this.timeLeft = timeLeft;
    }

    /**
     * For when the timer has counted down to zero
     */
    public void timeUp() throws IOException {
        seconds.setValue("00");
        setDontKnowBtn();
    }

    /**
     * Takes the line from list of clues and separates the clue, bracket and answer into their own variables
     */
    public void select() {
        String line = lines;

        clue = line.split(";")[0];
        bracket = line.substring(line.indexOf('(', 0)+1, line.indexOf(')', 0)).trim();
        ans = line.substring(line.indexOf(')',0)+1).trim().toLowerCase();

    }

}