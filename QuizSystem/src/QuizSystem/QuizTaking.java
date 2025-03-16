package QuizSystem;

import xjtlu.cpt111.assignment.quiz.model.Difficulty;
import xjtlu.cpt111.assignment.quiz.model.Option;
import xjtlu.cpt111.assignment.quiz.model.Question;

import java.io.*;
import java.util.*;

public class QuizTaking {

    /**
     * This method allows the user to select the number of questions for the quiz.
     *
     * @return the number of questions
     */
    public static int questionNumSelect() {
        int questionNum;
        Scanner sc = new Scanner(System.in);
        while (true) {
            try {
                System.out.println();
                System.out.println("**************************************");
                System.out.println("Please choose the number of questions: ");
                System.out.println("A. 5  B. 10");
                System.out.println("C. 20 D. 25");
                System.out.println("**************************************");
                System.out.print("Enter your choice: ");
                String choice = sc.nextLine();
                // Use a switch case to map the user's choice to the corresponding number of questions.
                switch (choice) {
                    case "A":
                        questionNum = 5;
                        return questionNum;
                    case "B":
                        questionNum = 10;
                        return questionNum;
                    case "C":
                        questionNum = 20;
                        return questionNum;
                    case "D":
                        questionNum = 25;
                        return questionNum;
                    default:
                        throw new IllegalArgumentException("Invalid choice, please try again");
                }
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            } // output the error information
        }
    }

    /**
     * This method displays the questions and handles the quiz logic.
     * totalScore: the ultimate score
     * singleQuestionScore: the mark of one question
     *
     * @param topic-the     topic of questions
     * @param questions-the object of Question[]
     * @return the ultimate score
     */
    public static int showQuestion(String topic, Question[] questions) {
        int questionNum = QuizTaking.questionNumSelect();
        int singleQuestionScore = 100 / questionNum; // Calculate score per question.
        int totalScore = 0;

        // Define possible difficulty levels.
        String[] difficultyList = new String[]{"EASY", "MEDIUM", "HARD", "VERY_HARD"};
        List<Question> questionList = new ArrayList<>();
        List<Integer> questionStatus = new ArrayList<>();

        // Filter questions by topic.
        for (Question question : questions) {
            if (question.getTopic().equals(topic)) {
                questionList.add(question);
            }
        }

        // Prepare a list for difficulties and initial question status.
        List<Difficulty> questionDifficultyList = new ArrayList<>();
        for (Question question : questionList) {
            questionStatus.add(0); // Mark questions as not yet answered.
            questionDifficultyList.add(question.getDifficulty());
        }

        Random random = new Random();
        // Randomly select questions based on difficulty and ensure no repetition.
        for (int i = 0; i < questionNum; i++) {
            while (true) {
                String difficulty = difficultyList[random.nextInt(4)];
                for (int j = 0; j < questionList.size(); j++) {
                    if (difficulty.equals(questionDifficultyList.get(j).toString()) && questionStatus.get(j) == 0) {
                        questionStatus.set(j, 1); // Mark the question as selected.
                        List<Option> optionList = new ArrayList<>();
                        Collections.addAll(optionList, questionList.get(j).getOptions());
                        Collections.shuffle(optionList); // Shuffle options to randomize their order.
                        questionList.get(j).setOptions(optionList);
                        System.out.println();
                        System.out.println("Question #" + (i + 1) + " " + questionList.get(j).toString());
                        int countCorrectNum = answerQuestion(questionList.get(j)); // Get the user's answer for the question.

                        // Award points based on the number of correct answers for multi-choice or single-choice questions.
                        if (numOfAnswer(optionList) == 1) {
                            if (countCorrectNum == 1) {
                                totalScore += singleQuestionScore;
                            }
                        } else {
                            if (countCorrectNum > 0) {
                                totalScore += Math.round((float) (singleQuestionScore * countCorrectNum) / numOfAnswer(optionList));
                            }
                        }
                        break;
                    } else if (j == questionList.size() - 1) {
                        break;
                    }
                }
                break;
            }
        }
        return totalScore;
    }

