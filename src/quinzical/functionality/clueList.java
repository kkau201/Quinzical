package quinzical.functionality;

import java.io.*;
import java.util.*;

import static quinzical.main.Main.runCommand;

/**
 * A class that stores a list of integer, which representing index position of the
 * category taken out from the all categories list
 * @author Wayne and Kayla
 */
public class clueList {
    private List<Integer> clues;

    /*
        This class contains the 5 categories randomly picked
     */

    /**
     * Takes out 5 random integer, and also record it into a text file
     * @param size number of categories there exists
     */
    public void takeOut5Categories(int size) {

        Map<Integer, Integer> map = new HashMap<>();
        String temp = "";
        clues = new ArrayList<>();
        Random number = new Random();
        while(clues.size()!=5) {
            int index = number.nextInt(size);
            if(!map.containsKey(index)){
                map.put(index, 1);
                clues.add(index);
                temp += index + "\n";

            }
        }
        temp.substring(0,temp.length()-2);
        runCommand("echo $\'"+ temp + "\' > ./progress/categories.txt");
    }

    /**
     * Read the temp file categories.txt to reload the 5 categories back
     */
    public void continueProgress(){
        clues = new ArrayList<>();
        BufferedReader readProgress = null;
        try {
            readProgress = new BufferedReader(new FileReader("./progress/categories.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try{
            String line;
                while((line=readProgress.readLine())!=null){
                    if (!line.equals("")) {
                        clues.add(Integer.parseInt(line));
                    }
                }
        } catch(Exception e){
            e.printStackTrace();
        }


    }

    /**
     * Get the integer at the index position
     * @param index index of the list
     * @return the integer at the index
     */
    public int get(int index){
        return clues.get(index);
    }

    /**
     * Size of the list
     * @return Size of the list
     */
    public int size(){
        return clues.size();
    }
}
