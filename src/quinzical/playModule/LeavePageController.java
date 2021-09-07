package quinzical.playModule;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import quinzical.functionality.ControllerHolder;
import quinzical.functionality.FlagArray;

import java.io.IOException;

/**
 * This class handles the display/controlling functions for the Leave Page screen where the
 * user is warned that if they return to the menu they clue cannot be played again.
 * @author Wayne and Kayla
 */
public class LeavePageController {
    @FXML
    private Button yesBtn;
    @FXML
    private Button noBtn;

    private FlagArray flags;
    private String clueWinnings;
    private ControllerHolder holder;
    private String lines;
    private String category;
    private int timeLeft;

    /**
     * Returns to menu screen
     * @param event         On click action for yes button
     * @throws IOException  If screen cannot load
     * */
    public void setYes(ActionEvent event) throws IOException {
        Parent tableViewScreen = FXMLLoader.load(getClass().getResource("/quinzical/other/MenuScreen.fxml"));
        Scene menuScene = new Scene(tableViewScreen);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(menuScene);
        window.show();
    }

    /**
     * Return to the play question screen to continue answering the current clue
     * @param event         On click action for no button
     * @throws IOException  If screen cannot load
     */
    public void setNo(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/quinzical/playModule/PlayQuestionScreen.fxml"));

        Parent tableViewScreen = loader.load();
        Scene scene = new Scene(tableViewScreen);

        PlayQuestionController controller = loader.getController();
        controller.initData(lines, clueWinnings, flags, holder, category);
        controller.initTimer(timeLeft);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    /**
     * Initiates the Settings screen with the data from the previous screen in order to set the values to the same state
     * when returning back to the Play screen.
     * @param winning       The amount of money the clue is worth
     * @param flags         The clues that have been completed
     * @param holder        The
     * @param line          The entire line for the clue from the txt file
     * @param category      The category the clue is sourced from
     * @param timeLeft      The amount of time the user has left for answering the clue
     */
    public void initData(String winning, FlagArray flags, ControllerHolder holder,
                         String line, String category, int timeLeft){
        clueWinnings = winning;
        this.flags = flags;
        this.holder = holder;
        lines = line;
        this.category = category;
        this.timeLeft = timeLeft;
    }
}