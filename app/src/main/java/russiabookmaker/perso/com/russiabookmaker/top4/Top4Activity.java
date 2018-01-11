package russiabookmaker.perso.com.russiabookmaker.top4;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import russiabookmaker.perso.com.russiabookmaker.R;
import russiabookmaker.perso.com.russiabookmaker.database.DBHelper;
import russiabookmaker.perso.com.russiabookmaker.teams.Team;
import russiabookmaker.perso.com.russiabookmaker.teams.TeamsService;

public class Top4Activity extends AppCompatActivity {

    private RecyclerView top4List;
    //private GridView top4Grid;
    private ArrayList<Team> tList;
    private DBHelper mydb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        setContentView(R.layout.activity_top4);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //top4Grid = (GridView) findViewById(R.id.top4Grid);
        top4List = (RecyclerView) findViewById(R.id.top4List);
        mydb = new DBHelper(getApplicationContext());
        mydb.getWritableDatabase();
        callService();

        /*top4List = (RecyclerView) findViewById(R.id.top4List);
        ArrayList<String> top4Teams = new ArrayList<String>();
        top4Teams.add("Premier");
        top4Teams.add("Deuxième");
        top4Teams.add("Troisième");
        top4Teams.add("Quatrième");
        Top4Adapter top4Adapter = new Top4Adapter(top4Teams);
        top4List.setAdapter(top4Adapter);*/
    }

    private void callService(){
        TeamsService teamsService = TeamsService.retrofit.create(TeamsService.class);
        final Call<List<Team>> call = teamsService.callTeams();
        call.enqueue(new Callback<List<Team>>(){
            @Override
            public void onResponse(Call<List<Team>> call, Response<List<Team>> response) {
                tList = new ArrayList<Team>();
                    for (int i = 0; i < response.body().size(); i++) {
                        Team team = response.body().get(i);
                        if (mydb.getTeam(team.getId()) == null) {
                            mydb.insertTeam(team.getName(), team.getFlag(), team.getId());
                        }
                        else
                        {
                            mydb.updateTeam(team.getId(), team.getName(), team.getFlag());
                        }
                        tList.add(team);
                    }
                //Top4Adapter top4Adapter = new Top4Adapter(tList);
                //top4List.setAdapter(top4Adapter);
            }
            @Override
            public void onFailure(Call<List<Team>> call, Throwable t) {
                Log.d("callko", t.getMessage());
                Log.d("callko", t.getCause().toString());
            }
        });
    }

}
