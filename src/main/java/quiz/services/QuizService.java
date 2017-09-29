package quiz.services;

import quiz.DataStore;
import quiz.entities.PlayerAnswer;
import quiz.entities.Question;
import quiz.entities.Quiz;
import quiz.entities.Score;
import quiz.entities.JSONResult;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Objects;

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

    @POST
    @Path("/{quizId}/question/{questionIndex}/answer")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public JSONResult postAnswerToQuestion(@PathParam("quizId") String strQuizId,
                                           @PathParam("questionIndex") String strQuestionIndex,
                                           PlayerAnswer answer) {
        try {
            int quizId, questionIndex;
            quizId = Integer.parseInt(strQuizId);
            questionIndex = Integer.parseInt(strQuestionIndex);

            if (!DataStore.quizes.containsKey(quizId)) {
                throw new NotFoundException();
            }
            Quiz quiz = DataStore.quizes.get(quizId);
            Question question = quiz.getQuestions()[questionIndex];

            ensurePlayerQuizScore(quiz, answer.getPlayer());

            if (answer.getAnswer() == question.getCorrect()) {
                Score score = getScoreFor(quiz, answer.getPlayer());
                score.setScore(score.getScore() + 1);
            }

            JSONResult result = new JSONResult();
            result.setResult("success");
            return result;

        } catch (Exception ex) {
            // Hvis noe feil skjer svarer vi bare med 404
            throw new NotFoundException();
        }
    }

    @GET
    @Path("/{quizId}/scores")
    @Produces(MediaType.APPLICATION_JSON)
    public Score[] getScores(@PathParam("quizId") String strQuizId) {
        try {
            int quizId = Integer.parseInt(strQuizId);

            if (!DataStore.quizes.containsKey(quizId)) {
                throw new NotFoundException();
            }
            Quiz quiz = DataStore.quizes.get(quizId);
            return quiz.getScores();

        } catch (Exception ex) {
            throw new NotFoundException();
        }
    }

    private Score getScoreFor(Quiz quiz, String player) {
        if (quiz.getScores() == null) {
            quiz.setScores(new Score[0]);
            return null;
        }

        for (Score score : quiz.getScores()) {
            if (Objects.equals(score.getPlayer(), player)) {
                return score;
            }
        }
        return null;
    }

    private void ensurePlayerQuizScore(Quiz quiz, String player) {
        if (getScoreFor(quiz, player) == null) {
            Score[] newScores = new Score[quiz.getScores().length + 1];
            System.arraycopy(quiz.getScores(), 0, newScores, 0, quiz.getScores().length);

            Score playerScore = new Score();
            playerScore.setPlayer(player);
            playerScore.setScore(0);
            newScores[quiz.getScores().length] = playerScore;

            quiz.setScores(newScores);
        }
    }
}
