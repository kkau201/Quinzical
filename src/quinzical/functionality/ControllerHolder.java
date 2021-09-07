package quinzical.functionality;

import quinzical.playModule.PlayCategoriesController;

/**
 * This class will contain a PlayCategoriesController, it helps passing the controller around if
 * something needs to be done with the controller.
 * @author Wayne and Kayla
 */
public class ControllerHolder {
    private PlayCategoriesController controller;

    /**
     * Constructor
     * @param controller The current controller for the PlayCategories screen
     */
    public ControllerHolder(PlayCategoriesController controller){
        this.controller = controller;
    }

    /**
     * SetText of the money text in that controller.
     * @param text
     */
    public void setText(String text){
        controller.setText(text);
    }

}
