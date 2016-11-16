package russiabookmaker.perso.com.russiabookmaker;



import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.annotation.IdRes;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import russiabookmaker.perso.com.russiabookmaker.bet.BetActivity;
import russiabookmaker.perso.com.russiabookmaker.bet.BetDetailsActivity;
import russiabookmaker.perso.com.russiabookmaker.bet.BetDetailsFragment;
import russiabookmaker.perso.com.russiabookmaker.database.DBHelper;
import russiabookmaker.perso.com.russiabookmaker.login.LoginActivity;
import russiabookmaker.perso.com.russiabookmaker.teams.Team;
import russiabookmaker.perso.com.russiabookmaker.ranking.CurrentRankFragment;
import russiabookmaker.perso.com.russiabookmaker.ranking.GlobalRankingFragment;
import russiabookmaker.perso.com.russiabookmaker.teams.TeamsService;
import russiabookmaker.perso.com.russiabookmaker.teams.CategoryFragment;
import russiabookmaker.perso.com.russiabookmaker.teams.MatchOfDayFragment;
import russiabookmaker.perso.com.russiabookmaker.top4.EditTop4Fragment;
import russiabookmaker.perso.com.russiabookmaker.top4.Top4Fragment;

/**
 * Created by versusmind on 05/09/16.
 */
public class HomeActivity extends AppCompatActivity implements MatchOfDayFragment.OnMatchOfDayFragmentInteractionListener,
        CurrentRankFragment.OnCurrentRankFragmentInteractionListener, BetDetailsFragment.OnFragmentInteractionListener,
        CategoryFragment.OnCategoryFragmentInteractionListener, Top4Fragment.OnFragmentInteractionListener,
        DashboardFragment.OnFragmentInteractionListener, GlobalRankingFragment.OnFragmentInteractionListener,
        EditTop4Fragment.OnFragmentInteractionListener {

    String pseudo = "";
    Button betButton;
    private Button goToTop4;
    private Button goToRanking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        populateTeams();

        /*TextView bonjourLogin = (TextView)findViewById(R.id.bonjourLogin);
        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences(getString(R.string.login), Context.MODE_PRIVATE);
        pseudo = sharedPref.getString(getString(R.string.login), "user");
        bonjourLogin.setText("Bonjour " + pseudo);
        betButton = (Button)findViewById(R.id.betButton);

        betButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, BetActivity.class);
                startActivity(intent);
            }
        });

        goToRanking = (Button)findViewById(R.id.currentRankButton);

        goToRanking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, GlobalRankingActivity.class);
                startActivity(intent);
            }
        });

        goToTop4 = (Button) findViewById(R.id.top4Button);

        goToTop4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, Top4Activity.class);
                startActivity(intent);
            }
        });

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        CurrentRankFragment crFragment = new CurrentRankFragment();

        fragmentTransaction.add(R.id.currentRank_container, crFragment);

        CategoryFragment cFragment = CategoryFragment.newInstance(0, "day");
        fragmentTransaction.add(R.id.matchOfDay_container, cFragment);

        Top4Fragment t4Fragment = new Top4Fragment();
        fragmentTransaction.add(R.id.top4_container, t4Fragment);
        fragmentTransaction.commit();*/

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        DashboardFragment dashboardFragment = new DashboardFragment();
        fragmentTransaction.add(R.id.contentContainer, dashboardFragment);
        fragmentTransaction.commit();

        BottomBar bottomBar = (BottomBar) findViewById(R.id.bottomBar);
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                if (tabId == R.id.tab_favorites) {
                    // The tab with id R.id.tab_favorites was selected,
                    // change your content accordingly.
                    DashboardFragment dashboardFragment = new DashboardFragment();
                    fragmentTransaction.replace(R.id.contentContainer, dashboardFragment);
                    fragmentTransaction.commit();
                }
                else if (tabId == R.id.tab_friends)
                {
                    CategoryFragment categoryFragment = new CategoryFragment();
                    fragmentTransaction.replace(R.id.contentContainer, categoryFragment);
                    fragmentTransaction.commit();
                }
                else if(tabId == R.id.tab_friends2)
                {
                    EditTop4Fragment editTop4Fragment = new EditTop4Fragment();
                    fragmentTransaction.replace(R.id.contentContainer, editTop4Fragment);
                    fragmentTransaction.commit();
                }
                else
                {
                    GlobalRankingFragment globalRankingFragment = new GlobalRankingFragment();
                    fragmentTransaction.replace(R.id.contentContainer, globalRankingFragment);
                    fragmentTransaction.commit();
                }
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.logout:
                logout();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void logout(){
        DialogFragment newFragment = MyAlertDialogFragment.newInstance(
                R.string.logout_question);
        newFragment.show(getSupportFragmentManager(), "dialog");
    }

    @Override
    public void onMatchOfDayFragmentInteraction(Uri uri) {

    }

    @Override
    public void onCurrentRankFragmentInteraction(Uri uri) {

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onItemSelected(int id) {
        System.out.println("id = " + id);
        System.out.println("go to next screen");
        BetDetailsFragment displayFrag = (BetDetailsFragment) getSupportFragmentManager().findFragmentById(R.id.betDetailsFragment);
            Intent intent = new Intent(this, BetDetailsActivity.class);
            //intent.putExtra("position", position);
            intent.putExtra("matchId", id);
            intent.putExtra("filter", "global");
            startActivity(intent);
    }

    public void doPositiveClick(){
        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences(getString(R.string.login), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.remove(getString(R.string.login));
        editor.commit();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    public void doNegativeClick(){

    }

    private void populateTeams(){
        final DBHelper mydb = new DBHelper(getApplicationContext());
        mydb.getWritableDatabase();
        TeamsService teamsService = TeamsService.retrofit.create(TeamsService.class);
        final Call<List<Team>> call = teamsService.callTeams();
        call.enqueue(new Callback<List<Team>>(){
            @Override
            public void onResponse(Call<List<Team>> call, Response<List<Team>> response) {
                for (int i = 0; i < response.body().size(); i++) {
                    Team team = response.body().get(i);
                    if (mydb.getTeam(team.getId()) == null) {
                        mydb.insertTeam(team.getName(), team.getFlag(), team.getId());
                    }
                    else
                    {
                        mydb.updateTeam(team.getId(), team.getName(), team.getFlag());
                    }

                }
            }
            @Override
            public void onFailure(Call<List<Team>> call, Throwable t) {
                Log.d("callko", t.getMessage());
                Log.d("callko", t.getCause().toString());
            }
        });
    }

    public static class MyAlertDialogFragment extends DialogFragment {

        public static MyAlertDialogFragment newInstance(int title) {
            MyAlertDialogFragment frag = new MyAlertDialogFragment();
            Bundle args = new Bundle();
            args.putInt("title", title);
            frag.setArguments(args);
            return frag;
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            int title = getArguments().getInt("title");

            return new AlertDialog.Builder(getActivity())
                    .setIcon(R.drawable.france)
                    .setTitle(title)
                    .setPositiveButton(R.string.yes,
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    ((HomeActivity)getActivity()).doPositiveClick();
                                }
                            }
                    )
                    .setNegativeButton(R.string.no,
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    ((HomeActivity)getActivity()).doNegativeClick();
                                }
                            }
                    )
                    .create();
        }
    }
}
