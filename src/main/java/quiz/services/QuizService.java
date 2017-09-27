package quiz.services;

import quiz.DataStore;
import quiz.entities.Quiz;
import quiz.jsonentities.JSONQuiz;
import quiz.jsonentities.JSONResult;

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
    @Produces(MediaType.APPLICATION_JSON)
    public JSONResult addQuiz(Quiz newQuiz) {
        DataStore.addNewQuiz(newQuiz);

        JSONResult result = new JSONResult();
        result.setResult("success");
        return result;
    }

    @GET
    @Path("/{quizId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Quiz getQuiz(@PathParam("quizId") String quizId) {
        int id;
        try {
            id = Integer.parseInt(quizId);
        } catch (NumberFormatException ex) {
            throw new NotFoundException();
        }

        if (!DataStore.quizes.containsKey(id)) {
            throw new NotFoundException();
        }
        return DataStore.quizes.get(id);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Quiz[] getAllQuizes() {
        return DataStore.quizes.values().toArray(new Quiz[0]);
    }
}
