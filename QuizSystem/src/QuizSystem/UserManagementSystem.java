package QuizSystem;

import java.io.*;
import java.util.*;

public class UserManagementSystem {
    /**
     * User management method that handles user login,registration,log out and exit operations
     *
     * @param userDataArrayList  List of user data
     * @param scoreDataArrayList List of score data
     * @return Returns the username if logged in otherwise returns an empty string for registration or logout
     */
    public static String userManagement(List<String[]> userDataArrayList, List<String[]> scoreDataArrayList) {
        String csvPath = "resources/users.csv"; // Path to the user data CSV file
        List<String> userIdsFromCsv = readUserIdsFromCsv(userDataArrayList); // Read user IDs from CSV
        List<String> userPasswordsFromCsv = readUserPasswordsFromCsv(userDataArrayList); // Read user password from CSV
        List<String> userNamesFromCsv = readUserNamesFromCsv(userDataArrayList); // Read usernames from CSV

        // Welcome message
        System.out.println();
        System.out.println("===========================");
        System.out.println("= Welcome to Quiz System! =");
        System.out.println("===========================");

        Scanner scanner = new Scanner(System.in); // Create a scanner to get user input
        while (true) {
            // Display operation menu
            System.out.println();
            System.out.println("***************************");
            System.out.println("Please choose an operation: ");
            System.out.println("1. Log in\n2. Register\n3. Log out\n4. Exit");
            System.out.println("***************************");
            System.out.print("Enter your choice: ");
            int choice;
            while (true) {
                try {
                    choice = scanner.nextInt(); // Get user choice
                    break;
                } catch (InputMismatchException e) {
                    // Handle invalid input
                    System.out.println("Invalid input, please input a integer.");
                    scanner.next();// Clear invalid input
                    System.out.println();
                    System.out.println("***************************");
                    System.out.println("Please choose an operation: ");
                    System.out.println("1. Log in\n2. Register\n3. Log out\n4. Exit");
                    System.out.println("***************************");
                    System.out.print("Enter your choice: ");
                }
            }
            scanner.nextLine();// Clear newline character

            // Execute corresponding operation based on user choice
            switch (choice) {
                case 1: // Login in
                    String username = login(scanner, userIdsFromCsv, userNamesFromCsv, userPasswordsFromCsv);
                    if (!username.isEmpty()) {
                        return username; // Return username
                    } else break;
                case 2: // Register
                    boolean registerStatus = register(scanner, userIdsFromCsv, csvPath);
                    if (registerStatus) {
                        return ""; // Return empty string on successful registration
                    } else break;
                case 3: // Log out
                    boolean deleteStatus = deleteUser(scanner, userIdsFromCsv, userPasswordsFromCsv, userNamesFromCsv, csvPath, scoreDataArrayList);
                    if (deleteStatus) {
                        return ""; // Return empty string on successful Log out
                    } else break;
                case 4: // Exit
                    System.out.println();
                    System.out.println("You successfully exit quiz system!");
                    return ""; // Exit the system
                default: // Invalid choice
                    System.out.println("Invalid choice, please enter again!");
            }
        }
    }

    /**
     * Login method that verifies user ID and password
     *
     * @param scanner              Scanner
     * @param userIdsFromCsv       List of user IDs
     * @param userNamesFromCsv     List of usernames
     * @param userPasswordsFromCsv List of user password
     * @return Returns the username if login is successful, otherwise return an empty string
     */
    private static String login(Scanner scanner, List<String> userIdsFromCsv, List<String> userNamesFromCsv, List<String> userPasswordsFromCsv) {
        System.out.print("Please enter your user id: ");
        String userId = scanner.nextLine(); // Get user ID
        int dataLine = dataLine(userId, userIdsFromCsv); // Get the line number of the user ID in the list
        if (userIdsFromCsv.contains(userId)) { // Verify if the user ID exists
            System.out.print("Please enter your password: ");
            String password = scanner.nextLine(); // Get user password
            if (password.equals(userPasswordsFromCsv.get(dataLine))) { // Verify password
                String username = userNamesFromCsv.get(dataLine); // Get username
                System.out.println();
                System.out.println("Hello, " + username + "! You successfully logged in!"); // Login success message
                System.out.println();
                return username; // Return username
            } else {
                System.out.println("Invalid password, please try again!"); // Password error message
            }
        } else {
            System.out.println("Invalid user id, please try again!"); // User ID error message
        }
        return ""; // Return empty string on login failure
    }

