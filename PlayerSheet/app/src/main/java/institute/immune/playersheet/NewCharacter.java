package institute.immune.playersheet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class NewCharacter extends AppCompatActivity {

    //region GLOBAL VARIABLES

    private CharacterCreationFirstFragment firstFragment;
    private CharacterCreationSecondFragment secondFragment;
    private CharcaterCreationThirdFragment thirdFragment;
    private FragmentTransaction fragmentTransaction;
    private Fragment currentFragment;
    //endregion

    public void setCurrentFragment(Fragment _fragment){
        if (_fragment == thirdFragment) {


            finish();
            Intent connector = new Intent(NewCharacter.this, MainActivity.class);

            startActivity(connector);

        }

        if(_fragment == firstFragment){
            currentFragment = secondFragment;
        }
        else{
            currentFragment = thirdFragment;
        }

    }

    public CharacterCreationFirstFragment getFirstFragment(){return firstFragment;}
    public CharacterCreationSecondFragment getSecondFragment(){return secondFragment;}
    public CharcaterCreationThirdFragment getThirdFragment(){return thirdFragment;}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_character);

        assignFragments();
    }

    private void assignFragments(){
        firstFragment = new CharacterCreationFirstFragment();
        secondFragment = new CharacterCreationSecondFragment();
        thirdFragment = new CharcaterCreationThirdFragment();
        currentFragment = firstFragment;
    }

    public void changeFragment(){

        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainer, currentFragment);
        fragmentTransaction.commit();
    }
}