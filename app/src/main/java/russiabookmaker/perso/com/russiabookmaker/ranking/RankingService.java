package russiabookmaker.perso.com.russiabookmaker.ranking;


import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import russiabookmaker.perso.com.russiabookmaker.ranking.Ranking;
import russiabookmaker.perso.com.russiabookmaker.rest.RetrofitBuilder;

/**
 * Created by versusmind on 09/09/16.
 */
public interface RankingService {

    @GET("points.php")
    Call<List<Ranking>> callRanking();

    public static final Retrofit retrofit = RetrofitBuilder.setBaseUrl();
}
