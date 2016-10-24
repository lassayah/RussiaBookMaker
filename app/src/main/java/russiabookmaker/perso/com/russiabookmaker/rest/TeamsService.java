package russiabookmaker.perso.com.russiabookmaker.rest;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import russiabookmaker.perso.com.russiabookmaker.model.Team;

/**
 * Created by versusmind on 20/10/2016.
 */

public interface TeamsService {
    @GET("getTeams.php")
    Call<List<Team>> callTeams();

    public static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8888/DesktopRussiaBookMaker/webservices/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
}
