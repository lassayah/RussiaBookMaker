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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import russiabookmaker.perso.com.russiabookmaker.adapter.CategoryAdapter;
import russiabookmaker.perso.com.russiabookmaker.adapter.MatchAdapter;
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
    private int position;
    private DBHelper mydb ;

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
                ArrayList<Match> mList = new ArrayList<Match>();
                for (int i = 0; i < response.body().size(); i++)
                {
                    Match match = new Match();
                    match.setTeam1(response.body().get(i).getTeam1());
                    match.setTeam2(response.body().get(i).getTeam2());
                    match.setResultTeam1(response.body().get(i).getResultTeam1());
                    match.setResultTeam2(response.body().get(i).getResultTeam2());
                    match.setId(response.body().get(i).getId());
                    match.setProlongations(response.body().get(i).isProlongations());
                    match.setMatchTime(response.body().get(i).getMatchTime());
                    match.setDateServeur(response.body().get(i).getDateServeur());
                    match.setFlag1(response.body().get(i).getFlag1());
                    match.setFlag2(response.body().get(i).getFlag2());
                    match.setGroup(response.body().get(i).getGroup());
                    DateFormat f = DateFormat.getDateTimeInstance(DateFormat.FULL, DateFormat.FULL, Locale.FRANCE);
                    //SimpleDateFormat parseFormat = new SimpleDateFormat("EEEE dd MMMM HH:mm", Locale.FRENCH);
                    SimpleDateFormat parseFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    mydb.insertMatch(response.body().get(i).getTeam1(), response.body().get(i).getTeam2(), response.body().get(i).getFlag1(),
                            response.body().get(i).getFlag2(), parseFormat.format(response.body().get(i).getMatchTime()), response.body().get(i).getResultTeam1(),
                            response.body().get(i).getResultTeam2(), response.body().get(i).isProlongations(), response.body().get(i).getId(),
                            response.body().get(i).getTeam1Id(), response.body().get(i).getTeam2Id());
                    mList.add(match);
                }
                CategoryAdapter categoryAdapter = new CategoryAdapter(mList, mListener);
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
