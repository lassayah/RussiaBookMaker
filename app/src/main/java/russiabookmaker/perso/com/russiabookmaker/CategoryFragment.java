package russiabookmaker.perso.com.russiabookmaker;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import russiabookmaker.perso.com.russiabookmaker.adapter.CategoryAdapter;
import russiabookmaker.perso.com.russiabookmaker.adapter.CategorySectionAdapter;
import russiabookmaker.perso.com.russiabookmaker.database.DBHelper;
import russiabookmaker.perso.com.russiabookmaker.model.Match;
import russiabookmaker.perso.com.russiabookmaker.rest.MatchService;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CategoryFragment.OnCategoryFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CategoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CategoryFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String FILTER = "filter";
    private static final String POSITION = "position";

    // TODO: Rename and change types of parameters
    private String pseudo;
    private String filter = "global";
    private CategorySectionAdapter categoryAdapter;
    private ArrayList<Match> mList;
    private int position;
    private DBHelper mydb;
    private LinearLayout noMatchLayout;

    private OnCategoryFragmentInteractionListener mListener;
    private RecyclerView categoriesList;

    public CategoryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param filter Parameter 1.
     * @return A new instance of fragment CategoryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CategoryFragment newInstance(int pos, String filter) {
        CategoryFragment fragment = new CategoryFragment();
        Bundle args = new Bundle();
        args.putInt(POSITION, pos);
        args.putString(FILTER, filter);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            filter = getArguments().getString(FILTER);
            position = getArguments().getInt(POSITION);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_category, container, false);
        SharedPreferences sharedPref = getContext().getSharedPreferences(getString(R.string.login), Context.MODE_PRIVATE);
        pseudo = sharedPref.getString(getString(R.string.login), "user");

        ArrayList<Match> categories = new ArrayList<Match>();
        /*categories.add("1er match de poules");
        categories.add("2eme match de poules");
        categories.add("3eme match de poules");
        categories.add("Huitièmes de finale");
        categories.add("Quarts de finale");
        categories.add("Demi-finales");
        categories.add("Finale et petite finale");*/
        categoriesList = (RecyclerView)v.findViewById(R.id.categoryList);
        noMatchLayout = (LinearLayout) v.findViewById(R.id.noMatchLayout);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        categoriesList.setLayoutManager(mLayoutManager);
        mydb = new DBHelper(getContext());
        mydb.getWritableDatabase();
        callService();
        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onItemClick(int id) {
        if (mListener != null) {
            mListener.onItemSelected(id);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (categoryAdapter != null) {
            mList.clear();
            System.out.println("size of get all matchs = " + mydb.getAllMatchs().size());
            for (Match match : mydb.getAllMatchs())
                mList.add(match);
            //mList = mydb.getAllMatchs();
            System.out.println("size of get all matchs list = " + mList.size());
            categoryAdapter.notifyDataSetChanged();
        }
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnCategoryFragmentInteractionListener) {
            mListener = (OnCategoryFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnCategoryFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
        void onItemSelected(int id);

    }

    private void callService(){
        MatchService matchService = MatchService.retrofit.create(MatchService.class);
        final Call<List<Match>> call = matchService.callMatch(pseudo, filter);
        call.enqueue(new Callback<List<Match>>(){
            @Override
            public void onResponse(Call<List<Match>> call, Response<List<Match>> response) {
                mList = new ArrayList<Match>();
                System.out.println("response size : " + response.body().size());
                if (response.body().size() > 0) {
                    for (int i = 0; i < response.body().size(); i++) {
                        Match match = response.body().get(i);
                        DateFormat f = DateFormat.getDateTimeInstance(DateFormat.FULL, DateFormat.FULL, Locale.FRANCE);
                        SimpleDateFormat parseFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        if (mydb.getMatch(response.body().get(i).getId() - 1) == null) {
                            mydb.insertMatch(match.getTeam1(), match.getTeam2(), match.getFlag1(),
                                    match.getFlag2(), parseFormat.format(match.getMatchTime()), match.getResultTeam1(),
                                    match.getResultTeam2(), match.isProlongations(), match.getId(),
                                    match.getTeam1Id(), match.getTeam2Id(), match.getResultBet());
                        }
                        else
                        {
                            mydb.updateMatch(match.getId(), match.getTeam1(), match.getTeam2(),
                                    parseFormat.format(match.getMatchTime()), match.getFlag1(),
                                    match.getFlag2(), match.getResultTeam1(),
                                    match.getResultTeam2(), match.isProlongations());
                        }
                        mList.add(match);
                    }
                }
                else
                {
                    categoriesList.setVisibility(View.GONE);
                    noMatchLayout.setVisibility(View.VISIBLE);
                }
                //categoryAdapter = new CategoryAdapter(mList, mListener);
                //categoriesList.setAdapter(categoryAdapter);

                categoryAdapter = new CategorySectionAdapter(mList, mListener, getContext());
                categoriesList.setAdapter(categoryAdapter);
            }
            @Override
            public void onFailure(Call<List<Match>> call, Throwable t) {
                Log.d("callko", t.getMessage());
                Log.d("callko", t.getCause().toString());
            }
        });
    }

}
