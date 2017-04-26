package py.edu.facitec.githubclient;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by virux on 19/04/17.
 */

public interface UserService {

    @GET("/users/{login}")
    void getUsuario(@Path("login") String login, Callback<User> callback);

}
