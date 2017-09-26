package quiz.entities;

/**
 * Created by Magnus on 26.09.2017.
 *
 * @author Magnus Conrad Hyll (magnus@hyll.no)
 */
public class Question {
    private static int nextId = 1;
    private String text;
    private String[] alternatives;
    private int correct;
    private int time;
}
