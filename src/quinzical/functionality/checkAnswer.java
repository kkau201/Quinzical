package quinzical.functionality;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

/**
 * This class checks the answer given by user to the given clue matches the real answer
 * @author Wayne and Kayla
 */
public class checkAnswer {
    private String userAnswer;
    private String realAnswer;

    /**
     * Checks the answer of the user to the real one
     * @param theirAns  The answer input by the user
     * @param ans       The correct answer to the clue
     * @return          Boolean value true if users answer matches
     */
    public Boolean isAnsCorrect(String theirAns, String ans){
        // If the user has not answered, return false
        if(userAnswer == "") {
            return false;
        }

        refactorAnswers(theirAns, ans);

        // For answers where multiple answers are required
        if(realAnswer.contains(",")){
            return multipleAnswerCheck();
        }
        // For answers with multiple forms of answers
        else if(realAnswer.contains("/")){
            return multitudeAnswerCheck();
        }
        // For regular, one answer questions
        else {
            // Remove any spaces in both real and user answer to ensure no extra spaces are present
            realAnswer = realAnswer.replace(" ", "");
            userAnswer = userAnswer.replace(" ", "");

            if (userAnswer.equals(realAnswer)){
                return true;
            }
        }
        return false;
    }

    /* Returns true if the users answer matches that of one other possible multiple real answers */
    private Boolean multitudeAnswerCheck() {

        String remainingAns = realAnswer; // Make a copy of the real answer in order to split it up
        String userAns = userAnswer.replace(" ", ""); // Remove spaces in user answer

        // Continuously loop until no more '/' or it has found a matching answer to the user1
        while(remainingAns.contains("/")){
            String ansSplit = remainingAns.split("/")[0];
            remainingAns = remainingAns.substring(remainingAns.indexOf('/',0)+1).trim().toLowerCase();

            // Remove any spaces in this version of the real answer to ensure no extra spaces are present
            ansSplit = ansSplit.replace(" ", "");
            if(userAns.equals(ansSplit)){
                return true;
            }
        }

        // Remove any spaces in this version of the real answer to ensure no extra spaces are present
        remainingAns = remainingAns.replace(" ", "");
        if(userAns.equals(remainingAns)){
            return true;
        }
        return false;
    }

    /* Returns true if the users answer matches that of all of the multiple real answers */
    private Boolean multipleAnswerCheck() {
        String updateTheirAnswer = userAnswer;
        List<String> theirAnsList =  new LinkedList<String>();

        // Checks if the user split their answer by comma or a space
        if(userAnswer.contains(",")) {
            // loops to split the answer at each comma to place each word in a linked list
            while (updateTheirAnswer.contains(",")) {
                theirAnsList.add(updateTheirAnswer.split(",")[0]);
                updateTheirAnswer = updateTheirAnswer.substring(updateTheirAnswer
                        .indexOf(',', 0) + 1).trim().toLowerCase();
            }
        } else {
            // loops to split the answer at each space to place each word in a linked list
            while (updateTheirAnswer.contains(" ")) {
                theirAnsList.add(updateTheirAnswer.split("\\s+")[0]);
                updateTheirAnswer = updateTheirAnswer.substring(updateTheirAnswer
                        .indexOf(' ', 0) + 1).trim().toLowerCase();
            }
        }
        // Finally add the last word of their answer
        theirAnsList.add(updateTheirAnswer);

        // Split the real answer so each word separated by a comma is added to a linked list
        String updateExpectedAnswer = realAnswer;
        List<String> expectedAnsList =  new LinkedList<String>();
        while(updateExpectedAnswer.contains(",")){
            expectedAnsList.add(updateExpectedAnswer.split(",")[0]);
            updateExpectedAnswer = updateExpectedAnswer.substring(updateExpectedAnswer
                    .indexOf(',',0)+1).trim().toLowerCase();
        }
        expectedAnsList.add(updateExpectedAnswer);

        // Checks if the two lists are equal, returns true if so
        return listEqualsIgnoreOrder(theirAnsList, expectedAnsList);
    }

    /**
     * Checks if lists of strings are equal, ignoring order of appearance
     * @param theirAnsList      A list of the users answers separated by each word
     * @param expectedAnsList   A list of the correct answers separated by each individual answer required
     * @return  Boolean value true if they two lists match
     */
    public boolean listEqualsIgnoreOrder(List<String> theirAnsList, List<String> expectedAnsList) {
        return new HashSet<>(theirAnsList).equals(new HashSet<>(expectedAnsList));
    }

    /**
     * Ensures no case differences or fullstops that would make it not equal
     * @param theirAns  The answer input by the user
     * @param ans       The correct answer to the clue
     */
    private void refactorAnswers(String theirAns, String ans){
        userAnswer = theirAns.toLowerCase();
        realAnswer = ans.toLowerCase();

        userAnswer = userAnswer.replace(".", "");
        realAnswer = realAnswer.replace(".", "");
    }
}
