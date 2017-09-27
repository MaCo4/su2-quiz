package quiz.entities;

/**
 * Created by Magnus on 26.09.2017.
 *
 * @author Magnus Conrad Hyll (magnus@hyll.no)
 */
public class Question {

    private int id;
    private String text;
    private String[] alternatives;
    private int correct;
    private int time;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String[] getAlternatives() {
        return alternatives;
    }

    public void setAlternatives(String[] alternatives) {
        this.alternatives = alternatives;
    }

    public int getCorrect() {
        return correct;
    }

    public void setCorrect(int correct) {
        this.correct = correct;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }
}
