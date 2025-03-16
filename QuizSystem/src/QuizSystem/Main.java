package QuizSystem;

import xjtlu.cpt111.assignment.quiz.model.Question;

import java.util.List;

/**
 * File Name: Main.java
 * Function Description: The main class used to implement all the functions of the QuizSystem.
 * @author Yiguan Zhang
 * @author Yuanhao Cheng
 * @author Tianyi Dai
 * @author Keyao Li
 * @author Anqi Wang
 * @date November 27, 2024
 */

public class Main {
    public static void main(String[] args) {
        // Loading problem data
        Question[] questionArray = ReadQuestionsData.getQuestions();

        // Loading fractional data
        List<String[]> scoreDataArrayList = ReadScoreData.getScoreData();

        // Loading user data
        List<String[]> userDataArrayList = ReadUserData.getUserData();

        // Get a list of all topics
        List<String> topicList = SelectTopic.getTopicList(questionArray);

        // Perform user management to obtain the current login username
        String username = UserManagementSystem.userManagement(userDataArrayList, scoreDataArrayList);

        // If the username is not empty, continue with the following operations
        if (!username.isEmpty()) {
            while (true) {
                // Determine whether the user chooses to take the quiz
                if (IsTakingQuiz.isTakingQuiz()) {
                    // Users choose to take the quiz and choose a topic
                    String topic = SelectTopic.selectTopic(topicList);

                    // Displays and calculates question scores for that topic
                    int totalScore = QuizTaking.showQuestion(topic, questionArray);

                    // Print the total score of this quiz
                    System.out.println("\nThe score of this quiz is: " + totalScore);

                    // Writes the user's score to the score data list
                    scoreDataArrayList = QuizTaking.writeScore(username, topic, totalScore);

                    // Show leaderboard
                    ShowBoard.showBoard(username, topicList, questionArray, scoreDataArrayList);
                } else {
                    // The user opts out of taking the quiz and displays the leaderboard directly
                    ShowBoard.showBoard(username, topicList, questionArray, scoreDataArrayList);
                }
            }
        }
    }
}

