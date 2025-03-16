package QuizSystem;

import xjtlu.cpt111.assignment.quiz.model.Question;
import xjtlu.cpt111.assignment.quiz.util.IOUtilities;

/**
 * This class is used to read the problem data.
 */
public class ReadQuestionsData {

    /**
     * Reads the topic data from the specified resource file.
     *
     * @return Read an array of questions
     */
    public static Question[] getQuestions() {
        try {
            System.out.println("===\n=== read questions - started\n==="); // Print a message to start reading the problem

            // Call the readQuestions method of the IOUtilities class to read the question data from the "resources/questionsBank" file
            Question[] questions = IOUtilities.readQuestions("resources/questionsBank");

            // Check that the array of questions read is empty or has a length of 0
            if (null == questions || questions.length == 0) {
                System.out.println("Questions is empty!"); // If the problem array is empty, print a message
            }

            return questions; // Returns an array of read questions
        } catch (Exception e) {
            e.printStackTrace(); // Capture and print stack trace information for any exceptions
        } finally {
            System.out.println("===\n=== read questions - complete\n==="); // Print a message reading the completion of the problem
        }

        return null; // Return null if an exception occurs or the problem array is empty
    }
}
