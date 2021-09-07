package quinzical.other;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static quinzical.main.Main.runCommand;

/**
 * This class handles the display/controlling functions for the main menu where the
 * user is able to go into the play module, practice module or quit the application.
 * @author Wayne and Kayla
 */
public class MenuController {
    @FXML
    private Button play;
    @FXML
    private Button practice;
    @FXML
    private Button quit;

    /** Displays the appropriate screen according to whether the user has started a game
     * @param event         On click action for Play button
     * @throws IOException  If screen cannot be loaded
     */
    public void setPlay(ActionEvent event) throws IOException {
        Parent tableViewScreen;
        File f = new File("progress/categories.txt");
        if (f.exists() && !f.isDirectory()) {
            // If already progressing through a game go straight to the categories screen
            tableViewScreen = FXMLLoader.load(getClass().getResource("/quinzical/playModule/PlayCategoriesScreen.fxml"));
            Scene playCatScene = new Scene(tableViewScreen);

            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.setScene(playCatScene);
            window.show();
        } else {
            // Else go to start screen for instructions if it is a new game
            tableViewScreen = FXMLLoader.load(getClass().getResource("/quinzical/playModule/StartScreen.fxml"));
            Scene guideScene = new Scene(tableViewScreen);

            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.setScene(guideScene);
            window.show();
        }
    }

    /**
     * Displays the categories screen for the Practice module
     * @param event         On click action for Practice button
     * @throws IOException  If screen cannot be loaded
     * */
    public void setPractice(ActionEvent event) throws IOException {
        Parent tableViewScreen = FXMLLoader.load(getClass().getResource("/quinzical/practiceModule/PracticeCategoriesScreen.fxml"));
        Scene practiceScene = new Scene(tableViewScreen);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(practiceScene);
        window.show();
    }

    /**
     * Displays the Quit screen
     * @param event         On click action for Quit button
     * @throws IOException  If screen cannot be loaded
     */
    public void setQuit(ActionEvent event) throws IOException {
        Parent tableViewScreen = FXMLLoader.load(getClass().getResource("/quinzical/other/QuitScreen.fxml"));
        Scene quitScene = new Scene(tableViewScreen);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(quitScene);
        window.show();
    }

}