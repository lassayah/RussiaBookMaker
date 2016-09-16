package russiabookmaker.perso.com.russiabookmaker.rest;


import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import russiabookmaker.perso.com.russiabookmaker.model.Ranking;

/**
 * Created by versusmind on 09/09/16.
 */
public interface RankingService {

    @GET("points.php")
    Call<List<Ranking>> callRanking();

    public static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://assayah.com/Brazil/webservices/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
}
