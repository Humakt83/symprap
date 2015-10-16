package fi.ukkosnetti.symprap.proxy;

import java.util.List;

import fi.ukkosnetti.symprap.dto.Question;
import fi.ukkosnetti.symprap.dto.User;
import retrofit.http.GET;
import retrofit.http.Path;

public interface SymprapProxy {

    public static final String TOKEN_PATH = "/oauth/token";

    @GET("/user/byusername/{username}")
    public User getUserInfo(@Path("username") String userName);

    @GET("/question/fordisease/{diseaseid}")
    public List<Question> getQuestionsForDisease(@Path("diseaseid") Long diseaseId);

}
