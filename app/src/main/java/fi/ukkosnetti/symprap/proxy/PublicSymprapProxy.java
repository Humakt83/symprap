package fi.ukkosnetti.symprap.proxy;

import fi.ukkosnetti.symprap.dto.User;
import fi.ukkosnetti.symprap.dto.UserCreate;
import retrofit.client.Response;
import retrofit.http.Body;
import retrofit.http.POST;

public interface PublicSymprapProxy {

    @POST("/user/register")
    User registerUser(@Body UserCreate userCreate);
}
