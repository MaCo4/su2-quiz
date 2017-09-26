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
    public static HashMap<Integer, Quiz> quizes = new HashMap<>(200);
    public static HashMap<Integer, User> users = new HashMap<>(200);
    public static HashMap<Integer, Question> questions = new HashMap<>(1000);

    static {
        User magnus = new User("Magnus");
        users.put(magnus.getId(), magnus);
    }

}
