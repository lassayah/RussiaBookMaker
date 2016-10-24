package russiabookmaker.perso.com.russiabookmaker.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import russiabookmaker.perso.com.russiabookmaker.R;
import russiabookmaker.perso.com.russiabookmaker.model.Team;

/**
 * Created by versusmind on 20/10/2016.
 */

public class Top4Adapter extends RecyclerView.Adapter<Top4Adapter.ViewHolder> {

    private ArrayList<Team> mDataset;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView top4TitleTextView;
        public TextView top4ChoiceTextView;
        public ImageView top4FlagImageView;
        public ViewHolder(View v) {
            super(v);
            top4TitleTextView = (TextView) v.findViewById(R.id.top4TitleItem);
            top4ChoiceTextView = (TextView) v.findViewById(R.id.top4TitleChoice);
            top4FlagImageView = (ImageView) v.findViewById(R.id.top4TitleFlag);
        }
    }

    public Top4Adapter(ArrayList<Team> dataset)
    {
        mDataset = dataset;
    }
    @Override
    public Top4Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.top4_item, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(Top4Adapter.ViewHolder holder, int position) {
        holder.top4TitleTextView.setText(mDataset.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
