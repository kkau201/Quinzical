package quinzical.main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.*;
import java.util.Objects;

/**
 * This class handles the Quinzical application
 * @author Wayne and Kayla
 */
public class Main extends Application {

    public static double voiceSpeed = 1.0; // the value for global festival speed

    /**
     * Launches application
     * @param args  Any arguments that may be passed in when running main class
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Displays on start up the menu screen and will initialise all folders/files
     * that are required to run if they don't already exist
     * @param primaryStage  The main stage for the application
     * @throws Exception    If screen cannot be loaded
     */
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root;

        runCommand("sudo cp -r akl_nz_jdt_diphone /usr/share/festival/voices/english");
        // Checks if a 'winnings' folder exists, if not creates new winnings folder with current_winnings text file
        File temp = new File("winnings");
        if(!(temp.exists())) {
            runCommand("mkdir winnings");
            runCommand("touch ./winnings/currentWinnings.txt");
            runCommand("echo " + "0" + " > ./winnings/currentWinnings.txt");
        }

        // Checks if a 'index' folder exists, if not creates new index folder with currentCategory text file
        temp = new File("index");
        if(!(temp.exists())) {
            runCommand("mkdir index");
            runCommand("touch ./index/currentCategory.txt");
        }

        // Checks if 'progress' folder exists, if not creates new progress folder
        temp = new File("progress");
        if(!temp.exists()){
            runCommand("mkdir progress");
        }

        // Checks if 'wrong' file exists within progress directory, else creates file
        temp = new File("./progress/wrongs.txt");
        if(!temp.exists()){
            runCommand("touch ./progress/wrongs.txt");
        }

        // Load up the menu screen on start up
        root = FXMLLoader.load(getClass().getResource("/quinzical/other/MenuScreen.fxml"));
        primaryStage.setTitle("Quinzical");
        primaryStage.setScene(new Scene(root));
        primaryStage.setOnCloseRequest(we -> {
            // Gets handle of the stage and closes it
            runCommand("^C");
            primaryStage.close();
        });
        primaryStage.show();
    }

    /**
     * Reads in the current winnings of the player stored in a txt file
     * @return  A string of the current winnings of the user
     */
    public static String getWinningsAmount() {
        String money = null;

        // Reads from the currentWinnings file to print the money at the top of the screen
        BufferedReader readWinnings = null;
        try {
            readWinnings = new BufferedReader(new FileReader("./winnings/currentWinnings.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            // Sets the money string to the amount in the file
            money = Objects.requireNonNull(readWinnings).readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            assert readWinnings != null;
            readWinnings.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return money;
    }

    /**
     * Reads in the
     * @return A string of the index referring to the current category that is being played
     */
    public static String getCurrentCategory() {
        String index = null;

        // Reads from the currentCategory file to print the money at the top of the screen
        BufferedReader readCategory = null;
        try {
            readCategory = new BufferedReader(new FileReader("./index/currentCategory.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            // Sets the category index string to the number in the file
            assert readCategory != null;
            index = readCategory.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            readCategory.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return index;
    }

    /**
     * Reads in the status for all the buttons in the current category
     * @return  The button status as an array of true and false strings
     */
    public static String[] getButtonStatus() {
        String index = null;

        BufferedReader readStatus = null;
        try {
            readStatus = new BufferedReader(new FileReader("./index/buttonStatus.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            assert readStatus != null;
            index = readStatus.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            readStatus.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        assert index != null;
        String[] result = index.split(",");

        return result;
    }

    /**
     * Method to take a bash command as a string and run it on terminal
     * @param com A bash command
     */
    public static void runCommand(String com) {
        try {
            ProcessBuilder builder = new ProcessBuilder("/bin/bash", "-c", com);
            Process process = builder.start();

            InputStream out = process.getInputStream();
            BufferedReader stdout = new BufferedReader(new InputStreamReader(out));
            int exitStatus = process.waitFor();

            if(exitStatus ==0) {
            }

        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }
}