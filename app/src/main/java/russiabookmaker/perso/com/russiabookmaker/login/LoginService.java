package russiabookmaker.perso.com.russiabookmaker.login;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import russiabookmaker.perso.com.russiabookmaker.login.User;
import russiabookmaker.perso.com.russiabookmaker.rest.RetrofitBuilder;

/**
 * Created by versusmind on 06/09/16.
 */
public interface LoginService {
    @FormUrlEncoded
    @POST("login.php")
    Call<User> callLogin(
            @Field("usernameConnect") String usernameConnect,
            @Field("passwordConnect") String passwordConnect);

    public static final Retrofit retrofit = RetrofitBuilder.setBaseUrl();
}
