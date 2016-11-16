package russiabookmaker.perso.com.russiabookmaker.teams;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import russiabookmaker.perso.com.russiabookmaker.rest.RetrofitBuilder;
import russiabookmaker.perso.com.russiabookmaker.teams.Team;

/**
 * Created by versusmind on 20/10/2016.
 */

public interface TeamsService {
    @GET("getTeams.php")
    Call<List<Team>> callTeams();

    public static final Retrofit retrofit = RetrofitBuilder.setBaseUrl();
}
