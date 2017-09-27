package quiz.jsonentities;

/**
 * Created by Magnus on 27.09.2017.
 *
 * @author Magnus Conrad Hyll (magnus@hyll.no)
 */
public class JSONAnswer {

    private int alternativeNum;
    private String player;

    public JSONAnswer() {
    }

    public int getAlternativeNum() {
        return alternativeNum;
    }

    public void setAlternativeNum(int alternativeNum) {
        this.alternativeNum = alternativeNum;
    }

    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }
}
