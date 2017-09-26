package quiz.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * Created by Magnus on 26.09.2017.
 *
 * @author Magnus Conrad Hyll (magnus@hyll.no)
 */
public class Quiz {
    private static int nextId = 1;

    private int id;
    private String name;
    private LocalDateTime startTime;
    private ArrayList<Question> questions;

    public Quiz(String name, LocalDateTime startTime, ArrayList<Question> questions) {
        id = nextId++;
        this.name = name;
        this.startTime = startTime;
        this.questions = questions;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public ArrayList<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(ArrayList<Question> questions) {
        this.questions = questions;
    }
}
