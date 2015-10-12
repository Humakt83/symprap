package fi.ukkosnetti.symprap.proxy;

import fi.ukkosnetti.symprap.dto.User;
import retrofit.http.GET;
import retrofit.http.Path;

public interface SymprapProxy {

    public static final String TOKEN_PATH = "/oauth/token";

    @GET("/user/{username}")
    public User getUserInfo(@Path("username") String userName);

}
