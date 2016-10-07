package russiabookmaker.perso.com.russiabookmaker;



import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Calendar;

import russiabookmaker.perso.com.russiabookmaker.adapter.CategoryAdapter;
import russiabookmaker.perso.com.russiabookmaker.service.Receiver;

/**
 * Created by versusmind on 05/09/16.
 */
public class HomeActivity extends AppCompatActivity implements MatchOfDayFragment.OnMatchOfDayFragmentInteractionListener,
        CurrentRankFragment.OnCurrentRankFragmentInteractionListener, BetDetailsFragment.OnFragmentInteractionListener, CategoryFragment.OnCategoryFragmentInteractionListener {

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
        //MatchOfDayFragment modFragment = new MatchOfDayFragment();

        CurrentRankFragment crFragment = new CurrentRankFragment();
        //fragmentTransaction.add(R.id.matchOfDay_container, modFragment);

        fragmentTransaction.add(R.id.currentRank_container, crFragment);
        //fragmentTransaction.commit();

        CategoryFragment cFragment = CategoryFragment.newInstance(0, "day");
        fragmentTransaction.add(R.id.matchOfDay_container, cFragment);
        fragmentTransaction.commit();

        /*Calendar cal=Calendar.getInstance();
        cal.set(Calendar.MONTH,(5));
        cal.set(Calendar.YEAR,2015);
        cal.set(Calendar.DAY_OF_MONTH, 27);
        cal.set(Calendar.HOUR_OF_DAY,13);
        cal.set(Calendar.MINUTE,00);

        Intent intent = new Intent(this, Receiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 001, intent, 0);

        AlarmManager alarmManager1 = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager1.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pendingIntent);*/
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
