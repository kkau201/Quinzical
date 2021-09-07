package quinzical.practiceModule;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.*;

/**
 * This class handles the display/controlling functions for the Categories screen where the
 * user is able to choose from all available categories in the Practice module.
 * @author Wayne and Kayla
 */
public class PracticeCategoriesController implements Initializable {
    @FXML
    private Button homeIcon;
    @FXML
    private Button backBtn;
    @FXML
    private VBox vBoxForButtons;

    private List<List<String>> categories;

    private String clue;
    private String ans;
    private String bracket;

    /**
     * On initialization of the category screen, will set up all buttons in a
     * scroll pane from the set categories list
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        readFileIntoArray();

        // Loops through the list of categories to create a button for each one
        for (List<String> category : categories) {
            Button btn = new Button();
            btn.setText(category.get(0));

            // When any of the buttons are clicked, will lead to the clue screen
            btn.setOnAction((e -> {
                try {
                    this.changeToPracticeQScreen(e);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }));

            vBoxForButtons.getChildren().add(btn);
        }
    }

    /**
     * Changes screen to play question based on the clue button pressed
     * @param event         On click of a clue button
     * @throws IOException  If screen cannot load
     */
    public void changeToPracticeQScreen(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/quinzical/practiceModule/PracticeQuestionScreen.fxml"));

        Parent tableViewScreen = loader.load();
        Scene clueScene = new Scene(tableViewScreen);

        PracticeQuestionController controller = loader.getController();
        String category = ((Button)event.getSource()).getText();
        // Loops to through category list to find the matching category
        String match = categories.get(0).get(0);
        int index = 0;
        while (!category.equals(match)){
            index++;
            match = categories.get(index).get(0);
        }

        //Generate a clue of that category
        select(index);

        //Set the controller to initialise the next screen
        controller.initData(clue, ans, bracket);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(clueScene);
        window.show();
    }

    /**
     * Display the menu screen
     * @param event         On click action for home icon and GO BACK button
     * @throws IOException  If screen cannot load
     */
    public void changeToHome(ActionEvent event) throws IOException {
        Parent tableViewScreen = FXMLLoader.load(getClass().getResource("/quinzical/other/MenuScreen.fxml"));
        Scene menuScene = new Scene(tableViewScreen);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(menuScene);
        window.show();
    }

    /**
     * This method reads in all categories and clues, storing appropriately in lists
     */
    public void readFileIntoArray(){
        categories = new ArrayList<>();

        File folder = new File("./categories");
        File[] files = folder.listFiles();
        try {
            for (int i = 0; i < Objects.requireNonNull(files).length; i++) { //loops through files in categories folder
                File file = files[i];
                if (file.length() != 0){ //checks if the file is empty, disregard empty files
                    List<String> category = new ArrayList<>();
                    category.add(file.getName());

                    // Reads each line of the text file
                    BufferedReader text = new BufferedReader(new FileReader(file));
                    String line;
                    while((line=text.readLine())!=null) {
                        category.add(line);
                    }
                    categories.add(category);
                    text.close();
                }
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    /** This method with take an index to specify the category chosen
     * and randomly chooses a clue from that list
     * @param index The index of the category chosen
     */
    public void select(int index) {
        //Chose a random number from between 0 and the number of clues
        Random random = new Random();
        int randomClueLine = random.nextInt(categories.get(index).size());

        //Cannot choose pos 0 of list as it is the title of the category
        if (randomClueLine == 0) {
            randomClueLine++;
        }

        //Store it in a string and separate into the clue, bracket and ans
        String line = categories.get(index).get(randomClueLine);
        clue = line.split(";")[0];
        bracket = line.substring(line.indexOf('(', 0)+1, line.indexOf(')', 0)).trim();
        ans = line.substring(line.indexOf(')',0)+1).trim().toLowerCase();

    }


}
