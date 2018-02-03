package russiabookmaker.perso.com.russiabookmaker.teams;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.tonicartos.superslim.GridSLM;
import com.tonicartos.superslim.LinearSLM;

import java.util.ArrayList;
import java.util.List;

import russiabookmaker.perso.com.russiabookmaker.R;
import russiabookmaker.perso.com.russiabookmaker.database.DBHelper;
import russiabookmaker.perso.com.russiabookmaker.bet.Match;

/**
 * Created by versusmind on 07/11/2016.
 */

public class CategorySectionAdapter extends RecyclerView.Adapter<CategorySectionAdapter.ViewHolder> {

    private static final int VIEW_TYPE_HEADER = 0x01;

    private static final int VIEW_TYPE_CONTENT = 0x00;

    private static final int LINEAR = 0;
    private int sectionManager = 0;
    private Context mContext;
    private ArrayList<Match> mDataset;
    private String [] sectionNames;
    private int mHeaderDisplay;
    private boolean mMarginsFixed;
    private CategoryFragment.OnCategoryFragmentInteractionListener mListener;
    private final ArrayList<LineItem> mItems;


    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView team1TextView;
        public TextView team2TextView;
        public TextView resultTeam1TextView;
        public TextView resultTeam2TextView;
        public TextView resultBetTextView;
        public TextView sectionTextView;
        public ViewHolder(View v) {
            super(v);
            team1TextView = (TextView) v.findViewById(R.id.team1Label);
            team2TextView = (TextView) v.findViewById(R.id.team2Label);
            resultTeam1TextView = (TextView) v.findViewById(R.id.resultTeam1);
            resultTeam2TextView = (TextView) v.findViewById(R.id.resultTeam2);
            resultBetTextView = (TextView) v.findViewById(R.id.resultBet);
            sectionTextView = (TextView) v.findViewById(R.id.section_title);
        }
    }




    public CategorySectionAdapter(ArrayList<Match> myDataset, CategoryFragment.OnCategoryFragmentInteractionListener myListener, Context context) {
        mDataset = myDataset;
        mItems = new ArrayList<>();
        mContext = context;
        mListener = myListener;
        sectionNames = context.getResources().getStringArray(R.array.categories_names);
        int [] sectionId = context.getResources().getIntArray(R.array.categories_id);
        String lastHeader = "";
        int sectionManager = 0;
        int headerCount = 0;
        int sectionFirstPosition = 0;
        for (int i = 0; i < myDataset.size(); i++)
        {
            if (headerCount < 8) {
                if (sectionId[headerCount] == getCategory(mDataset.get(i).getId()) || i == 0) {
                    String section = sectionNames[headerCount];
                    sectionManager = (sectionManager + 1) % 2;
                    sectionFirstPosition = i + headerCount;
                    lastHeader = section;
                    headerCount++;
                    mItems.add(new LineItem(null, true, sectionManager, sectionFirstPosition, section));
                }
            }
            mItems.add(new LineItem(myDataset.get(i), false, sectionManager, sectionFirstPosition, null));
        }

    }

    private int getCategory(int id){
        if (id < 17)
            return 0;
        else if (id >= 17 && id < 33)
            return 1;
        else if (id >= 33 && id < 49)
            return 2;
        else if (id >= 49 && id < 57)
            return 3;
        else if (id >= 57 && id < 61)
            return 4;
        else if (id >= 61 && id < 63)
            return 5;
        else if (id == 63)
            return 6;
        else
            return 7;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;

        if (viewType == VIEW_TYPE_HEADER) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item_header, parent, false);
        } else {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item, parent, false);
        }
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    public void notify(List<Match> categories){
        int [] sectionId = mContext.getResources().getIntArray(R.array.categories_id);
        String lastHeader = "";
        int sectionManager = 0;
        int headerCount = 0;
        int sectionFirstPosition = 0;
        mItems.clear();
        for (int i = 0; i < categories.size(); i++)
        {
            if (headerCount < 8) {
                if (sectionId[headerCount] == getCategory(categories.get(i).getId()) || i == 0) {
                    String section = sectionNames[headerCount];
                    sectionManager = (sectionManager + 1) % 2;
                    sectionFirstPosition = i + headerCount;
                    lastHeader = section;
                    headerCount++;
                    mItems.add(new LineItem(null, true, sectionManager, sectionFirstPosition, section));
                }
            }
            mItems.add(new LineItem(categories.get(i), false, sectionManager, sectionFirstPosition, null));
        }
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final LineItem item = mItems.get(position);
        final View itemView = holder.itemView;
        final int id = position;

        final GridSLM.LayoutParams lp = GridSLM.LayoutParams.from(itemView.getLayoutParams());
        // Overrides xml attrs, could use different layouts too.
        if (item.isHeader) {
            lp.headerDisplay = mHeaderDisplay;
            if (lp.isHeaderInline() || (mMarginsFixed && !lp.isHeaderOverlay())) {
                lp.width = ViewGroup.LayoutParams.MATCH_PARENT;
            } else {
                lp.width = ViewGroup.LayoutParams.WRAP_CONTENT;
            }

            lp.headerEndMarginIsAuto = !mMarginsFixed;
            lp.headerStartMarginIsAuto = !mMarginsFixed;
            holder.sectionTextView.setText(item.text);
        }
        else
        {
            holder.team1TextView.setText(mItems.get(position).match.getTeam1().getName());
            holder.team2TextView.setText(mItems.get(position).match.getTeam2().getName());
            holder.resultTeam1TextView.setText(String.valueOf(mItems.get(position).match.getResultTeam1()));
            holder.resultTeam2TextView.setText(String.valueOf(mItems.get(position).match.getResultTeam2()));
            if (mItems.get(position).match.getResultBet() == null)
                holder.resultBetTextView.setText("Non parié");
            else
                holder.resultBetTextView.setText(mItems.get(position).match.getResultBet());
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DBHelper mydb = new DBHelper(mContext);
                        mydb.getWritableDatabase();
                        if (mydb.hasTeam(mItems.get(id).match.getTeam1().getName()) && mydb.hasTeam(mItems.get(id).match.getTeam2().getName())) {
                            if (mListener != null)
                                mListener.onItemSelected(id - getCategory(mItems.get(id).match.getId()) - 1);
                            mydb.close();
                        }
                        else
                        {
                            Toast.makeText(mContext, "Match bloqué", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        }
        lp.setSlm(item.sectionManager == LINEAR ? LinearSLM.ID : GridSLM.ID);
        lp.setColumnWidth(mContext.getResources().getDimensionPixelSize(R.dimen.activity_horizontal_margin));
        lp.setFirstPosition(item.sectionFirstPosition);
        itemView.setLayoutParams(lp);
    }

    @Override
    public int getItemViewType(int position) {
        return mItems.get(position).isHeader ? VIEW_TYPE_HEADER : VIEW_TYPE_CONTENT;
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }


    private static class LineItem {

        public int sectionManager;

        public int sectionFirstPosition;

        public boolean isHeader;

        public Match match;

        public String text;

        public LineItem(Match match, boolean isHeader, int sectionManager,
                        int sectionFirstPosition, String text) {
            this.isHeader = isHeader;
            this.match = match;
            this.text = text;
            this.sectionManager = sectionManager;
            this.sectionFirstPosition = sectionFirstPosition;
        }
    }
}
