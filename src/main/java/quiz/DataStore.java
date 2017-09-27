package quiz;

import quiz.entities.Question;
import quiz.entities.Quiz;
import quiz.entities.User;

import java.util.HashMap;

/**
 * Created by Magnus on 26.09.2017.
 *
 * @author Magnus Conrad Hyll (magnus@hyll.no)
 */
public class DataStore {
    private static int nextQuizId = 1;

    public static HashMap<Integer, Quiz> quizes = new HashMap<>(200);
    public static HashMap<Integer, User> users = new HashMap<>(200);
    public static HashMap<Integer, Question> questions = new HashMap<>(1000);

    static {
        Quiz quiz = new Quiz();
        quiz.setName("Test");
        quiz.setStartTime(792384);

        Question q1 = new Question();
        q1.setText("Hva er tallet?");
        q1.setAlternatives(new String[]{"1", "2", "3", "4"});
        q1.setCorrect(2);
        q1.setTime(123);

        Question q2 = new Question();
        q1.setText("Hva er navnet?");
        q1.setAlternatives(new String[]{"adfg", "wert", "sdfg", "dfgh"});
        q1.setCorrect(1);
        q1.setTime(123);

        quiz.setQuestions(new Question[] {q1, q2});
        addNewQuiz(quiz);
    }

    public static void addNewQuiz(Quiz quiz) {
        quiz.setId(nextQuizId++);
        quizes.put(quiz.getId(), quiz);
    }

}
