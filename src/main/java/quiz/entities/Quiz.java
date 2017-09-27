package quiz.entities;

/**
 * Created by Magnus on 26.09.2017.
 *
 * @author Magnus Conrad Hyll (magnus@hyll.no)
 */
public class Quiz {

    private int id;
    private String name;
    private long startTime;
    private Question[] questions;
    private Score[] scores;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public Question[] getQuestions() {
        return questions;
    }

    public void setQuestions(Question[] questions) {
        this.questions = questions;
    }

    public Score[] getScores() {
        return scores;
    }

    public void setScores(Score[] scores) {
        this.scores = scores;
    }
}
