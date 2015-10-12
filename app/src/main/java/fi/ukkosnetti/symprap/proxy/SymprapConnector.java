package fi.ukkosnetti.symprap.proxy;

import fi.ukkosnetti.symprap.util.Constants;
import fi.ukkosnetti.symprap.webdata.SecuredRestBuilder;
import retrofit.RestAdapter;
import retrofit.android.AndroidApacheClient;
import retrofit.client.ApacheClient;

public class SymprapConnector {

    private static final String CLIENT_ID = "mobile";

    public static synchronized SymprapProxy login(String user, String pass) {
        return new SecuredRestBuilder()
                .setLoginEndpoint(Constants.SERVER_URL + SymprapProxy.TOKEN_PATH)
                .setUsername(user)
                .setPassword(pass)
                .setClientId(CLIENT_ID)
                .setClient(new AndroidApacheClient())
                .setEndpoint(Constants.SERVER_URL).setLogLevel(RestAdapter.LogLevel.FULL).build()
                .create(SymprapProxy.class);
    }
}
