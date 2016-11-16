package russiabookmaker.perso.com.russiabookmaker.bet;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import russiabookmaker.perso.com.russiabookmaker.bet.Match;
import russiabookmaker.perso.com.russiabookmaker.rest.RetrofitBuilder;

/**
 * Created by versusmind on 19/09/16.
 */
public interface BetService {
    @FormUrlEncoded
    @POST("makebet.php")
    Call<Match> callMatch(
            @Field("id") int id,
            @Field("username") String username,
            @Field("matchTime") String matchTime,
            @Field("resultBet") int resultBet
    );

    public static final Retrofit retrofit = RetrofitBuilder.setBaseUrl();

}
