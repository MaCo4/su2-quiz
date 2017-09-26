package quiz.entities;

/**
 * Created by Magnus on 26.09.2017.
 *
 * @author Magnus Conrad Hyll (magnus@hyll.no)
 */
public class User {
    private static int nextId = 1;

    private int id;
    private String name;

    public User(String name) {
        id = nextId++;
        this.name = name;
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
}
