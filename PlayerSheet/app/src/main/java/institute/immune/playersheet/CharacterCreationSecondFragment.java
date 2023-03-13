package institute.immune.playersheet;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CharacterCreationSecondFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CharacterCreationSecondFragment extends Fragment {

    //region GLOBAL VARIABLES
    private EditText s_dex, s_int, s_strg, s_ate;
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

    public CharacterCreationSecondFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CharacterCreationSecondFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CharacterCreationSecondFragment newInstance(String param1, String param2) {
        CharacterCreationSecondFragment fragment = new CharacterCreationSecondFragment();
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
        View view = inflater.inflate(R.layout.fragment_character_creation_second, container, false);

        assignViewElements(view);
        createListeners();
        assignListeners();

        return view;
    }

    private void assignViewElements(View view){
        s_ate = view.findViewById(R.id.s_attention);
        s_int = view.findViewById(R.id.s_intelligence);
        s_dex = view.findViewById(R.id.s_dexterity);
        s_strg = view.findViewById(R.id.s_strenght);
        next = view.findViewById(R.id.button2);
    }

    private void createListeners(){
        nextListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkInputs();
                EasyAccess.getCurrentCharacter().setSecundaries(Integer.parseInt(s_dex.getText().toString()),
                        Integer.parseInt(s_int.getText().toString()),
                        Integer.parseInt(s_strg.getText().toString()),
                        Integer.parseInt(s_ate.getText().toString()));

                ((NewCharacter) getActivity()).setCurrentFragment(((NewCharacter) getActivity()).getSecondFragment());
                ((NewCharacter) getActivity()).changeFragment();
            }
        };
    }

    private void checkInputs(){
        EasyAccess.checkForEmpty(s_int);
        EasyAccess.checkForEmpty(s_strg);
        EasyAccess.checkForEmpty(s_dex);
        EasyAccess.checkForEmpty(s_ate);
    }

    private void assignListeners(){
        next.setOnClickListener(nextListener);
    }


}