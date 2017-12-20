package russiabookmaker.perso.com.russiabookmaker.login;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import russiabookmaker.perso.com.russiabookmaker.rest.RetrofitBuilder;

/**
 * Created by versusmind on 17/11/2016.
 */

public interface SubscriptionService {
    @FormUrlEncoded
    @POST("subscription.php")
    Call<User> callSusbcription(
            @Field("username") String usernameConnect,
            @Field("password") String passwordConnect);

    public static final Retrofit retrofit = RetrofitBuilder.setBaseUrl();
}
