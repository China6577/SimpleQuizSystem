package QuizSystem;

import java.util.Scanner;

/**
 * This class is used to determine whether the user has chosen to take the quiz.
 */
public class IsTakingQuiz {

    /**
     * Asks the user if they want to take the quiz and returns a Boolean value based on the user's selection.
     *
     * @return Returns true if the user chooses to take the quiz; If the user chooses to go to the user panel, false is returned.
     */
    public static boolean isTakingQuiz() {
        Scanner sc = new Scanner(System.in);
        while (true) { // Use an infinite loop to keep requesting user input until you get a valid choice
            System.out.println("***************************"); // Print divider
            System.out.println("Please choose an operation: "); // Prompts the user to select an action
            System.out.println("1. Take a quiz\n2. Go to user board\n3. Exit"); // Displays two selectable actions
            System.out.println("***************************"); // Print divider
            System.out.print("Enter an operation: "); // Requests the user to enter an operation number
            String operation = sc.nextLine(); // Reads a line of text entered by the user
            switch (operation) { // Perform different actions depending on what the user enters
                case "1": // If the user typed "1"
                    return true; // Return true, indicating that the user has chosen to take the quiz
                case "2": // If the user typed "2"
                    return false; // If false is returned, the user chooses to go to the user panel
                case "3": // If the user typed "3"
                    System.out.println();
                    System.out.println("Thank you for using Quiz System!");
                    System.exit(0); // Exit the quiz system
                default: // If the user enters anything else
                    System.out.println("Invalid choice, please try again."); // The user is prompted to enter invalid, please try again
                    System.out.println();
            }
        }
    }
}

