package institute.immune.playersheet;

import androidx.appcompat.app.AppCompatActivity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.DigitsKeyListener;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    //region GLOBAL VARIABLES
    private Button add, delete;
    private EraseCharacterDialogFragment eraseFragment;
    private FragmentTransaction fragmentTransaction;

    private View.OnClickListener addListener, removeListener;
    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle(getString(R.string.character_selector));

        EasyAccess.assignDB(this);


        eraseButtons();
        generateDefaults();
        printButtons();

        assignViewElements();
        assignFragments();
        createListeners();
        assignListeners();

    }

    private void assignViewElements(){
        add = findViewById(R.id.btnAddCharacter);
        delete = findViewById(R.id.btn_remove);
    }

    private void assignListeners(){
        add.setOnClickListener(addListener);
        delete.setOnClickListener(removeListener);
    }

    private void assignFragments(){
        eraseFragment = new EraseCharacterDialogFragment();
    }

    private void createListeners(){
        addListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Intent connector = new Intent(MainActivity.this, NewCharacter.class);

                startActivity(connector);
            }
        };

        removeListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EasyAccess.setIsErasing();
            }
        };
    }

    public void generateDefaults(){
        String query = "SELECT * FROM character";
        String[] args = new String[]{};
        Cursor c = EasyAccess.getDb().rawQuery(query, args);

        while(c.moveToNext()) {
            EasyAccess.setCharacters(ManageDDBB.readCharacter(c));
        }

    }

    public void eraseButtons(){
        EasyAccess.getCharacters().clear();
        LinearLayout boxVertical = (LinearLayout)findViewById(R.id.linearLayoutMain);

        boxVertical.removeAllViews();
    }

    public void selectCharacter(){
        if(!EasyAccess.getCurrentCharacter().isFilled()) {
            fillInventory();
            EasyAccess.getCurrentCharacter().setFilled();
        }
        finish();
        Intent connector = new Intent(MainActivity.this, CharacterSheet.class);

        startActivity(connector);
    }

    private void fillInventory(){
        String query = Constants.SELECT_FROM_INVENTORY_WHERE_CHARACTER;
        String[] args = new String[]{Integer.toString(EasyAccess.getCurrentCharacter().getId())};
        Cursor c = EasyAccess.getDb().rawQuery(query, args);

        while(c.moveToNext()) {
            EasyAccess.getCurrentCharacter().setItemToInventory(ManageDDBB.readItem(c));
        }
    }

    public void printButtons(){
        ArrayList<Character> currentCharcaters = EasyAccess.getCharacters();
        for(int i = 0; i < currentCharcaters.size(); i++){
            LinearLayout boxVertical = (LinearLayout)findViewById(R.id.linearLayoutMain);

            LinearLayout boxHorizontal = new LinearLayout(this);
            boxHorizontal.setOrientation(LinearLayout.HORIZONTAL);
            LinearLayout.LayoutParams parameters = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            boxHorizontal.setLayoutParams(parameters);
            boxHorizontal.setClickable(true);

            Character thisCharacter = currentCharcaters.get(i);

            boxHorizontal.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    EasyAccess.setCurrentCharacter(thisCharacter);

                    if(!EasyAccess.isIsErasing()){
                        selectCharacter();
                    }
                    eraseFragment.show(getFragmentManager(), Constants.DIALOG);


                }
            });

            boxHorizontal.setWeightSum(2);

            TextView newText = new TextView(this);
            LinearLayout.LayoutParams contentParameters = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT, 1.0f);
            newText.setLayoutParams(contentParameters);
            newText.setText(currentCharcaters.get(i).getName() + Constants.SPACE + currentCharcaters.get(i).getSurname());
            newText.setTextSize(20);
            newText.setTextColor(Color.rgb(255, 238, 223));
            contentParameters.setMargins(10,10,10,10);
            newText.setInputType(InputType.TYPE_CLASS_TEXT);

            boxHorizontal.setId(i);
            boxHorizontal.addView(newText);

            boxVertical.addView(boxHorizontal);
        }
    }

    private void emptyInventory(){
        String query = Constants.SELECT_FROM_INVENTORY_WHERE_CHARACTER;
        String[] args = new String[]{Integer.toString(EasyAccess.getCurrentCharacter().getId())};
        Cursor c = EasyAccess.getDb().rawQuery(query, args);

        while(c.moveToNext()) {
            if(EasyAccess.getCurrentCharacter().isFilled()){
            EasyAccess.getCurrentCharacter().removeItemFromInventory(ManageDDBB.readItem(c));
            }

            ManageDDBB.deleteItem(c, EasyAccess.getDb());

        }
    }

    public void eraseCharacter(){
        EasyAccess.setCurrentCharacter(EasyAccess.getCurrentCharacter());
        emptyInventory();
        ManageDDBB.deleteCharacter(EasyAccess.getCurrentCharacter(), EasyAccess.getDb());
        EasyAccess.getCharacters().remove(EasyAccess.getCurrentCharacter());

        eraseButtons();
        generateDefaults();
        printButtons();

    }
}