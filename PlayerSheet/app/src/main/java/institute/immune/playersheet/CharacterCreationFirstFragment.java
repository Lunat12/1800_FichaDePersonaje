package institute.immune.playersheet;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CharacterCreationFirstFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CharacterCreationFirstFragment extends Fragment {

    //region GLOBAL VARIABLES

    private EditText name;
    private EditText surname;
    private EditText money;
    private EditText level;
    private EditText con;
    private EditText dex;
    private EditText inte;
    private EditText ate;
    private EditText stre;
    private EditText pow;

    private Button next;

    private View.OnClickListener nextListener;
    //endregion


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CharacterCreationFirstFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CharacterCreationFirstFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CharacterCreationFirstFragment newInstance(String param1, String param2) {
        CharacterCreationFirstFragment fragment = new CharacterCreationFirstFragment();
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
        View view = inflater.inflate(R.layout.fragment_character_creation_first, container, false);

        assignViewElements(view);
        createListeners();
        assignListeners();

        return view;
    }

    private void assignListeners(){
        next.setOnClickListener(nextListener);
    }

    private void createListeners(){
        nextListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkInputs();
                Character newCharacter = new Character(name.getText().toString(),
                        surname.getText().toString(),
                        Float.parseFloat(money.getText().toString()),
                        Integer.parseInt(level.getText().toString()),
                        Integer.parseInt(dex.getText().toString()),
                        Integer.parseInt(inte.getText().toString()),
                        Integer.parseInt(con.getText().toString()),
                        Integer.parseInt(stre.getText().toString()),
                        Integer.parseInt(pow.getText().toString()),
                        Integer.parseInt(ate.getText().toString()));


                EasyAccess.setCurrentCharacter(newCharacter);

                ((NewCharacter) getActivity()).setCurrentFragment(((NewCharacter) getActivity()).getFirstFragment());
                ((NewCharacter) getActivity()).changeFragment();


            }
        };
    }

    private void checkInputs(){
        EasyAccess.checkForEmpty(name);
        EasyAccess.checkForEmpty(surname);
        EasyAccess.checkForEmpty(money);
        EasyAccess.checkForEmpty(level);
        EasyAccess.checkForEmpty(dex);
        EasyAccess.checkForEmpty(inte);
        EasyAccess.checkForEmpty(con);
        EasyAccess.checkForEmpty(stre);
        EasyAccess.checkForEmpty(pow);
        EasyAccess.checkForEmpty(ate);
    }

    private void assignViewElements(View view){
        name = view.findViewById(R.id.name);
        surname = view.findViewById(R.id.surname);
        money = view.findViewById(R.id.money);
        level = view.findViewById(R.id.level);
        con = view.findViewById(R.id.constitution);
        dex = view.findViewById(R.id.dexterity);
        inte = view.findViewById(R.id.intelligence);
        ate = view.findViewById(R.id.attention);
        stre = view.findViewById(R.id.strenght);
        pow = view.findViewById(R.id.power);
        next = view.findViewById(R.id.button1);
    }


}