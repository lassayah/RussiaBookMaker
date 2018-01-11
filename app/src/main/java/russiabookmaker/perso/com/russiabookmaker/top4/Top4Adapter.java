package russiabookmaker.perso.com.russiabookmaker.top4;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import russiabookmaker.perso.com.russiabookmaker.R;
import russiabookmaker.perso.com.russiabookmaker.teams.Team;
import russiabookmaker.perso.com.russiabookmaker.rest.RetrofitBuilder;

/**
 * Created by versusmind on 20/10/2016.
 */

public class Top4Adapter extends RecyclerView.Adapter<Top4Adapter.ViewHolder> {

    private ArrayList<Team> mDataset;
    private ArrayList<String> mTop4Teams;
    private int selectedPositionTeam1 = -1;
    private int selectedPositionTeam2 = -1;
    private int selectedPositionTeam3 = -1;
    private int selectedPositionTeam4 = -1;
    private AdapterListener mListener;

    // define listener
    public interface AdapterListener {
        void onClick(ArrayList<String> teams);
    }

    public Top4Adapter(ArrayList<Team> dataset, ArrayList<String> top4Teams)
    {
        mDataset = dataset;
        mTop4Teams = top4Teams;

        selectedPositionTeam1 = setInitialSelectedPosition(0);
        selectedPositionTeam2 = setInitialSelectedPosition(1);
        selectedPositionTeam3 = setInitialSelectedPosition(2);
        selectedPositionTeam4 = setInitialSelectedPosition(3);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView top4TitleTextView;
        public ToggleButton top4Team1ToggleButton;
        public ToggleButton top4Team2ToggleButton;
        public ToggleButton top4Team3ToggleButton;
        public ToggleButton top4Team4ToggleButton;

        public ViewHolder(View v) {
            super(v);
            top4TitleTextView = (TextView) v.findViewById(R.id.top4TitleItem);
            top4Team1ToggleButton = (ToggleButton) v.findViewById(R.id.firstPlaceButton);
            top4Team2ToggleButton = (ToggleButton) v.findViewById(R.id.secondPlaceButton);
            top4Team3ToggleButton = (ToggleButton) v.findViewById(R.id.thirdPlaceButton);
            top4Team4ToggleButton = (ToggleButton) v.findViewById(R.id.fourthPlaceButton);
            final String currentTeam = top4TitleTextView.getText().toString();
            top4Team1ToggleButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (selectedPositionTeam1 != getAdapterPosition())
                    {
                        removeSelectedPositionIfNecessary(top4TitleTextView.getText().toString());
                        mTop4Teams.set(0, top4TitleTextView.getText().toString());
                        top4Team1ToggleButton.setChecked(true);
                        selectedPositionTeam1 = getAdapterPosition();

                    }
                    else
                    {
                        mTop4Teams.set(0, "");
                        selectedPositionTeam1 = -1;
                    }
                    notifyDataSetChanged();
                    mListener.onClick(mTop4Teams);
                }
            });

            top4Team2ToggleButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (selectedPositionTeam2 != getAdapterPosition())
                    {
                        removeSelectedPositionIfNecessary(top4TitleTextView.getText().toString());
                        mTop4Teams.set(1, top4TitleTextView.getText().toString());
                        top4Team2ToggleButton.setChecked(true);
                        selectedPositionTeam2 = getAdapterPosition();

                    }
                    else
                    {
                        mTop4Teams.set(1, "");
                        selectedPositionTeam2 = -1;
                    }
                    notifyDataSetChanged();
                    mListener.onClick(mTop4Teams);
                }
            });

            top4Team3ToggleButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (selectedPositionTeam3 != getAdapterPosition())
                    {
                        removeSelectedPositionIfNecessary(top4TitleTextView.getText().toString());
                        mTop4Teams.set(2, top4TitleTextView.getText().toString());
                        top4Team3ToggleButton.setChecked(true);
                        selectedPositionTeam3 = getAdapterPosition();

                    }
                    else
                    {
                        mTop4Teams.set(2, "");
                        selectedPositionTeam3 = -1;
                    }
                    notifyDataSetChanged();
                    mListener.onClick(mTop4Teams);
                }
            });

            top4Team4ToggleButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (selectedPositionTeam4 != getAdapterPosition())
                    {
                        removeSelectedPositionIfNecessary(top4TitleTextView.getText().toString());
                        mTop4Teams.set(3, top4TitleTextView.getText().toString());
                        top4Team4ToggleButton.setChecked(true);
                        selectedPositionTeam4 = getAdapterPosition();

                    }
                    else
                    {
                        mTop4Teams.set(3, "");
                        selectedPositionTeam4 = -1;
                    }
                    notifyDataSetChanged();
                    mListener.onClick(mTop4Teams);
                }
            });
        }

        private void removeSelectedPositionIfNecessary(String currentTeam)
        {
            if (mTop4Teams.contains(currentTeam)) {
                int index = mTop4Teams.indexOf(currentTeam);
                if (index == 0)
                    selectedPositionTeam1 = -1;
                else if (index == 1)
                    selectedPositionTeam2 = -1;
                else if (index == 2)
                    selectedPositionTeam3 = -1;
                else
                    selectedPositionTeam4 = -1;
                mTop4Teams.set(mTop4Teams.indexOf(currentTeam), "");
            }
        }
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

        final String currentTeam = mDataset.get(position).getName();
        holder.top4TitleTextView.setText(currentTeam);
        this.setCheckedToggleButton(position, selectedPositionTeam1, holder.top4Team1ToggleButton);
        this.setCheckedToggleButton(position, selectedPositionTeam2, holder.top4Team2ToggleButton);
        this.setCheckedToggleButton(position, selectedPositionTeam3, holder.top4Team3ToggleButton);
        this.setCheckedToggleButton(position, selectedPositionTeam4, holder.top4Team4ToggleButton);
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }


    private int setInitialSelectedPosition(int team)
    {
        for (int i = 0; i < mDataset.size(); i++)
        {
            if (mTop4Teams.get(team).equals(mDataset.get(i).getName()))
                return i;
        }
        return -1;

    }

    private void setCheckedToggleButton(int position, int selectedPositionTeam, ToggleButton toggleButton)
    {
        if (position == selectedPositionTeam)
            toggleButton.setChecked(true);
        else
            toggleButton.setChecked(false);
    }

    // set the listener. Must be called from the fragment
    public void setListener(AdapterListener listener) {
        this.mListener = listener;
    }

}
