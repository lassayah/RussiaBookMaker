package russiabookmaker.perso.com.russiabookmaker.rest;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import russiabookmaker.perso.com.russiabookmaker.model.Top4;

/**
 * Created by versusmind on 19/10/2016.
 */

public interface Top4Service {
    @FormUrlEncoded
    @POST("getTop4.php")
    Call<Top4> callTop4(
            @Field("username") String username
    );

    public static final Retrofit retrofit = RetrofitBuilder.setBaseUrl();
}
