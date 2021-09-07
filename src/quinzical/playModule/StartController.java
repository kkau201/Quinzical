package quinzical.playModule;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * This class handles the display/controlling functions starting a new
 * game. Hence instructions on how to play will be displayed.
 * @author Wayne and Kayla
 */
public class StartController implements Initializable {
    @FXML
    private Button startBtn;
    @FXML
    private Button backBtn;
    @FXML
    private Button one;
    @FXML
    private Button two;
    @FXML
    private Button three;
    @FXML
    private Button four;
    @FXML
    private ImageView instructionImage;

    private static Scene playScene;

    /**
     * Go to play categories screen to begin game
     * @param event         On click action for start button
     * @throws IOException  If screen cannot be loaded
     */
    public void setStartBtn(ActionEvent event) throws IOException {
        Parent tableViewScreen = FXMLLoader.load(getClass()
                .getResource("/quinzical/playModule/PlayCategoriesScreen.fxml"));
        playScene = new Scene(tableViewScreen);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(playScene);
        window.show();
    }

    /**
     * Return to menu screen
     * @param event         On click action for back button
     * @throws IOException  If screen cannot load
     * */
    public void setBackBtn(ActionEvent event) throws IOException {
        Parent tableViewScreen = FXMLLoader.load(getClass().getResource("/quinzical/other/MenuScreen.fxml"));
        Scene menuScene = new Scene(tableViewScreen);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(menuScene);
        window.show();
    }

    /**
     * Changes the opacity of the 'dot' buttons according to the current instruction slide
     * @param currentNo The number of the current instruction slide
     */
    public void setBtnOpacity(int currentNo) {
        one.setOpacity(0.5);
        two.setOpacity(0.5);
        three.setOpacity(0.5);
        four.setOpacity(0.5);
        if (currentNo == 1){
            one.setOpacity(1.0);
        } else if (currentNo == 2) {
            two.setOpacity(1.0);
        } else if (currentNo == 3) {
            three.setOpacity(1.0);
        } else {
            four.setOpacity(1.0);
        }
    }

    /**
     * Displays the first instruction slide image
     */
    public void setOne() {
        File file = new File("src/quinzical/resources/img/slide1.png");
        Image img = new Image(file.toURI().toString());
        instructionImage.setImage(img);
        setBtnOpacity(1);
    }

    /**
     * Displays the second instruction slide image
     */
    public void setTwo() {
        File file = new File("src/quinzical/resources/img/slide2.png");
        Image img = new Image(file.toURI().toString());
        instructionImage.setImage(img);
        setBtnOpacity(2);
    }

    /**
     * Displays the third instruction slide image
     */
    public void setThree() {
        File file = new File("src/quinzical/resources/img/slide3.png");
        Image img = new Image(file.toURI().toString());
        instructionImage.setImage(img);
        setBtnOpacity(3);
    }

    /**
     * Displays the fourth instruction slide image
     */
    public void setFour() {
        File file = new File("src/quinzical/resources/img/slide4.png");
        Image img = new Image(file.toURI().toString());
        instructionImage.setImage(img);
        setBtnOpacity(4);
    }

    /**
     * Changes to the next instruction slide when the current one is clicked on
     */
    public void imageClickOn() {
        int slideNo;
        if (one.getOpacity() == 1.0){
            slideNo = 2;
        } else if (two.getOpacity() == 1.0){
            slideNo = 3;
        } else {
            slideNo = 4;
        }
        File file = new File("src/quinzical/resources/img/slide" + slideNo + ".png");
        Image img = new Image(file.toURI().toString());
        instructionImage.setImage(img);
        setBtnOpacity(slideNo);
    }

    /**
     * Initialise the first slide as the first image and 'dot' button
     * Ensure when image is clicked, calls the method 'imageClickOn'
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        File file = new File("src/quinzical/resources/img/slide1.png");
        Image img = new Image(file.toURI().toString());
        instructionImage.setImage(img);
        instructionImage.setOnMouseClicked(e-> {
            imageClickOn();
        });
        setBtnOpacity(1);
    }
}