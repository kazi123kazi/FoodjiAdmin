package admin.example.foodie.org.example.foodie.apifetch;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

class AuthenticationInterceptor implements Interceptor {


    private static String authToken;

    public AuthenticationInterceptor(String authToken) {

        this.authToken = authToken;


    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();

        Request.Builder builder = original.newBuilder().addHeader("Authorizzation", "Bearer " + authToken);

        Request request = builder.build();

        return chain.proceed(request);
    }
}
