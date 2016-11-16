package russiabookmaker.perso.com.russiabookmaker.bet;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import russiabookmaker.perso.com.russiabookmaker.teams.CategoryFragment;
import russiabookmaker.perso.com.russiabookmaker.R;

public class BetActivity extends AppCompatActivity implements CategoryFragment.OnCategoryFragmentInteractionListener, BetDetailsFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bet);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


    }


    @Override
    public void onFragmentInteraction(Uri uri) {


    }

    @Override
    public void onItemSelected(int id) {
        System.out.println("id = " + id);
        System.out.println("go to next screen");
        BetDetailsFragment displayFrag = (BetDetailsFragment) getSupportFragmentManager().findFragmentById(R.id.betDetailsFragment);
        if (displayFrag == null) {
            // DisplayFragment (Fragment B) is not in the layout (handset layout),
            // so start DisplayActivity (Activity B)
            // and pass it the info about the selected item
            Intent intent = new Intent(this, BetDetailsActivity.class);
            //intent.putExtra("position", position);
            intent.putExtra("matchId", id);
            intent.putExtra("filter", "global");
            startActivity(intent);
        } else {
            // DisplayFragment (Fragment B) is in the layout (tablet layout),
            // so tell the fragment to update
            //displayFrag.updateContent(position);
            displayFrag.updateContent(id);
        }
    }


}
