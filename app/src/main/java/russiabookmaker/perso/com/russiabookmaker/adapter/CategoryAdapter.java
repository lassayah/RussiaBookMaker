package russiabookmaker.perso.com.russiabookmaker.adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import russiabookmaker.perso.com.russiabookmaker.BetDetailsActivity;
import russiabookmaker.perso.com.russiabookmaker.CategoryFragment;
import russiabookmaker.perso.com.russiabookmaker.R;

/**
 * Created by versusmind on 13/09/16.
 */
public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    private ArrayList<String> mDataset;
    private CategoryFragment.OnCategoryFragmentInteractionListener mListener;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView categoryTextView;
        public ViewHolder(View v) {
            super(v);
           categoryTextView = (TextView) v.findViewById(R.id.categoryTextView);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public CategoryAdapter(ArrayList<String> myDataset, CategoryFragment.OnCategoryFragmentInteractionListener myListener) {
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
    public void onBindViewHolder(ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        holder.categoryTextView.setText(mDataset.get(position));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null)
                    mListener.onItemSelected(position);
            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