    /**
     * Registration method that handles user registration
     *
     * @param scanner        Scanner
     * @param userIdsFromCsv List of user IDs
     * @param csvPath        Path to the user data CVS file
     * @return Return registration status, true for success, fails for failure
     */
    private static boolean register(Scanner scanner, List<String> userIdsFromCsv, String csvPath) {
        boolean registerStatus = false; // Registration status
        System.out.print("Please enter the ID you want to register: ");
        String userId = scanner.nextLine(); // Get user ID
        if (Objects.equals(userId, "")) { // Check if ID is empty
            System.out.println("The registration id cannot be empty, please try again!");
            return registerStatus; // Return registration status
        }
        if (userIdsFromCsv.contains(userId)) { // Check if ID already exists
            System.out.println("Id already exists, please try again!");
        } else {
            System.out.print("Please enter the username you want to register: ");
            String username = scanner.nextLine(); // Get username
            if (Objects.equals(username, "")) { // Check if username is empty
                System.out.println("The username cannot be empty, please try again!");
                return registerStatus; // Return registration status
            }
            System.out.print("Please enter the password you want to register: ");
            String password = scanner.nextLine(); // Get password
            if (Objects.equals(password, "")) { // Check if password is empty
                System.out.println("The password cannot be empty, please try again!");
                return registerStatus; // Return registration status
            }
            String data = userId + "," + username + "," + password; // Create user data string
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(csvPath, true))) { // Append to CVS file
                writer.write(data); // Write data
                writer.newLine(); // New line
            } catch (IOException e) {
                e.printStackTrace(); // Handle IO exception
            }
            registerStatus = true; // Registration successful
            System.out.println();
            System.out.println("You successfully registered!"); // Registration success message
        }
        return registerStatus; // Return registration status
    }

    /**
     * Log out method that handles user log out
     *
     * @param scanner              Scanner
     * @param userIdsFromCsv       List of user IDs
     * @param userPasswordsFromCsv List of user password
     * @param userNamesFromCsv     List of usernames
     * @param csvPath              Path to the user data CVS file
     * @param scoreDataArrayList   List of score data
     * @return Returns Log out status, true for success, false for failure
     */
    private static boolean deleteUser(Scanner scanner, List<String> userIdsFromCsv, List<String> userPasswordsFromCsv, List<String> userNamesFromCsv, String csvPath, List<String[]> scoreDataArrayList) {
        boolean deleteStatus = false; // Logout status
        System.out.print("Please enter the ID you want to delete: ");
        String userId = scanner.nextLine(); // Get user ID
        if (userIdsFromCsv.contains(userId)) { // Check if ID exists
            int dataLine = dataLine(userId, userIdsFromCsv); // Get the line number of the user ID in the List
            System.out.print("Please enter the password you want to delete: ");
            String password = scanner.nextLine(); // Get user password
            if (password.equals(userPasswordsFromCsv.get(dataLine))) { // Verify password
                deleteStatus = true; // Set log out status to true
                List<String[]> dataList = new ArrayList<>(); // Create data List
                for (int i = 0; i < userIdsFromCsv.size(); i++) {
                    dataList.add(new String[]{userIdsFromCsv.get(i), userNamesFromCsv.get(i), userPasswordsFromCsv.get(i)});
                }
                for (int i = 0; i < dataList.size(); i++) {
                    if (Objects.equals(dataList.get(i)[0], userId)) { // Find the user to delete
                        dataList.remove(i);// Remove user data
                        i--; // Adjust index
                    }
                }
                try (BufferedWriter bw = new BufferedWriter(new FileWriter(csvPath))) { // Rewrite CVS file
                    for (String[] row : dataList) {
                        bw.write(String.join(",", row)); // Write data
                        bw.newLine(); // New line
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                deleteScoreData(userNamesFromCsv.get(dataLine), scoreDataArrayList); // Delete user score data
                System.out.println();
                System.out.println("You successfully deleted!"); // Log out success message
            } else System.out.println("Password error, delete failed!"); // Password error message
        } else {
            System.out.println("Id does not exist, please try again!"); // ID does not exist message
        }
        return deleteStatus; // Return Log out status
    }

    /**
     * Reads user IDs from the CSV file
     *
     * @param userDataArrayList List of user data
     * @return Returns the list of user IDs
     */
    private static List<String> readUserIdsFromCsv(List<String[]> userDataArrayList) {
        List<String> userIds = new ArrayList<>(); // Create user ID list
        for (String[] strings : userDataArrayList) {
            userIds.add(strings[0]); // Add user ID
        }
        return userIds; // Return user ID list
    }

    /**
     * Get the line number of a user ID in the list
     *
     * @param userId         User ID
     * @param userIdsFromCsv List of user IDs
     * @return Return the line number of the user ID, returns 0 if not found
     */
    private static int dataLine(String userId, List<String> userIdsFromCsv) {
        for (int i = 0; i < userIdsFromCsv.size(); i++) {
            if (userId.equals(userIdsFromCsv.get(i))) { // Find user ID
                return i; // Return line number
            }
        }
        return 0; // Return 0 if not found
    }

    /**
     * Reads usernames from the CVS file
     *
     * @param userDataArrayList List of user data
     * @return Returns the list of usernames
     */
    private static List<String> readUserNamesFromCsv(List<String[]> userDataArrayList) {
        List<String> userNames = new ArrayList<>(); // Create username list
        for (String[] strings : userDataArrayList) {
            userNames.add(strings[1]); // Add username
        }
        return userNames; // Return username list
    }

    /**
     * Reads user passwords from the CVS file
     *
     * @param userDataArrayList List of user data
     * @return Returns the list of user passwords
     */
    private static List<String> readUserPasswordsFromCsv(List<String[]> userDataArrayList) {
        List<String> userPasswords = new ArrayList<>(); // Create user password list
        for (String[] strings : userDataArrayList) {
            userPasswords.add(strings[2]); // Add user password list
        }
        return userPasswords;
    }

    /**
     * Deletes the user's score data
     *
     * @param username           Username
     * @param scoreDataArrayList List of score data
     */
    private static void deleteScoreData(String username, List<String[]> scoreDataArrayList) {
        try {
            FileWriter fileWriter = new FileWriter("resources/score.txt"); // Create score FileWriter
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter); // Create BufferedWriter
            for (String[] strings : scoreDataArrayList) {
                if (!username.equals(strings[0].split(": ")[1])) { // Check username
                    bufferedWriter.write(strings[0] + ", " + strings[1] + ", " + strings[2] + ", " + strings[3]); // Write score data
                    bufferedWriter.newLine();
                }
            }
            bufferedWriter.close(); // Close BufferedWriter
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}
