package quinzical.functionality;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import static quinzical.main.Main.runCommand;

/**
 * FlagArray class is used to store all the information about button status and internation unlock status
 * @author Wayne and Kayla
 */
public class FlagArray {
    private String[][] flags; //button status
    private boolean unlockFlag; //unlock international status
    private boolean firstFlag; //temp flag for implementation reason

    /**
     * Initialize the array, and also write it into a temp text file.
     */
    public FlagArray(){
        firstFlag = false;
        unlockFlag = false;
        File temp = new File("./progress/flags.txt");
        if(temp.exists()){
            flags = new String[6][5];
            BufferedReader readProgress = null;
            try {
                readProgress = new BufferedReader(new FileReader("./progress/flags.txt"));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            try{
                String line;
                int x = 0;
                int y = 0;
                while((line=readProgress.readLine())!=null){
                    if (x < 6 && y < 5) {
                        flags[x][y] = line;
                    }
                    if(y==4){
                        x++;
                        y = 0;
                    }else{
                        y++;
                    }
                }
            } catch(Exception e){
                e.printStackTrace();
            }
        } else{
            flags= new String[][]{
                    {"true", "false", "false", "false", "false"},
                    {"true", "false", "false", "false", "false"},
                    {"true", "false", "false", "false", "false"},
                    {"true", "false", "false", "false", "false"},
                    {"true", "false", "false", "false", "false"},
                    {"true", "false", "false", "false", "false"}
            };
            updateProgress();
        }

    }

    /** SETTER: Updates the flag for the specific category and clue */
    public void setFlag(int cateIndex, int clueIndex, String value){
        flags[cateIndex][clueIndex] = value;
        updateProgress();
    }

    /**
     * GETTER: Returns the flag for the specific category and clue
     */
    public String get (int cateIndex, int clueIndex){
        return flags[cateIndex][clueIndex];
    }

    /** Check if the game is completed */
    public boolean containTrue(){
        for(int i = 0; i < flags.length; i++){
            for(int j = 0; j < flags[0].length; j++){
                // If a any position of the entire array is true then the game is not complete
                if(flags[i][j].equals("true")){
                    return true;
                }
            }
        }
        // If no position of the entire array is true then the game is complete
        return false;
    }

    /**
     * Used for check if category is completed
     */
    public boolean cateContainTrue(int index){
        for(int i = 0; i < 5; i++){
            if(flags[index][i].equals("true")){
                // If a any clue of that category is true then the category is not complete
                return true;
            }
        }
        // If no clue of that category has returned true then the category is complete
        return false;
    }

    /**
     * Updates the progress of the game in play by writing to the flag text file
     */
    public void updateProgress(){
        String temp = "";
        for(int i = 0; i < flags.length; i++){
            for(int j = 0; j < flags[0].length; j++){
                temp += flags[i][j] + "\n";
            }
        }
        temp.substring(0,temp.length()-2);
        runCommand("echo $\'"+ temp + "\' > ./progress/flags.txt");
    }

    /**
     * Check if the international can be unlocked or not
     * @return true if international is unlocked, false otherwise
     */
    public boolean checkInternational(){
        int counter = 0;
        for(int i = 0; i < 5; i++){
            if(!cateContainTrue(i)){
                counter++;
            }
        }
        if(counter>=2 && !unlockFlag){
            unlockFlag = true;
            firstFlag = true;
            return true;
        } else if (counter>=2 && unlockFlag){
            firstFlag = false;
            return true;
        } else{
            return false;
        }

    }

    /**
     * Check if it's the first time international is being able to be unlocked
     * @return a boolean depending on the status
     */
    public boolean isFirstInternational(){
        return firstFlag;
    }

}
