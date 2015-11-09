package fi.ukkosnetti.symprap.proxy;

import java.util.List;

import fi.ukkosnetti.symprap.dto.Disease;
import fi.ukkosnetti.symprap.dto.User;
import fi.ukkosnetti.symprap.dto.UserCreate;
import retrofit.client.Response;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;

/**
 * Public REST methods of symprap-server that does not require authentication.
 */
public interface PublicSymprapProxy {

    @POST("/user/register")
    User registerUser(@Body UserCreate userCreate);

    @GET("/disease/all")
    List<Disease> diseases();
}
