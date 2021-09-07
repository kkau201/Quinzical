package quinzical.functionality;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class contains 6 maps, one for each category.
 * It helps to make sure no clues is ever repeated
 * @author Wayne and Kayla
 */
public class CategoryMaps {

    private List<Map<Integer, Integer>> maps;
    private Map<Integer, Integer> internationalMaps;

    /**
     * Constructor for CategoryMaps
     * Initialize all maps and lists for further method call.
     */
    public CategoryMaps(){
        maps = new ArrayList<>();
        internationalMaps = new HashMap<>();

        Map<Integer, Integer> category1 = new HashMap<>();
        Map<Integer, Integer> category2 = new HashMap<>();
        Map<Integer, Integer> category3 = new HashMap<>();
        Map<Integer, Integer> category4 = new HashMap<>();
        Map<Integer, Integer> category5 = new HashMap<>();
        Map<Integer, Integer> categoryInt = new HashMap<>();

        maps.add(category1);
        maps.add(category2);
        maps.add(category3);
        maps.add(category4);
        maps.add(category5);
        maps.add(categoryInt);
    }

    /**
     * Put the number (index of the clue) into corresponding map.
     * @param index category index
     * @param number clue index
     */
    public void put(int index, int number){
        maps.get(index).put(number, 1);
    }

    /**
     * Find out if the map already contains a number
     * @param index category index
     * @param number clue index
     * @return true if contains, otherwise false
     */
    public boolean contains(int index, int number){
        return maps.get(index).containsKey(number);
    }

    /**
     * Remove a number from a map
     * @param index category index
     * @param number clue index
     */
    public void remove(int index, int number){
        maps.get(index).remove(number);
    }
}
