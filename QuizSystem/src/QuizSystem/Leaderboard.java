package QuizSystem;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used to process and display leaderboard information.
 */
public class Leaderboard {

    /**
     * Gets a list of the highest scoring usernames for each topic.
     *
     * @param topicList          Topic list
     * @param scoreDataArrayList A list of score data, where each element is an array of strings containing the username, subject, and score
     * @return List of highest scoring users for each topic
     */
    public static List<String> getHighestScoreNameList(List<String> topicList, List<String[]> scoreDataArrayList) {
        List<Integer> highestScoreList = new ArrayList<>(); // Store the highest score for each topic
        List<String> highestScoreNameList = new ArrayList<>(); // Stores the highest scoring username for each theme

        // Initializes the list of highest scores and usernames
        for (int i = 0; i < topicList.size(); i++) {
            highestScoreNameList.add("None"); // The initial value is "None"
            highestScoreList.add(0); // The initial value is 0
        }

        // Iterate over the score data list
        for (String[] dataArray : scoreDataArrayList) {
            // Parse the username, subject, and score in each record
            String name = dataArray[0].split(": ")[1]; // name
            String topic = dataArray[1].split(": ")[1]; // topic
            int score = Integer.parseInt(dataArray[2].split(": ")[1]); // score

            // Iterate through the topic list, updating the highest score and username
            for (int i = 0; i < topicList.size(); i++) {
                if (topicList.get(i).equals(topic) && score > highestScoreList.get(i) && !name.equals(highestScoreNameList.get(i))) {
                    highestScoreList.set(i, score); // Update maximum score
                    highestScoreNameList.set(i, name); // Update the highest score username
                }
            }
        }

        return highestScoreNameList; // Returns a list of the highest scoring usernames
    }

    /**
     * Displays the highest scoring username for each topic.
     *
     * @param topicList            Topic list
     * @param highestScoreNameList List of highest scoring users for each topic
     */
    public static void showHighestScoreName(List<String> topicList, List<String> highestScoreNameList) {
        System.out.println();
        System.out.println("*****************************************************"); // Print divider
        System.out.println("The person with the highest score for each topic are: ");
        for (int i = 0; i < topicList.size(); i++) {
            System.out.println(topicList.get(i) + ": " + highestScoreNameList.get(i)); // Print the highest scoring username for each topic
        }
        System.out.println("*****************************************************"); // Print divider
    }

}
