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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import russiabookmaker.perso.com.russiabookmaker.adapter.MatchAdapter;
import russiabookmaker.perso.com.russiabookmaker.database.DBHelper;
import russiabookmaker.perso.com.russiabookmaker.model.Match;
import russiabookmaker.perso.com.russiabookmaker.rest.BetService;
import russiabookmaker.perso.com.russiabookmaker.rest.MatchService;
import russiabookmaker.perso.com.russiabookmaker.rest.RetrofitBuilder;


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
    private static final String FILTER = "filter";

    private RecyclerView matchList;
    private DBHelper mydb;

    // TODO: Rename and change types of parameters
    private int position = 0;
    private String pseudo;
    private String filter = "global";
    private TextView team1TextView;
    private TextView team2TextView;
    private ImageView team1ImageView;
    private ImageView team2ImageView;
    private Button team1ImageButton;
    private Button team2ImageButton;
    private Button nulImageButton;
    private Match match;

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
    public static BetDetailsFragment newInstance(int pos, String filter) {
        BetDetailsFragment fragment = new BetDetailsFragment();
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
            position = getArguments().getInt(POSITION);
            filter = getArguments().getString(FILTER);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_bet_details, container, false);
        /*matchList = (RecyclerView) v.findViewById(R.id.matchList);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        matchList.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        matchList.setLayoutManager(mLayoutManager);
        SharedPreferences sharedPref = getContext().getSharedPreferences(getString(R.string.login), Context.MODE_PRIVATE);
        pseudo = sharedPref.getString(getString(R.string.login), "user");




        callService(position);*/
        SharedPreferences sharedPref = getContext().getSharedPreferences(getString(R.string.login), Context.MODE_PRIVATE);
        pseudo = sharedPref.getString(getString(R.string.login), "user");
        team1ImageButton = (Button) v.findViewById(R.id.team1DetailImageButton);
        team2ImageButton = (Button) v.findViewById(R.id.team2DetailImageButton);
        nulImageButton = (Button) v.findViewById(R.id.nulImageButton);
        team1ImageView = (ImageView) v.findViewById(R.id.team1DetailImageView);
        team2ImageView = (ImageView) v.findViewById(R.id.team2DetailImageView);
        callService(position);


        team1ImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeBet(match.getTeam1Id());
            }
        });

        team2ImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeBet(match.getTeam2Id());
            }
        });

        nulImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeBet(33);
            }
        });

        return v;
    }

    private void makeBet(int bet) {
        BetService betService = BetService.retrofit.create(BetService.class);
        System.out.println(match.getMatchTime());
        System.out.println(match.getId());
        System.out.println(bet);
        System.out.println(pseudo);
        SimpleDateFormat parseFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        final Call<Match> call = betService.callMatch(match.getId(), pseudo, parseFormat.format(match.getMatchTime()), bet);
        call.enqueue(new Callback<Match>(){
            @Override
            public void onResponse(Call<Match> call, Response<Match> response) {
                Match match = response.body();
                System.out.println("response : " + match.getBetOk());
                String message = "";
                if (match.getBetOk().equals("late"))
                    message = "Trop tard...";
                else if (match.getBetOk().equals("true"))
                {
                    message = "Pari envoyé";
                    DBHelper mydb = new DBHelper(getContext());
                    mydb.updateResult(match.getId(), match.getResultBet());
                }
                else
                    message = "Erreur d'envoi";
                Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onFailure(Call<Match> call, Throwable t) {
                Log.d("callko", t.getMessage());
                Log.d("callko", t.getCause().toString());
            }
        });
    }



    public void updateContent(int position) {
        callService(position);
    }

    private void callService(final int position){
        /*MatchService matchService = MatchService.retrofit.create(MatchService.class);
        final Call<List<Match>> call = matchService.callMatch(/*String.valueOf(position),*/ /*pseudo, filter);
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
                    match.setMatchTime(response.body().get(i).getMatchTime());
                    match.setDateServeur(response.body().get(i).getDateServeur());
                    match.setFlag1(response.body().get(i).getFlag1());
                    match.setFlag2(response.body().get(i).getFlag2());
                    match.setGroup(response.body().get(i).getGroup());
                    mList.add(match);
                }
                MatchAdapter matchAdapter = new MatchAdapter(mList, mListener, pseudo, getContext());
                matchList.setAdapter(matchAdapter);
            }
            @Override
            public void onFailure(Call<List<Match>> call, Throwable t) {
                Log.d("callko", t.getMessage());
                Log.d("callko", t.getCause().toString());
            }
        });*/
        mydb = new DBHelper(getContext());
        match = mydb.getMatch(position);
        /*if (filter.equals("day")) {
            matchLayout.setVisibility(View.GONE);
            noMatchLayout.setVisibility(View.VISIBLE);
        }*/

        //System.out.println("get team 1 " + match.getTeam1());
        Picasso.with(getContext()).load(RetrofitBuilder.baseUrl + match.getFlag1()).into(team1ImageView);
        Picasso.with(getContext()).load(RetrofitBuilder.baseUrl + match.getFlag2()).into(team2ImageView);
        team1ImageButton.setText(match.getTeam1());
        team2ImageButton.setText(match.getTeam2());
        nulImageButton.setText("Nul");
        //MatchAdapter matchAdapter = new MatchAdapter(position, getContext());
        //matchList.setAdapter(matchAdapter);

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
