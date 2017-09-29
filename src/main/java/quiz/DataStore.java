package quiz;

import quiz.entities.Question;
import quiz.entities.Quiz;

import java.util.HashMap;

/**
 * Created by Magnus on 26.09.2017.
 *
 * @author Magnus Conrad Hyll (magnus@hyll.no)
 */
public class DataStore {
    private static int nextQuizId = 1;

    public static HashMap<Integer, Quiz> quizes = new HashMap<>(200);

    static {
        Quiz quiz = new Quiz();
        quiz.setName("Eksempelquiz");
        quiz.setStartTime(System.currentTimeMillis() / 1000 + 60);

        Question q1 = new Question();
        q1.setText("Hva er tallet?");
        q1.setAlternatives(new String[]{"1", "2", "3", "4"});
        q1.setCorrect(2);
        q1.setTime(20);
        q1.setImg("http://ny.telespor.no/wp-content/uploads/2012/12/manedens-bilde-april-09.jpg");

        Question q2 = new Question();
        q2.setText("Hva er navnet?");
        q2.setAlternatives(new String[]{"Sigrid", "Steinar", "Susanne", "Svein"});
        q2.setCorrect(0);
        q2.setTime(30);

        quiz.setQuestions(new Question[] {q1, q2});
        addNewQuiz(quiz);
    }

    public static void addNewQuiz(Quiz quiz) {
        quiz.setId(nextQuizId++);
        quizes.put(quiz.getId(), quiz);
    }

}
