package fi.ukkosnetti.symprap.proxy;

import android.content.Context;
import android.content.Intent;

import com.squareup.okhttp.OkHttpClient;

import java.security.cert.CertificateException;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import fi.ukkosnetti.symprap.LoginActivity;
import fi.ukkosnetti.symprap.util.Constants;
import fi.ukkosnetti.symprap.webdata.SecuredRestBuilder;
import retrofit.RestAdapter;
import retrofit.android.AndroidApacheClient;
import retrofit.client.ApacheClient;
import retrofit.client.Client;
import retrofit.client.OkClient;

public class SymprapConnector {

    private static final String CLIENT_ID = "mobile";

    private static SymprapProxy proxy;

    public static synchronized SymprapProxy proxy(Context ctx) {
        if (proxy == null) {
            ctx.startActivity(new Intent(ctx, LoginActivity.class));
            return null;
        }
        return proxy;
    }

    public static synchronized SymprapProxy login(String user, String pass) {
        proxy = new SecuredRestBuilder()
                .setLoginEndpoint(Constants.SERVER_URL + SymprapProxy.TOKEN_PATH)
                .setUsername(user)
                .setPassword(pass)
                .setClientId(CLIENT_ID)
                .setClient(getOkClient())
                .setEndpoint(Constants.SERVER_URL).setLogLevel(RestAdapter.LogLevel.FULL).build()
                .create(SymprapProxy.class);
        return proxy;
    }

    public static OkHttpClient getUnsafeOkHttpClient() {

        try {
            // Create a trust manager that does not validate certificate chains
            final TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
                @Override
                public void checkClientTrusted(
                        java.security.cert.X509Certificate[] chain,
                        String authType) throws CertificateException {
                }

                @Override
                public void checkServerTrusted(
                        java.security.cert.X509Certificate[] chain,
                        String authType) throws CertificateException {
                }

                @Override
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
            } };

            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts,
                    new java.security.SecureRandom());
            // Create an ssl socket factory with our all-trusting manager
            final SSLSocketFactory sslSocketFactory = sslContext
                    .getSocketFactory();

            OkHttpClient okHttpClient = new OkHttpClient();
            okHttpClient.setSslSocketFactory(sslSocketFactory);
            okHttpClient.setHostnameVerifier(new HostnameVerifier() {

                @Override
                public boolean verify(String hostname, SSLSession session) {
                    if (hostname.equalsIgnoreCase("10.0.2.2"))
                        return true;
                    else
                        return false;
                }
            });

            return okHttpClient;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public static OkClient getOkClient (){
        OkHttpClient client1 = getUnsafeOkHttpClient();
        return new OkClient(client1);
    }

}
