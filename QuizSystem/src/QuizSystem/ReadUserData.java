package QuizSystem;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is used to read user data.
 */
public class ReadUserData {

    /**
     * Reads user data from the specified CSV file.
     *
     * @return A list containing user data, each element being an array of strings
     */
    public static List<String[]> getUserData() {
        // Define the path to the user data file
        String csvPath = "resources/users.csv";

        // Create a list to store the read user data
        List<String[]> userDataArrayList = new ArrayList<>();

        // Print a blank line to separate the output
        System.out.println();

        // Prints a message to start reading user data
        System.out.println("===\n=== read user data - started\n===");
        System.out.println("read users information from file \"" + csvPath + "\"");

        try (BufferedReader br = new BufferedReader(new FileReader(csvPath))) {
            // Close BufferedReader automatically with the try-with-resources statement
            String line;
            while ((line = br.readLine()) != null) {
                // Read the file contents line by line
                // Use commas to split the contents of each line to get an array of strings
                String[] userData = line.split(",");
                // Adds an array of strings to the list
                userDataArrayList.add(userData);
            }
        } catch (IOException e) {
            // Capture and print stack trace information for any I/O exceptions
            e.printStackTrace();
        }

        // Print a message that reads user data is complete
        System.out.println("===\n=== read user data - complete\n===");

        // Returns a list containing user data
        return userDataArrayList;
    }
}
