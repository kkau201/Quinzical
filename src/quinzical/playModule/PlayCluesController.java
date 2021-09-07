package quinzical.playModule;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import quinzical.functionality.ControllerHolder;
import quinzical.functionality.FlagArray;

import java.io.IOException;
import java.net.URL;
import java.util.*;

import static quinzical.main.Main.*;

/**
 * This class handles the display/controlling functions for the Clue screen where the
 * user is able to choose from 5 clues of the category chosen in the play module.
 * @author Wayne and Kayla
 */
public class PlayCluesController implements Initializable {
    @FXML
    private Button homeIcon;
    @FXML
    private Button settingIcon;
    @FXML
    private Button backBtn;
    @FXML
    private Text money;
    @FXML
    private GridPane gridPaneForButtons; //Grid pane for clue buttons - consists of 5 vertical grids

    private String lines;
    private FlagArray flags;
    private int categoryIndex;
    private String category;
    private ControllerHolder holder;
    // List of monetary values for clue buttons
    private List<String> cluesList = Arrays.asList("$100", "$200", "$300", "$400", "$500");


    /* On initialization of the category screen, will set up buttons from the set categories list */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        categoryIndex = Integer.parseInt(getCurrentCategory());

        String[] status = getButtonStatus();
        Boolean clueFlag = true;

        // Loops through the list of clues to create a button for each one
        for (int i = 0; i < cluesList.size(); i++) {
            Button btn = new Button();
            btn.setText(cluesList.get(i)); // Set the text of the button to the string in the list
            // Set width and height of the button to that of the grid pane
            btn.setMinWidth(gridPaneForButtons.getPrefWidth());
            btn.setMinHeight(gridPaneForButtons.getPrefHeight());

            // Disables non lowest level button or button that haven't been answered
            if (!status[i].equals("true")) {
                btn.setDisable(true);

            } else {
                clueFlag = false;
            }

            // Sets buttons that have been completed to be grey and crossed out
            if (clueFlag) {
                btn.setId("completedBtn");
            }

            // When any of the buttons are clicked, will lead to the question screen for the particular clue
            btn.setOnAction((e -> {
                try {
                    this.setChangeToQuestion(e);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }));
            gridPaneForButtons.add(btn, 0, i);
        }

        // Sets the text 'money' to the amount in the file
        money.setText(getWinningsAmount());
    }

    /** Displays question screen with correct information passed through
     * @param event         On click action when a clue button is pressed
     * @throws IOException  If screen cannot load
     */
    public void setChangeToQuestion(ActionEvent event) throws IOException {
        int clueIndex = Integer.parseInt(((Button) event.getSource()).getText().substring(1));
        clueIndex = clueIndex / 100 - 1;
        flags.setFlag(categoryIndex, clueIndex, "false");
        if (clueIndex != 4) {
            flags.setFlag(categoryIndex, clueIndex + 1, "true");
        }

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/quinzical/playModule/PlayQuestionScreen.fxml"));

        Parent tableViewScreen = loader.load();

        Scene questionScene = new Scene(tableViewScreen);
        PlayQuestionController controller = loader.getController();
        String winning = ((Button) event.getSource()).getText().substring(1);

        controller.initData(lines, winning, flags, holder, category);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(questionScene);
        window.show();
    }

    /**
     * Returns to categories screen
     * @param event         On click action for GO BACK button
     * @throws IOException  If screen cannot load
     */
    public void setBackButton(ActionEvent event) throws IOException {
        Parent tableViewScreen = FXMLLoader.load(getClass()
                .getResource("/quinzical/playModule/PlayCategoriesScreen.fxml"));
        Scene playCatScene = new Scene(tableViewScreen);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(playCatScene);
        window.show();
    }


    /**
     * Change screen to menu
     * @param event         On click action for home icon button
     * @throws IOException  If screen cannot load
     */
    public void setHomeIcon(ActionEvent event) throws IOException {
        Parent tableViewScreen = FXMLLoader.load(getClass().getResource("/quinzical/other/MenuScreen.fxml"));
        Scene menuScene = new Scene(tableViewScreen);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(menuScene);
        window.show();
    }

    /**
     * Initialises screen by passing the screen the information of that category
     * @param line      The lines from the chosen categories text file
     * @param flags     Flags of which clues have been completed
     * @param holder    Holds the play categories controller
     * @param category  The category chosen by user
     */
    public void initData(String line, FlagArray flags, ControllerHolder holder, String category) {
        lines = line;
        this.holder = holder;
        this.flags = flags;
        this.category = category;
    }


}