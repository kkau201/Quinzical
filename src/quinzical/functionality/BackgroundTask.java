package quinzical.functionality;

import java.io.IOException;

/**
 * This class is a background thread for festival to run and speak the clue chosen by user in games module
 * It helps improve responsiveness of the GUI.
 * @author Wayne and Kayla
 */
public class BackgroundTask extends javafx.concurrent.Task<Void>{

    private String speedCmd;
    private String textCmd;
    private ProcessBuilder builder;

    public BackgroundTask(String speed, String text){
        speedCmd =  speed;
        textCmd =  text;
    }

    /**
     * Calls festival command in bash to speak the clue
     * @return
     */
    @Override
    protected Void call() {
        try {
            builder = new ProcessBuilder("/bin/festival", "(voice_akl_nz_jdt_diphone)" , speedCmd, textCmd);
            builder.start().waitFor();

        } catch(Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }

    /**
     * Stops the thread, in order to stop festival from speaking
     * @throws IOException
     */
    public void stop() throws IOException {
        builder.start().destroy();
    }
}
