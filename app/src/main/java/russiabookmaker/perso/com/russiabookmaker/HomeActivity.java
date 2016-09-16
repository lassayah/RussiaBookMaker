package russiabookmaker.perso.com.russiabookmaker;



import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by versusmind on 05/09/16.
 */
public class HomeActivity extends AppCompatActivity implements MatchOfDayFragment.OnMatchOfDayFragmentInteractionListener,
        CurrentRankFragment.OnCurrentRankFragmentInteractionListener {

    String pseudo = "";
    Button betButton;
    private Button goToRanking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        TextView bonjourLogin = (TextView)findViewById(R.id.bonjourLogin);
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

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        MatchOfDayFragment modFragment = new MatchOfDayFragment();
        CurrentRankFragment crFragment = new CurrentRankFragment();
        fragmentTransaction.add(R.id.matchOfDay_container, modFragment);
        fragmentTransaction.add(R.id.currentRank_container, crFragment);
        fragmentTransaction.commit();
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
}
