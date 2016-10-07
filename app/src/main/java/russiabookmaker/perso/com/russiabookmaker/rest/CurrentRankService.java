package russiabookmaker.perso.com.russiabookmaker.rest;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import russiabookmaker.perso.com.russiabookmaker.model.Ranking;

/**
 * Created by versusmind on 27/09/2016.
 */

public interface CurrentRankService {
    @FormUrlEncoded
    @POST("currentRank.php")
    Call<Ranking> callCurrentRank(
            @Field("username") String username
    );

    public static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8888/DesktopRussiaBookMaker/webservices/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
}
