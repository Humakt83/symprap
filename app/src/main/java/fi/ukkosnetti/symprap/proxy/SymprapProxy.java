package fi.ukkosnetti.symprap.proxy;

import java.util.List;

import fi.ukkosnetti.symprap.dto.Answer;
import fi.ukkosnetti.symprap.dto.Question;
import fi.ukkosnetti.symprap.dto.User;
import retrofit.client.Response;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;

public interface SymprapProxy {

    public static final String TOKEN_PATH = "/oauth/token";

    @GET("/user/byusername/{username}")
    User getUserInfo(@Path("username") String userName);

    @GET("/question/fordisease/{diseaseid}")
    List<Question> getQuestionsForDisease(@Path("diseaseid") Long diseaseId);

    @POST("/answer/")
    Response submitAnswers(@Body List<Answer> answers);

}
