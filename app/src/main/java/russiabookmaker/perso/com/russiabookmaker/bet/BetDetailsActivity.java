package russiabookmaker.perso.com.russiabookmaker.bet;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import russiabookmaker.perso.com.russiabookmaker.R;

public class BetDetailsActivity extends AppCompatActivity implements BetDetailsFragment.OnFragmentInteractionListener {

    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bet_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent intent = getIntent();
        position = intent.getIntExtra("matchId", 0);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        BetDetailsFragment betFragment = BetDetailsFragment.newInstance(position, "global");
        fragmentTransaction.add(R.id.bet_detail_layout, betFragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

}