    /**
     * This method handles answering a question by the user, either single or multi-choice.
     * optionNum: the number of options
     * countCorrectNum: the number of correct answer
     *
     * @param question-the object of Question[]
     * @return the number of correct answer
     */
    public static int answerQuestion(Question question) {
        List<Option> optionList = new ArrayList<>();
        Collections.addAll(optionList, question.getOptions());
        Scanner sc = new Scanner(System.in);
        int optionNum = optionList.size();

        // If it's a single-choice question, allow only one answer.
        if (numOfAnswer(optionList) == 1) {
            System.out.println("This is a single-choice question.");
            while (true) {
                System.out.print("Please enter your Answer: ");
                try {
                    int answer = sc.nextInt();
                    if (answer > 0 && answer <= optionNum) {
                        if (optionList.get(answer - 1).isCorrectAnswer()) {
                            return 1; // Correct answer.
                        } else return 0; // Incorrect answer.
                    } else {
                        throw new IllegalArgumentException("Invalid answer, please try again");
                    }
                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                } catch (InputMismatchException e) {
                    System.out.println("Invalid answer, please try again");
                    sc.next();
                }
            }
        } else {
            // If it's a multi-choice question, allow multiple answers.
            System.out.println("This is a multi-choice question. Separate the answers with spaces.");
            while (true) {
                System.out.print("Please enter your Answer: ");
                int countCorrectNum = 0;
                try {
                    String answer = sc.nextLine();
                    String[] answerArray = answer.split(" ");
                    if (answerArray.length > optionNum) {
                        throw new IllegalArgumentException("The number of answers is too much, please try again");
                    }
                    for (String s : answerArray) {
                        int answerInt = Integer.parseInt(s);
                        if (answerInt > 0 && answerInt <= optionNum) {
                            if (optionList.get(answerInt - 1).isCorrectAnswer()) {
                                countCorrectNum++;
                            } else return 0; // Incorrect answer.
                        } else {
                            throw new IllegalArgumentException("Invalid answer, please try again");
                        }
                    }
                    return countCorrectNum; // Return the number of correct answers.
                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                } catch (InputMismatchException e) {
                    System.out.println("Invalid answer, please try again");
                    sc.next();
                }
            }
        }
    }

    /**
     * This helper method counts the number of correct answers from the options.
     *
     * @param optionList-
     * @return the number of correct question
     */
    private static int numOfAnswer(List<Option> optionList) {
        int count = 0;
        for (Option option : optionList) {
            if (option.isCorrectAnswer()) {
                count++;
            }
        }
        return count;
    }

    /**
     * This method writes the score to a file and returns the updated score data.
     *
     * @param name-receive the name of user
     * @param topic-the    topic of the question
     * @param score-the    score of the question
     * @return the updated score data
     */
    public static List<String[]> writeScore(String name, String topic, int score) {
        List<String[]> newScoreDataArrayList = new ArrayList<>();
        try {
            // Open the score file and read its content.
            FileWriter fileWriter = new FileWriter("resources/score.txt", true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            FileReader fileReader = new FileReader("resources/score.txt");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            int count = 1;
            while (true) {
                String line = bufferedReader.readLine();
                if (line != null) {
                    String[] data = line.split(", ");
                    newScoreDataArrayList.add(data);
                    // Check if the score already exists for the given user and topic.
                    if (("username: " + name).equals(data[0]) && ("topic: " + topic).equals(data[1])) {
                        count++;
                    }
                } else break;
            }
            // Create a new score entry.
            String newScoreData = "username: " + name + ", topic: " + topic + ", score: " + String.valueOf(score) + ", count: " + String.valueOf(count);
            newScoreDataArrayList.add(newScoreData.split(", "));
            // Write the new score entry to the file.
            bufferedWriter.write(newScoreData);
            bufferedWriter.newLine();
            bufferedWriter.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return newScoreDataArrayList; // Return the updated score data.
    }
}
