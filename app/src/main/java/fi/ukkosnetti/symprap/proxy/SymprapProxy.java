package fi.ukkosnetti.symprap.proxy;

import java.util.List;

import fi.ukkosnetti.symprap.dto.Answer;
import fi.ukkosnetti.symprap.dto.AnswerGet;
import fi.ukkosnetti.symprap.dto.Disease;
import fi.ukkosnetti.symprap.dto.Question;
import fi.ukkosnetti.symprap.dto.User;
import retrofit.client.Response;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;

/**
 * REST methods of symprap-server used by this client that require authentication
 */
public interface SymprapProxy {

    String TOKEN_PATH = "/oauth/token";

    @GET("/user/byusername/{username}")
    User getUserInfo(@Path("username") String username);

    @PUT("/user/follower/add/{username}")
    Response addFollower(@Path("username") String username);

    @PUT("/user/follower/remove/{username}")
    Response removeFollower(@Path("username") String username);

    @GET("/question/fordisease/{diseaseid}")
    List<Question> getQuestionsForDisease(@Path("diseaseid") Long diseaseId);

    @POST("/answer/")
    Response submitAnswers(@Body List<Answer> answers);

    @GET("/answer/byuser/{username}")
    List<AnswerGet> getAnswersForUser(@Path("username") String username);

    @GET("/disease/byuser/{username}")
    List<Disease> getDiseasesOfUser(@Path("username") String username);

}
