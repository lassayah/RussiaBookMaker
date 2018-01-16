package russiabookmaker.perso.com.russiabookmaker.login;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import russiabookmaker.perso.com.russiabookmaker.HomeActivity;
import russiabookmaker.perso.com.russiabookmaker.R;
import russiabookmaker.perso.com.russiabookmaker.ranking.CurrentRankFragment;
import russiabookmaker.perso.com.russiabookmaker.utils.AlertDialogFragment;

public class SubscriptionActivity extends AppCompatActivity implements SubscriptionFragment.OnSubscriptionFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscription);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FragmentManager fragmentManager = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        SubscriptionFragment sbFragment = new SubscriptionFragment();
        fragmentTransaction.add(R.id.subscription_container, sbFragment);
        fragmentTransaction.commit();


    }

    @Override
    public void onSubscriptionFragmentInteraction(Uri uri) {

    }

}
