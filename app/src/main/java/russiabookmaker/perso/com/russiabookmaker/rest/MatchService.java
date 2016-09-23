package russiabookmaker.perso.com.russiabookmaker.rest;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import russiabookmaker.perso.com.russiabookmaker.model.Match;

/**
 * Created by versusmind on 14/09/16.
 */
public interface MatchService {
    @FormUrlEncoded
    @POST("bet.php")
    Call<List<Match>> callMatch(
            @Field("numberGroupMatch") String numberGroupMatch,
            @Field("username") String username,
            @Field("filter") String filter
    );

    public static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8888/DesktopRussiaBookMaker/webservices/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
}
