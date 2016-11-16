package russiabookmaker.perso.com.russiabookmaker.teams;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import russiabookmaker.perso.com.russiabookmaker.R;
import russiabookmaker.perso.com.russiabookmaker.bet.Match;

/**
 * Created by versusmind on 13/09/16.
 */
public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    private ArrayList<Match> mDataset;
    private CategoryFragment.OnCategoryFragmentInteractionListener mListener;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        //public TextView categoryTextView;
        public TextView team1TextView;
        public TextView team2TextView;
        public TextView resultTeam1TextView;
        public TextView resultTeam2TextView;
        public TextView resultBetTextView;
        public ViewHolder(View v) {
            super(v);
           //categoryTextView = (TextView) v.findViewById(R.id.categoryTextView);
            team1TextView = (TextView) v.findViewById(R.id.team1Label);
            team2TextView = (TextView) v.findViewById(R.id.team2Label);
            resultTeam1TextView = (TextView) v.findViewById(R.id.resultTeam1);
            resultTeam2TextView = (TextView) v.findViewById(R.id.resultTeam2);
            resultBetTextView = (TextView) v.findViewById(R.id.resultBet);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public CategoryAdapter(ArrayList<Match> myDataset, CategoryFragment.OnCategoryFragmentInteractionListener myListener) {
        mDataset = myDataset;
        mListener = myListener;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public CategoryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                        int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.category_item, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }


    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        final int id = position;
        //holder.categoryTextView.setText(mDataset.get(position));
        holder.team1TextView.setText(mDataset.get(position).getTeam1());
        holder.team2TextView.setText(mDataset.get(position).getTeam2());
        holder.resultTeam1TextView.setText(String.valueOf(mDataset.get(position).getResultTeam1()));
        holder.resultTeam2TextView.setText(String.valueOf(mDataset.get(position).getResultTeam2()));
        if (mDataset.get(position).getResultBet() == null)
            holder.resultBetTextView.setText("Non parié");
        else
            holder.resultBetTextView.setText(mDataset.get(position).getResultBet());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null)
                    mListener.onItemSelected(id);
            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
