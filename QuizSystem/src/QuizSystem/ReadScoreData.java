package QuizSystem;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is used to read score data.
 */
public class ReadScoreData {

    /**
     * Reads score data from the specified file.
     *
     * @return A list containing the score data, where each element is an array of strings
     */
    public static List<String[]> getScoreData() {
        System.out.println(); // Print a blank line to separate the output

        // Create a list to store the read score data
        List<String[]> scoreDataArrayList = new ArrayList<>();

        // Define the path to the score data file
        String filename = "resources/score.txt";

        // Prints a message to start reading the score data
        System.out.println("===\n=== read score data - started\n===");

        try {
            // Creates the FileReader object to read the file
            FileReader fileReader = new FileReader(filename);

            // Creates a BufferedReader object to read the contents of the file line by line
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            // Read the file contents line by line
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                // Separate the contents of each line with commas and Spaces to get an array of strings
                String[] scoreData = line.split(", ");

                // Adds an array of strings to the list
                scoreDataArrayList.add(scoreData);
            }

            // Print a message that read the score data is complete
            System.out.println("read users information from file " + filename);
        } catch (IOException ioe) {
            // Capture and print stack trace information for any I/O exceptions
            ioe.printStackTrace();
        } finally {
            // Print a message that read the score data is complete
            System.out.println("===\n=== read score data - complete\n===");
        }

        // Returns a list containing the score data
        return scoreDataArrayList;
    }
}
