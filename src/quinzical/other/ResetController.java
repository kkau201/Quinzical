package quinzical.other;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import quinzical.functionality.FlagArray;

import java.io.*;

import static quinzical.main.Main.runCommand;

/**
 * This class handles the display/controlling functions for the Reset screen where the
 * user is able to reset the play module.
 * @author Wayne and Kayla
 */
public class ResetController {
    @FXML
    private Button yesBtn;
    @FXML
    private Button noBtn;

    private FlagArray flags;

    /**
     * Resets game and returns to menu screen
     * @param event         On click action for yes button
     * @throws IOException  If screen cannot be loaded
     */
    public void setYes(ActionEvent event) throws IOException {
        // Reset game by setting winnings to zero and removing progress files
        runCommand("echo " + "0" + " > ./winnings/currentWinnings.txt");
        runCommand("rm -r ./progress/categories.txt");
        runCommand("rm -r ./progress/flags.txt");

        // Return to menu screen
        Parent tableViewScreen = FXMLLoader.load(getClass().getResource("/quinzical/other/MenuScreen.fxml"));
        Scene menuScene = new Scene(tableViewScreen);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(menuScene);
        window.show();
    }

    /**
     * Returns to categories/reward screen
     * @param event         On click action for no button
     * @throws IOException  If screen cannot be loaded
     */
    public void setNo(ActionEvent event) throws IOException {
        Boolean check = gameProgress();

        if (check) {
            // Return to categories screen, if resetting during the game
            Parent tableViewScreen = FXMLLoader.load(getClass().getResource("/quinzical/playModule/PlayCategoriesScreen.fxml"));
            Scene catScreen = new Scene(tableViewScreen);

            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(catScreen);
            window.show();
        } else {
            // Return to reward screen, if resetting after the game is completed
            Parent tableViewScreen = FXMLLoader.load(getClass().getResource("/quinzical/other/RewardScreen.fxml"));
            Scene rewardScene = new Scene(tableViewScreen);

            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(rewardScene);
            window.show();
        }
    }

    /**
     * Checks if the game is in progress or completed
     * @return  A boolean where true is if the game is still in progress, false for completed
     */
    public Boolean gameProgress() {
        boolean check = false;
        File file = new File("progress/flags.txt");
        try {
            if (file.length() != 0) { // Checks if the file is empty, disregard empty files
                BufferedReader text = new BufferedReader(new FileReader(file));

                String line;
                while ((line = text.readLine()) != null) {
                    if (line.equals("true")) {
                        check = true;
                    }
                }
                text.close();
            }
        } catch(Exception e) {
            e.printStackTrace();
        }

        return check;
    }
}