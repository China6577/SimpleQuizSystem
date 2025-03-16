package QuizSystem;

import xjtlu.cpt111.assignment.quiz.model.Question;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class SelectTopic {

    /**
     * This method allows the user to select a topic from a list of topics.
     *
     * @param topicList is a list of available topics.
     * @return The topic selected by the user.
     */
    public static String selectTopic(List<String> topicList) {
        Scanner scanner = new Scanner(System.in); // Create a scanner object for input
        int topicNum = 1; // Counter for topic numbering
        System.out.println(); // Print a new line for better formatting

        // Print the list of topics to the user
        printTopic(topicList, topicNum);

        // Prompt the user to enter their choice
        System.out.print("Please enter your choice: ");
        int choice;

        while (true) {
            try {
                choice = scanner.nextInt(); // Read the user's input as an integer
                if (choice > topicList.size()) {
                    // Check if the input is out of range
                    throw new IllegalArgumentException("The input value is out of the choice range!");
                } else break; // If input is valid, exit the loop
            } catch (InputMismatchException e) {
                // If the input is not an integer, prompt the user again
                System.out.println("Invalid input, please input an integer.");
                scanner.next(); // Consume the invalid input
                System.out.println(); // Print a new line for better formatting

                // Reprint the list of topics and ask the user for input again
                printTopic(topicList, topicNum);
                System.out.print("Please enter your choice: ");
            } catch (IllegalArgumentException e) {
                // If the input is out of range, prompt the user again
                System.out.println(e.getMessage());
                System.out.println();  // Print a new line for better formatting

                // Reprint the list of topics and ask the user for input again
                printTopic(topicList, topicNum);
                System.out.print("Please enter your choice: ");
            }
        }

        // Confirm the user's choice and return the selected topic
        System.out.println("You have successfully selected the topic: \"" + topicList.get(choice - 1) + "\".");
        return topicList.get(choice - 1); // Return the topic selected by the user
    }

    /**
     * This method is to prints the list of topics with corresponding numbers.
     *
     * @param topicList A list of topics to be printed.
     * @param topicNum  The starting number for the topics (used for numbering).
     */
    private static void printTopic(List<String> topicList, int topicNum) {
        System.out.println("******************************************");
        System.out.println("Please choose one of the following topics:");

        // Print each topic with its corresponding number
        for (String topic : topicList) {
            System.out.println(topicNum++ + ": " + topic);
        }

        System.out.println("******************************************");
    }

    /**
     * This method is to generates a list of unique topics from an array of questions.
     *
     * @param questions An array of Question objects.
     * @return A list of unique topic names extracted from the questions.
     */
    public static List<String> getTopicList(Question[] questions) {
        List<String> topicList = new ArrayList<>(); // List to store unique topics

        // Loop through all the questions and add the unique topics to the list
        for (Question question : questions) {
            if (!topicList.contains(question.getTopic())) {
                topicList.add(question.getTopic());
            }
        }

        return topicList; // Return the list of unique topics
    }
}

