package QuizSystem;

import xjtlu.cpt111.assignment.quiz.model.Question;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class UserDashboard {

    /**
     * Allows the user to select a topic and view the history scores for that topic.
     * The user is prompted to select a topic from the available list of topics,
     * or choose to view all topics.
     *
     * @param username  The username of the user requesting the history scores.
     * @param questions An array of Question objects used to retrieve the topic list.
     */
    public static void selectOption(String username, Question[] questions) {
        // Get the list of topics from the questions
        List<String> topicList = SelectTopic.getTopicList(questions);
        System.out.println();  // Print a blank line for formatting

        while (true) {
            // Display a prompt to the user
            System.out.println("**********************************************************************");
            System.out.println("Please select the topic for which you want to view the history scores: ");

            // Get a filtered list of topics relevant to the user's scores
            List<String> targetTopicList = getTargetTopicList(topicList, username);
            // Retrieve the user's score data and the count data
            List<String> scoreDataList = getScoreList(username);
            List<Integer> countList = getCountList(topicList, scoreDataList);

            // Display the topics for the user to choose from
            int num = 1;
            // getTopicList: Store the topic of the quiz done
            List<String> getTopicList = new ArrayList<>();
            for (String topic : targetTopicList) {
                if (!Objects.equals(topic, "")) {
                    System.out.println(num + ". " + topic);
                    num++;
                    getTopicList.add(topic);
                }
            }
            // Provide an option to view all topics
            System.out.println(num + ". All Topic");
            System.out.println("**********************************************************************");
            System.out.print("Enter your choice: "); // Ask the user to input their choice

            Scanner sc = new Scanner(System.in); // Create a new scanner for input
            String choice = sc.nextLine(); // Read the user's input as a string

            try {
                // Try to convert the input to an integer
                int choiceInt = Integer.parseInt(choice);
                boolean isScoreHistory = false; // Determine whether there is a history of score
                // Check if the choice is within a valid range
                if (choiceInt > 0 && choiceInt <= num) {
                    for (String topic : getTopicList) {
                        if (!Objects.equals(topic, "")) {
                            isScoreHistory = true;
                            break;
                        }
                    }
                    if (isScoreHistory) {
                        int index = choiceInt - 1; // Adjust the index to match the user's input

                        // If the user selects a topic from the list (not 'All Topic')
                        if (index <= num - 2) {
                            String targetTopic = getTopicList.get(index); // Get the selected topic
                            System.out.println(); // Print a blank line for formatting
                            // Show the history result for the selected topic
                            showSingleHistoryResult(targetTopic, scoreDataList, countList, index);
                            break; // Exit the loop after displaying the result
                        } else {
                            // If the user selects 'All Topic', show results for all topics
                            System.out.println(); // Print a blank line for formatting
                            showAllHistoryResult(targetTopicList, scoreDataList, countList);
                            break; // Exit the loop after displaying the results
                        }
                    }
                    else {
                        System.out.println();
                        System.out.println("You currently have no record of score.");
                        break;
                    }
                } else {
                    // If the input choice is out of range, throw an exception
                    throw new IllegalArgumentException("Please enter a valid choice.");
                }
            } catch (NumberFormatException e) {
                // Handle the case where the input is not a valid integer
                System.out.println("Please enter a valid choice.");
                System.out.println(); // Print a blank line for formatting
            } catch (IllegalArgumentException e) {
                // Handle invalid choice exceptions (out of range)
                System.out.println(e.getMessage());
                System.out.println(); // Print a blank line for formatting
            }
        }
    }

    /**
     * Displays the score history of a specific topic for a given index.
     * It prints the last three scores for the specified topic.
     *
     * @param targetTopic   the name of the topic whose score history will be displayed
     * @param scoreDataList a list of strings where each string contains score data in the format "name, topic, score, count"
     * @param countList     a list of counts corresponding to each topic, representing the number of tests for each topic
     * @param index         the index of the topic in the list to fetch the score data for
     */
    public static void showSingleHistoryResult(String targetTopic, List<String> scoreDataList, List<Integer> countList, int index) {
        System.out.println("The scores of the last three times for the topic of \"" + targetTopic + "\" are (1 means the last time):");
        int maxCount = countList.get(index);
        printScore(targetTopic, scoreDataList, maxCount);
    }

    /**
     * Displays the score history for all topics that have been tested.
     * It prints the last three scores for each topic that has been tested at least once.
     *
     * @param targetTopicList a list of topics to check and display score history for
     * @param scoreDataList   a list of strings where each string contains score data in the format "name, topic, score, count"
     * @param countList       a list of counts corresponding to each topic, representing the number of tests for each topic
     */
    public static void showAllHistoryResult(List<String> targetTopicList, List<String> scoreDataList, List<Integer> countList) {
        for (int i = 0; i < countList.size(); i++) {
            if (countList.get(i) > 0) {
                int maxCount = countList.get(i);
                String targetTopic = targetTopicList.get(i);
                System.out.println("The scores of the last three times for the topic of \"" + targetTopic + "\" are (1 means the last time):");
                printScore(targetTopic, scoreDataList, maxCount);
                System.out.println();
            }
        }
    }

    /**
     * Prints the score history for a specific topic, displaying the last three scores based on the provided count.
     *
     * @param targetTopic   the name of the topic whose score history will be printed
     * @param scoreDataList a list of strings where each string contains score data in the format "name, topic, score, count"
     * @param maxCount      the maximum count to compare against for filtering score history
     */
    public static void printScore(String targetTopic, List<String> scoreDataList, int maxCount) {
        String[] scoreArray = new String[3];
        int index = 2;
        int time = 1; // record the times of score
        for (String s : scoreDataList) {
            // Split the score data string to extract individual fields
            String[] scoreDataArray = s.split(",");
            String name = scoreDataArray[0];
            String topic = scoreDataArray[1];
            String score = scoreDataArray[2];
            int count = Integer.parseInt(scoreDataArray[3]);
            // Check if the topic matches and if the count is within the last three tests
            if (topic.equals(targetTopic) && count > maxCount - 3) {
                scoreArray[index] = "username: " + name + ", topic: " + topic + ", score: " + score;
                index--;
            }
        }
        // Print the score details
        for (String s : scoreArray) {
            if (s != null) {
                System.out.println(time++ + ". " + s);
            }
        }
    }

    /**
     * Retrieves a list of topics that have been tested by the specified username.
     * For each topic, it checks if the user has a score history and includes only topics with scores.
     *
     * @param topicList a list of all topics
     * @param username  the username for which to fetch the score history
     * @return a list of topics that the user has been tested on and has a score history
     */
    public static List<String> getTargetTopicList(List<String> topicList, String username) {
        List<String> scoreDataList = getScoreList(username); // Get the user's score data and count list
        List<Integer> countList = getCountList(topicList, scoreDataList); // Get the number of tests for each topic
        List<String> targetTopicList = new ArrayList<>(); // Create a list of topics with score data (topics with count > 0)
        for (int i = 0; i < countList.size(); i++) {
            if (countList.get(i) > 0) {
                String targetTopic = topicList.get(i);
                targetTopicList.add(targetTopic);
            } else {
                targetTopicList.add("");
            }
        }
        return targetTopicList;
    }

    /**
     * Retrieves the score data for a specific user.
     * It reads the score data from a file (score.txt), filters by username, and returns the user's score history.
     *
     * @param username the username whose score history needs to be fetched
     * @return a list of score data strings for the specified user, where each string is in the format "name, topic, score, count"
     */
    public static List<String> getScoreList(String username) {
        List<String> scoreDataList = new ArrayList<>();
        try {   // Open the score file and read the data
            FileReader fileReader = new FileReader("resources/score.txt");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            while (!((line = bufferedReader.readLine()) == null)) {   // Read each line of the file
                String[] data = line.split(", ");
                String name = data[0].split(": ")[1];
                String topic = data[1].split(": ")[1];
                String score = data[2].split(": ")[1];
                String count = data[3].split(": ")[1];
                if (name.equals(username)) {    // If the current line's username matches the given username, add the data to the list
                    scoreDataList.add(name + "," + topic + "," + score + "," + count);
                }
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return scoreDataList;
    }

    /**
     * Retrieves the count of tests for each topic based on the score data.
     * This method creates a list of counts representing how many times each topic has been tested.
     *
     * @param topicList     a list of all available topics
     * @param scoreDataList a list of score data strings, where each string is in the format "name, topic, score, count"
     * @return a list of integers where each integer represents the number of tests for each topic
     */
    public static List<Integer> getCountList(List<String> topicList, List<String> scoreDataList) {
        List<Integer> countList = new ArrayList<>();
        // Initialize count list with zeros
        for (int i = 0; i < topicList.size(); i++) {
            countList.add(0);
        }
        // Fill the count list based on score data
        for (String s : scoreDataList) {
            String[] scoreDataArray = s.split(",");
            String topic = scoreDataArray[1];
            int count = Integer.parseInt(scoreDataArray[3]);
            for (int j = 0; j < topicList.size(); j++) {  // Update the count for the matching topic
                if (topic.equals(topicList.get(j))) {
                    countList.set(j, count);
                }
            }
        }
        return countList;
    }
}

