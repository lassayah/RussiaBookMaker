package russiabookmaker.perso.com.russiabookmaker.top4;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import russiabookmaker.perso.com.russiabookmaker.R;
import russiabookmaker.perso.com.russiabookmaker.teams.Team;
import russiabookmaker.perso.com.russiabookmaker.rest.RetrofitBuilder;

/**
 * Created by versusmind on 20/10/2016.
 */

public class Top4Adapter extends /*RecyclerView.Adapter<Top4Adapter.ViewHolder>*/ BaseAdapter {

    private ArrayList<Team> mDataset;
    private Context mContext;

    public Top4Adapter(ArrayList<Team> dataset, Context context)
    {
        mDataset = dataset;
        mContext = context;
    }

    public ViewHolder holder;

    public static class ViewHolder {
        public ImageView flag;
        public TextView team;
    }

    @Override
    public int getCount() {
        return mDataset.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        holder = null;
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
        {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.top4_item, viewGroup, false);
            holder.flag = (ImageView) convertView.findViewById(R.id.top4TitleFlag);
            holder.team = (TextView) convertView.findViewById(R.id.top4TitleItem);
            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.team.setText(mDataset.get(i).getName());
        Picasso.with(mContext).load(RetrofitBuilder.baseUrl + mDataset.get(i).getFlag()).into(holder.flag);
        return convertView;
    }

    /*// Provide a reference to the views for each data item
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
    }*/
}
