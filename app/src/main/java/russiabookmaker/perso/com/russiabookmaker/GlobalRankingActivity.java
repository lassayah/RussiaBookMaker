package russiabookmaker.perso.com.russiabookmaker;

import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import russiabookmaker.perso.com.russiabookmaker.adapter.RankingAdapter;
import russiabookmaker.perso.com.russiabookmaker.model.Ranking;
import russiabookmaker.perso.com.russiabookmaker.rest.RankingService;

public class GlobalRankingActivity extends AppCompatActivity implements CurrentRankFragment.OnCurrentRankFragmentInteractionListener {

    private RecyclerView rankingList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_global_ranking);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        rankingList = (RecyclerView) findViewById(R.id.rankRecyclerView);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        rankingList.setHasFixedSize(true);

        // use a linear layout manager
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        rankingList.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        RankingService rankingService = RankingService.retrofit.create(RankingService.class);
        final Call<List<Ranking>> call = rankingService.callRanking();
        call.enqueue(new Callback<List<Ranking>>() {
            @Override
            public void onResponse(Call<List<Ranking>> call, Response<List<Ranking>> response) {
                ArrayList<Ranking> rankList = new ArrayList<Ranking>();
                for (int i = 0; i < response.body().size(); i++)
                {
                    Ranking ranking = new Ranking();
                    ranking.setUser(response.body().get(i).getUser());
                    ranking.setRank(response.body().get(i).getRank());
                    rankList.add(ranking);
                }
                RankingAdapter rankingAdapter = new RankingAdapter(rankList);
                rankingList.setAdapter(rankingAdapter);
            }
            @Override
            public void onFailure(Call<List<Ranking>> call, Throwable t) {
                Log.d("callko", t.getMessage());
            }
        });
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        CurrentRankFragment crFragment = new CurrentRankFragment();
        fragmentTransaction.add(R.id.global_ranking_container, crFragment);
        fragmentTransaction.commit();
        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
    }

    @Override
    public void onCurrentRankFragmentInteraction(Uri uri) {

    }
}
