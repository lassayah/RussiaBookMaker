package russiabookmaker.perso.com.russiabookmaker.top4;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import russiabookmaker.perso.com.russiabookmaker.rest.RetrofitBuilder;
import russiabookmaker.perso.com.russiabookmaker.top4.Top4;

/**
 * Created by versusmind on 19/10/2016.
 */

public interface Top4Service {
    @FormUrlEncoded
    @POST("getTop4.php")
    Call<Top4> callTop4(
            @Field("username") String username
    );

    @FormUrlEncoded
    @POST("setTop4.php")
    Call<Object> callSetTop4(
            @Field("username") String username,
            @Field("team1") String team1,
            @Field("team2") String team2,
            @Field("team3") String team3,
            @Field("team4") String team4

    );

    public static final Retrofit retrofit = RetrofitBuilder.setBaseUrl();
}
