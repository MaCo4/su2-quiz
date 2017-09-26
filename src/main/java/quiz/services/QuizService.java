package quiz.services;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * Created by Magnus on 13.09.2017.
 *
 * @author Magnus Conrad Hyll (magnus@hyll.no)
 */
@Path("/quiz")
public class QuizService {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void addQuiz(Quiz newMsg) {
        msg = newMsg;
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getMessage() {
        return msg;
    }
}
