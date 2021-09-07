package quinzical.playModule;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import quinzical.functionality.CategoryMaps;
import quinzical.functionality.ControllerHolder;
import quinzical.functionality.FlagArray;
import quinzical.functionality.clueList;

import java.io.*;
import java.net.URL;
import java.util.*;

import static quinzical.main.Main.*;

/**
 * This class handles the display/controlling functions for the Categories screen where the
 * user is able to choose from 5-6 categories in the play module.
 * @author Wayne and Kayla
 */
public class PlayCategoriesController implements Initializable {
    @FXML
    private Button homeIcon;
    @FXML
    private Button resetBtn;
    @FXML
    private Text money;
    @FXML
    private GridPane gridPaneForButtons; //Grid pane for category buttons - consists of 5 vertical grids
    @FXML
    private Button intBtn;
    @FXML
    private ImageView unlockArrow;

    private List<List<String>> categories;
    private List<String> internationalCategory;
    private List<String> categoriesList ;
    private clueList category;
    private FlagArray flags;
    private CategoryMaps map;

    /**
     * On initialization of the category screen, will set up buttons from the set categories list
     * */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        money.setText(getWinningsAmount()); // Set the Money displayed to the amount in the file
        categorySetUp();
        readFileIntoInternational();
        setUpBtns();
        internationalBtn();
    }

    /**
     * Sets up all necessary data required for the categories
     */
    private void categorySetUp() {
        readFile();
        File temp = new File("./progress/categories.txt");
        category = new clueList();
        if(!(temp.exists())){
            category.takeOut5Categories(categories.size());
        } else{
            category.continueProgress();
        }
        flags = new FlagArray();
        map = new CategoryMaps();
        categoriesList = getCatList();
    }

    /**
     * Checks if international button needs to be displayed or not
     */
    private void internationalBtn() {
        if(flags.checkInternational()){
            if(!flags.cateContainTrue(5)){
                intBtn.setDisable(true);
                intBtn.setOpacity(0.5);
                unlockArrow.setOpacity(0.0);
            } else{
                intBtn.setOpacity(1.0);
                intBtn.setDisable(false);
                unlockArrow.setOpacity(1.0);
            }
        } else {
            intBtn.setOpacity(0.0);
            intBtn.setDisable(true);
            unlockArrow.setOpacity(0.0);
        }
    }

    /**
     * Loops through the list of categories to create a button for each one
     */
    private void setUpBtns() {
        for(int i=0; i<categoriesList.size(); i++){
            Button btn = new Button();
            // Set the text of the button to the string in the list
            btn.setText(categoriesList.get(i));
            // Set width and height of the button
            btn.setMinWidth(gridPaneForButtons.getPrefWidth());
            btn.setMinHeight(gridPaneForButtons.getPrefHeight());

            // When any of the buttons are clicked, will lead to the clue screen
            btn.setOnAction((e -> {
                try {
                    this.changeToClueScreen(e);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }));
            gridPaneForButtons.add(btn, 0, i);

            // If no true in category flag, disable due to category being complete
            if(!flags.cateContainTrue(i)){
                btn.setDisable(true);
            }
        }
    }

    /**
     * Gets the category names from the array and turns into list
     * @return List of category names
     */
    private List<String> getCatList(){
        return Arrays.asList(categories.get(category.get(0)).get(0),
                categories.get(category.get(1)).get(0),
                categories.get(category.get(2)).get(0),
                categories.get(category.get(3)).get(0),
                categories.get(category.get(4)).get(0));
    }

    /**
     * Display clue screen with that categories info
     * @param event         On click action when a category button is pressed
     * @throws IOException  If screen cannot load
     */
    public void changeToClueScreen(ActionEvent event) throws IOException {
        String chosenCategory = ((Button)event.getSource()).getText();

        // Updates the information stored for the new current category chosen by the user
        if(chosenCategory.equals("International")){
            updateInternational();
        } else {
            updateNZCategory(chosenCategory);
        }

        // Change to clue screen for the category
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/quinzical/playModule/PlayCluesScreen.fxml"));

        Parent tableViewScreen = loader.load();
        Scene clueScene = new Scene(tableViewScreen);

        PlayCluesController controller = loader.getController();
        ControllerHolder holder = new ControllerHolder(this);
        setController(chosenCategory, controller, holder);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(clueScene);
        window.show();
    }

    /**
     * Sets up the next screens controller
     * @param chosenCategory    The category picked by the user
     * @param controller        An instance of the play clue controller
     * @param holder            Holds the current controller
     * @return                  The updated instance of the play clue controller
     */
    private PlayCluesController setController(String chosenCategory, PlayCluesController controller,
                                              ControllerHolder holder) {
        if (chosenCategory.equals("International")){
            int number = generateRandomNumber(5, categories.get(categories.size()-1).size());
            controller.initData(categories.get(categories.size()-1).get(number), flags, holder, chosenCategory);
        } else {
            for(int i=0; i<category.size(); i++){
                //Find pos of the chosen category in categories list
                if(categories.get(category.get(i)).get(0).equals(chosenCategory)){
                    int number = generateRandomNumber(i, categories.get(category.get(i)).size());
                    controller.initData(categories.get(category.get(i)).get(number), flags, holder, chosenCategory);
                }
            }
        }
        return controller;
    }

    /**
     * Updates the information for play of the chosen NZ category
     */
    private void updateNZCategory(String chosenCategory) {
        for(int i=0; i<category.size(); i++){
            if(categories.get(category.get(i)).get(0).equals(chosenCategory)){
                // update category index file
                runCommand("echo " + i + " > ./index/currentCategory.txt");
                String temp = "";
                for(int j=0; j<5; j++){
                    temp += flags.get(i, j) + ",";
                }
                runCommand("echo " + temp + " > ./index/buttonStatus.txt");
            }
        }
    }

    /**
     * Updates the information for play of the international category
     */
    private void updateInternational() {
        runCommand("echo " + 5 + " > ./index/currentCategory.txt");
        String temp = "";
        for(int j=0; j<5; j++){
            temp += flags.get(5, j) + ",";
        }
        runCommand("echo " + temp + " > ./index/buttonStatus.txt");
    }

    /**
     * Display the reset screen
     * @param event         On click action for RESET button
     * @throws IOException  If screen cannot load
     */
    public void setResetButton(ActionEvent event) throws IOException {
        Parent tableViewScreen = FXMLLoader.load(getClass().getResource("/quinzical/other/ResetScreen.fxml"));
        Scene menuScene = new Scene(tableViewScreen);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(menuScene);
        window.show();
    }

    /**
     * Display the menu screen
     * @param event         On click action for home icon button
     * @throws IOException  If screen cannot load
     */
    public void setHomeIcon(ActionEvent event) throws IOException {
        Parent tableViewScreen = FXMLLoader.load(getClass().getResource("/quinzical/other/MenuScreen.fxml"));
        Scene menuScene = new Scene(tableViewScreen);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(menuScene);
        window.show();
    }

    /* This method reads in all categories and clues, storing appropriately in lists */
    public void readFile(){
        categories = new ArrayList<>();

        File folder = new File("./categories");
        File[] files = folder.listFiles();
        try {
            assert files != null;
            for (File file : files) {
                if (file.length() != 0) { // Checks if the file is empty, disregard empty files
                    List<String> category = new ArrayList<>();
                    category.add(file.getName());
                    BufferedReader text = new BufferedReader(new FileReader(file));

                    String line;
                    int numberOfLines = 0;
                    while ((line = text.readLine()) != null) {
                        category.add(line);
                        numberOfLines++;
                    }

                    if (numberOfLines > 4) { // Checks that there are at least 5 clues in the category
                        categories.add(category);
                    }

                    text.close();
                }
            }
        } catch(Exception e) {
            e.printStackTrace();
        }


    }

    /**
     * Set the money to current winnings in top corner
     * @param text  The current winning amount
     */
    public void setText(String text){
        money.setText(text);
    }

    /**
     * This method will remove the specific clue passed in
     * @param line
     */
    public void clearMap(String line){
        for(int i=0; i<category.size();i++){
            for(int j=0;j<categories.get(category.get(i)).size(); j++){
                if(line.equals(categories.get(category.get(i)).get(j))){
                    map.remove(i, j);
                }
            }
        }

    }

    /**
     * Reads the International.txt file to get all international questions
     */
    public void readFileIntoInternational(){
        File file = new File("./international/International.txt");
        try {
            assert file != null;
            if (file.length() != 0) { // Checks if the file is empty, disregard empty files
                internationalCategory = new ArrayList<>();
                BufferedReader text = new BufferedReader(new FileReader(file));

                String line;
                while ((line = text.readLine()) != null) {
                    internationalCategory.add(line);
                }

                categories.add(internationalCategory);
                text.close();
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * It generate a random integer for choosing random non-repeated clues
     * @param index index of the category in the list
     * @param size size of the category chosen
     * @return a random integer
     */
    public int generateRandomNumber(int index, int size){
        // Randomly chose an index between 0 and the number of clues that category to randomly chose a clue
        Random random = new Random();
        int number = random.nextInt(size);
        // Random index cannot be 0 as that is the position of the category title
        if (number == 0){
            number++;
        }

        while(map.contains(index, number)){
            number = random.nextInt(size);
            if(number == 0){
                number++;
            }
        }
        map.put(index, number);
        return number;
    }

}