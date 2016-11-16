package russiabookmaker.perso.com.russiabookmaker.ranking;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import russiabookmaker.perso.com.russiabookmaker.R;

/**
 * Created by versusmind on 09/09/16.
 */
public class RankingAdapter extends RecyclerView.Adapter<RankingAdapter.ViewHolder> {


    private ArrayList<Ranking> mDataset;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView nameTextView;
        public TextView rankTextView;
        public ViewHolder(View v) {
            super(v);
            nameTextView = (TextView) v.findViewById(R.id.nameRanking);
            rankTextView = (TextView) v.findViewById(R.id.rankRanking);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public RankingAdapter(ArrayList<Ranking> myDataset) {
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public RankingAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ranking_item, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.nameTextView.setText(mDataset.get(position).getUser());
        holder.rankTextView.setText(mDataset.get(position).getRank());

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
