package russiabookmaker.perso.com.russiabookmaker.top4;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import russiabookmaker.perso.com.russiabookmaker.R;
import russiabookmaker.perso.com.russiabookmaker.database.DBHelper;
import russiabookmaker.perso.com.russiabookmaker.teams.Team;
import russiabookmaker.perso.com.russiabookmaker.teams.TeamsService;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link EditTop4Fragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link EditTop4Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditTop4Fragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private RecyclerView top4List;
    private GridView top4Grid;
    private ArrayList<Team> tList;
    private DBHelper mydb;
    private String pseudo;
    private TextView team1TextView;
    private TextView team2TextView;
    private TextView team3TextView;
    private TextView team4TextView;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public EditTop4Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EditTop4Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EditTop4Fragment newInstance(String param1, String param2) {
        EditTop4Fragment fragment = new EditTop4Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_edit_top4, container, false);
        SharedPreferences sharedPref = getContext().getSharedPreferences(getString(R.string.login), Context.MODE_PRIVATE);
        pseudo = sharedPref.getString(getString(R.string.login), "user");
        top4Grid = (GridView) v.findViewById(R.id.top4Grid);
        mydb = new DBHelper(this.getContext());
        mydb.getWritableDatabase();
        team1TextView = (TextView) v.findViewById(R.id.premierTop4Edit);
        team2TextView = (TextView) v.findViewById(R.id.deuxiemeTop4Edit);
        team3TextView = (TextView) v.findViewById(R.id.troisiemeTop4Edit);
        team4TextView = (TextView) v.findViewById(R.id.quatriemeTop4Edit);
        callService();
        getTop4();


        return v;
    }

    private void getTop4() {
        final Top4Service top4Service = Top4Service.retrofit.create(Top4Service.class);
        final Call<Top4> call = top4Service.callTop4(pseudo);
        call.enqueue(new Callback<Top4>(){
            @Override
            public void onResponse(Call<Top4> call, Response<Top4> response) {

                    team1TextView.setText(response.body().getTeam1());
                    team2TextView.setText(response.body().getTeam2());
                    team3TextView.setText(response.body().getTeam3());
                    team4TextView.setText(response.body().getTeam4());
            }
            @Override
            public void onFailure(Call<Top4> call, Throwable t) {
                Log.d("callko", t.getMessage());
                Log.d("callko", t.getCause().toString());
            }
        });
    }

    private void callService(){
        TeamsService teamsService = TeamsService.retrofit.create(TeamsService.class);
        final Call<List<Team>> call = teamsService.callTeams();
        call.enqueue(new Callback<List<Team>>(){
            @Override
            public void onResponse(Call<List<Team>> call, Response<List<Team>> response) {
                tList = new ArrayList<Team>();
                for (int i = 0; i < response.body().size(); i++) {
                    Team team = response.body().get(i);
                    if (mydb.getAllTeams() == null) {
                        mydb.insertTeam(team.getName(), team.getFlag(), team.getId());
                    }
                    else
                    {
                        mydb.updateTeam(team.getId(), team.getName(), team.getFlag());
                    }
                    tList.add(team);
                }
                Top4Adapter top4Adapter = new Top4Adapter(tList, getContext());
                top4Grid.setAdapter(top4Adapter);
            }
            @Override
            public void onFailure(Call<List<Team>> call, Throwable t) {
                Log.d("callko", t.getMessage());
                Log.d("callko", t.getCause().toString());
            }
        });
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
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
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
