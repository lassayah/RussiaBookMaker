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

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import russiabookmaker.perso.com.russiabookmaker.adapter.MatchAdapter;
import russiabookmaker.perso.com.russiabookmaker.model.Match;
import russiabookmaker.perso.com.russiabookmaker.rest.MatchService;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link BetDetailsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link BetDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BetDetailsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String POSITION = "position";

    private RecyclerView matchList;

    // TODO: Rename and change types of parameters
    private int position = 0;
    private String pseudo;

    private OnFragmentInteractionListener mListener;

    public BetDetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param pos Parameter 1.
     * @return A new instance of fragment BetDetailsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BetDetailsFragment newInstance(int pos) {
        BetDetailsFragment fragment = new BetDetailsFragment();
        Bundle args = new Bundle();
        args.putInt(POSITION, pos);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            position = getArguments().getInt(POSITION);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_bet_details, container, false);
        matchList = (RecyclerView) v.findViewById(R.id.matchList);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        matchList.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        matchList.setLayoutManager(mLayoutManager);
        SharedPreferences sharedPref = getContext().getSharedPreferences(getString(R.string.login), Context.MODE_PRIVATE);
        pseudo = sharedPref.getString(getString(R.string.login), "user");
        callService(position);

        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    public void updateContent(int position) {
        callService(position);
    }

    private void callService(int position){
        MatchService matchService = MatchService.retrofit.create(MatchService.class);
        final Call<List<Match>> call = matchService.callMatch(String.valueOf(position), pseudo);
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
                    mList.add(match);
                }
                MatchAdapter matchAdapter = new MatchAdapter(mList);
                matchList.setAdapter(matchAdapter);
            }
            @Override
            public void onFailure(Call<List<Match>> call, Throwable t) {
                Log.d("callko", t.getMessage());
                Log.d("callko", t.getCause().toString());
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
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
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
