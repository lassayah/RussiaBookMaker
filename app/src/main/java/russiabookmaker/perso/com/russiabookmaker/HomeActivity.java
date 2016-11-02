package russiabookmaker.perso.com.russiabookmaker;



import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.annotation.IdRes;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import java.util.Calendar;
import java.util.Date;

import russiabookmaker.perso.com.russiabookmaker.adapter.CategoryAdapter;
import russiabookmaker.perso.com.russiabookmaker.service.Receiver;

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
}
