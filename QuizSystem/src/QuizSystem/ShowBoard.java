package QuizSystem;

import xjtlu.cpt111.assignment.quiz.model.Question;

import java.util.List;
import java.util.Scanner;

/**
 * This class is used to display the main interface menu and perform the corresponding action according to the user's selection.
 */
public class ShowBoard {

    /**
     * The main screen menu is displayed and the corresponding actions are performed according to the user selection.
     *
     * @param username           username
     * @param topicList          topicList
     * @param questions          questions
     * @param scoreDataArrayList scoreDataArrayList
     */
    public static void showBoard(String username, List<String> topicList, Question[] questions, List<String[]> scoreDataArrayList) {
        System.out.println(); // Print a blank line to separate the output

        // Create a Scanner object to read user input
        Scanner sc = new Scanner(System.in);

        // Use an infinite loop to continuously display menus and process user choices
        while (true) {
            // Print menu titles and options
            System.out.println("*******************************************");
            System.out.println("Please choose one of the following options: ");
            System.out.println("1. UserDashboard\n2. Leaderboard\n3. Exit");
            System.out.println("*******************************************");
            System.out.print("Enter your choice: ");

            // Read the selection of user input
            String choice = sc.nextLine();

            try {
                switch (choice) {
                    case "1":
                        // Call the selectOption method of the UserDashboard class to display the user dashboard
                        UserDashboard.selectOption(username, questions);
                        System.out.println();
                        return;
                    case "2":
                        // Gets a list of the names of the highest scoring users
                        List<String> highestScoreNameList = Leaderboard.getHighestScoreNameList(topicList, scoreDataArrayList);
                        // Call the showHighestScoreName method of the Leaderboard class to display the highest scoring users
                        Leaderboard.showHighestScoreName(topicList, highestScoreNameList);
                        System.out.println(); // Print a blank line to separate the output
                        return;
                    case "3":
                        // Print a thank message for quitting the system
                        System.out.println();
                        System.out.println("Thank you for using Quiz System!");
                        // Exit program
                        System.exit(0);
                    default:
                        // An invalid parameter exception is thrown, prompting the user for invalid input
                        throw new IllegalArgumentException("Invalid choice, please try again");
                }
            } catch (IllegalArgumentException e) {
                // Catches and prints an error message for an illegal parameter exception
                System.out.println(e.getMessage());
                System.out.println(); // Print a blank line to separate the output
            }
        }
    }
}
