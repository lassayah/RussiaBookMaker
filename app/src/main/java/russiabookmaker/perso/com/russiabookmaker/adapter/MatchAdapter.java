package russiabookmaker.perso.com.russiabookmaker.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import russiabookmaker.perso.com.russiabookmaker.BetDetailsFragment;
import russiabookmaker.perso.com.russiabookmaker.R;
import russiabookmaker.perso.com.russiabookmaker.database.DBHelper;
import russiabookmaker.perso.com.russiabookmaker.model.Match;
import russiabookmaker.perso.com.russiabookmaker.rest.BetService;

/**
 * Created by versusmind on 14/09/16.
 */
public class MatchAdapter extends RecyclerView.Adapter<MatchAdapter.ViewHolder> {

    private ArrayList<Match> mDataset;
    private String mUser;
    private Context mContext;
    private Match match;
    private BetDetailsFragment.OnFragmentInteractionListener mListener;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        //public TextView team1TextView;
        //public TextView team2TextView;
        //public TextView resultTeam1TextView;
        //public TextView resultTeam2TextView;
        //public ImageView team1ImageView;
        //public ImageView team2ImageView;
        //public TextView matchTimeTextView;
        //public TextView matchTypeTextView;
        //public Button betAction;
        public Button team1ImageButton;
        public Button team2ImageButton;
        public Button nulImageButton;
        public ImageView team1ImageView;
        public ImageView team2ImageView;
        public ViewHolder(View v) {
            super(v);
            //team1TextView = (TextView) v.findViewById(R.id.team1Label);
            //team2TextView = (TextView) v.findViewById(R.id.team2Label);
            //resultTeam1TextView = (TextView) v.findViewById(R.id.resultTeam1);
            //resultTeam2TextView = (TextView) v.findViewById(R.id.resultTeam2);
            //betAction = (Button) v.findViewById(R.id.betAction);
            //team1ImageView = (ImageView) v.findViewById(R.id.team1Flag);
            //team2ImageView = (ImageView) v.findViewById(R.id.team2Flag);
            //matchTimeTextView = (TextView) v.findViewById(R.id.matchTime);
            //matchTypeTextView = (TextView) v.findViewById(R.id.typeMatch);
            team1ImageButton = (Button) v.findViewById(R.id.team1DetailImageButton);
            team2ImageButton = (Button) v.findViewById(R.id.team2DetailImageButton);
            nulImageButton = (Button) v.findViewById(R.id.nulImageButton);
            team1ImageView = (ImageView) v.findViewById(R.id.team1DetailImageView);
            team2ImageView = (ImageView) v.findViewById(R.id.team2DetailImageView);

        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    /*public MatchAdapter(ArrayList<Match> myDataset, BetDetailsFragment.OnFragmentInteractionListener myListener, String user, Context context) {
        mDataset = myDataset;
        mListener = myListener;
        mUser = user;
        mContext = context;
    }*/
    public MatchAdapter(int position, Context context) {
        mContext = context;
        DBHelper myDb = new DBHelper(context);
        match = myDb.getMatch(position);
    }


    @Override
    public MatchAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.match_item, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(MatchAdapter.ViewHolder holder, final int position) {
        /*holder.team1TextView.setText(mDataset.get(position).getTeam1());
        holder.team2TextView.setText(mDataset.get(position).getTeam2());
        holder.resultTeam1TextView.setText(String.valueOf(mDataset.get(position).getResultTeam1()));
        holder.resultTeam2TextView.setText(String.valueOf(mDataset.get(position).getResultTeam2()));
        if (mDataset.get(position).getGroup() != null)
            holder.matchTypeTextView.setText("Groupe " + mDataset.get(position).getGroup());
        else
            holder.matchTimeTextView.setText("Phase finale");
        DateFormat f = DateFormat.getDateTimeInstance(DateFormat.FULL, DateFormat.FULL, Locale.FRANCE);
        SimpleDateFormat parseFormat = new SimpleDateFormat("EEEE dd MMMM HH:mm", Locale.FRENCH);
        holder.matchTimeTextView.setText(parseFormat.format(mDataset.get(position).getMatchTime()));*/
        Picasso.with(mContext).load("http://10.0.2.2:8888/DesktopRussiaBookMaker/webservices/" + mDataset.get(position).getFlag1()).into(holder.team1ImageView);
        Picasso.with(mContext).load("http://10.0.2.2:8888/DesktopRussiaBookMaker/webservices/" + mDataset.get(position).getFlag2()).into(holder.team2ImageView);
        holder.team1ImageButton.setText(match.getTeam1());
        holder.team2ImageButton.setText(match.getTeam2());
        holder.nulImageButton.setText("Nul");
        //final int id = mDataset.get(position).getId();
        //final Date matchTime = mDataset.get(position).getMatchTime();
        //final int resultBet = mDataset.get(position).getResultBet();
        /*holder.betAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                BetService betService = BetService.retrofit.create(BetService.class);
                System.out.println(id);
                System.out.println(mUser);
                System.out.println(matchTime);
                System.out.println(resultBet);
                final Call<Match> call = betService.callMatch(id, mUser, matchTime, 2);
                call.enqueue(new Callback<Match>(){
                    @Override
                    public void onResponse(Call<Match> call, Response<Match> response) {
                        System.out.println("response : " + response.body().getBetOk());
                        String message = "";
                        if (response.body().getBetOk().equals("late"))
                            message = "Trop tard...";
                        else if (response.body().getBetOk().equals("true"))
                            message = "Pari envoyé";
                        else
                            message = "Erreur d'envoi";
                        Toast.makeText(v.getContext(), message, Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onFailure(Call<Match> call, Throwable t) {
                        Log.d("callko", t.getMessage());
                        Log.d("callko", t.getCause().toString());
                    }
                });
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
