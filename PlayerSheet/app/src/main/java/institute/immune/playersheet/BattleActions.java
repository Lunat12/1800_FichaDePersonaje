package institute.immune.playersheet;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BattleActions#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BattleActions extends Fragment {

    //region GLOBAL VARIABLES

    private int result = 0;

    private Button btnDex, btnInte, btnAte, btnStrg, btnPow, btnCons, btnD20, btnD10;

    private View.OnClickListener dexListener;
    private View.OnClickListener inteListener;
    private View.OnClickListener ateListener;
    private View.OnClickListener strgListener;
    private View.OnClickListener powListener;
    private View.OnClickListener consListener;
    private View.OnClickListener d20Listener;
    private View.OnClickListener d10Listener;

    int penalties;
    int mod;
    //endregion

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public BattleActions() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BattleActions.
     */
    // TODO: Rename and change types and number of parameters
    public static BattleActions newInstance(String param1, String param2) {
        BattleActions fragment = new BattleActions();
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
        View view = inflater.inflate(R.layout.fragment_battle_actions, container, false);

        assignViewElements(view);
        createListeners();
        assignListeners();

        return view;
    }

    private void createListeners(){
        dexListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mod = EasyAccess.getCurrentCharacter().get_attr(Constants.DEX);
                result = EasyAccess.rolld20() + penalties + mod;
                ((CharacterSheet) getActivity()).generateNotification(Constants.D20, result);
            }
        };

        inteListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mod = EasyAccess.getCurrentCharacter().get_attr(Constants.INTE);
                result = EasyAccess.rolld20() + penalties + mod;
                ((CharacterSheet) getActivity()).generateNotification(Constants.D20, result);
            }
        };

        consListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mod = EasyAccess.getCurrentCharacter().get_attr(Constants.CONS);
                result = EasyAccess.rolld20() + penalties + mod;
                ((CharacterSheet) getActivity()).generateNotification(Constants.D20, result);
            }
        };

        ateListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mod = EasyAccess.getCurrentCharacter().get_attr(Constants.ATE);
                result = EasyAccess.rolld20() + penalties + mod;
                ((CharacterSheet) getActivity()).generateNotification(Constants.D20, result);
            }
        };

        strgListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mod = EasyAccess.getCurrentCharacter().get_attr(Constants.STRG);
                result = EasyAccess.rolld20() + penalties + mod;
                ((CharacterSheet) getActivity()).generateNotification(Constants.D20, result);
            }
        };

        powListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mod = EasyAccess.getCurrentCharacter().get_attr(Constants.POW);
                result = EasyAccess.rolld20() + penalties + mod;
                ((CharacterSheet) getActivity()).generateNotification(Constants.D20, result);
            }
        };

        d10Listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                result = EasyAccess.rolld10() + penalties;
                ((CharacterSheet) getActivity()).generateNotification(Constants.D10, result);
            }
        };

        d20Listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                result = EasyAccess.rolld20() + penalties;
                ((CharacterSheet) getActivity()).generateNotification(Constants.D20, result);
            }
        };

    }

    private void assignViewElements(View view){
        penalties = EasyAccess.getCurrentCharacter().get_madness_penalties();
        btnDex = view.findViewById(R.id.dex);
        btnInte = view.findViewById(R.id.inte);
        btnAte = view.findViewById(R.id.ate);
        btnStrg = view.findViewById(R.id.strg);
        btnCons = view.findViewById(R.id.con);
        btnPow = view.findViewById(R.id.pow);
        btnD20 = view.findViewById(R.id.d20);
        btnD10 = view.findViewById(R.id.d10);
    }

    private void assignListeners(){
        btnDex.setOnClickListener(dexListener);
        btnInte.setOnClickListener(inteListener);
        btnAte.setOnClickListener(ateListener);
        btnStrg.setOnClickListener(strgListener);
        btnCons.setOnClickListener(consListener);
        btnPow.setOnClickListener(powListener);
        btnD20.setOnClickListener(d20Listener);
        btnD10.setOnClickListener(d10Listener);
    }
}