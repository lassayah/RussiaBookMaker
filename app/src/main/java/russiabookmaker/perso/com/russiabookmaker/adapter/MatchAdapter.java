package russiabookmaker.perso.com.russiabookmaker.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import russiabookmaker.perso.com.russiabookmaker.R;
import russiabookmaker.perso.com.russiabookmaker.model.Match;

/**
 * Created by versusmind on 14/09/16.
 */
public class MatchAdapter extends RecyclerView.Adapter<MatchAdapter.ViewHolder> {

    private ArrayList<Match> mDataset;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView team1TextView;
        public TextView team2TextView;
        public TextView resultTeam1TextView;
        public TextView resultTeam2TextView;
        public ViewHolder(View v) {
            super(v);
            team1TextView = (TextView) v.findViewById(R.id.team1Label);
            team2TextView = (TextView) v.findViewById(R.id.team2Label);
            resultTeam1TextView = (TextView) v.findViewById(R.id.resultTeam1);
            resultTeam2TextView = (TextView) v.findViewById(R.id.resultTeam2);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MatchAdapter(ArrayList<Match> myDataset) {
        mDataset = myDataset;
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
    public void onBindViewHolder(MatchAdapter.ViewHolder holder, int position) {
        holder.team1TextView.setText(mDataset.get(position).getTeam1());
        holder.team2TextView.setText(mDataset.get(position).getTeam2());
        holder.resultTeam1TextView.setText(String.valueOf(mDataset.get(position).getResultTeam1()));
        holder.resultTeam2TextView.setText(String.valueOf(mDataset.get(position).getResultTeam2()));
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
