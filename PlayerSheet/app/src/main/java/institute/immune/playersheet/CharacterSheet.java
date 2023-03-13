package institute.immune.playersheet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.FragmentTransaction;

import android.animation.Animator;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

public class CharacterSheet extends AppCompatActivity {

    //region GLOBAL VARIABLES
    private TextView name, fue, des, con, inte, pod, ate, lvl, life, mad, magic, strenght, dexterity, intelligence, attention, money;
    private LinearLayout inventory;
    private Button addItem, addAmmount, addPv, addPl, addPm, back, btnChangeFragment;
    private EditText newItem, newAmmount, pv, pl, pm;
    private ImageView picture;

    private LottieAnimationView dice;

    private RolActions rolFragment;
    private BattleActions battleFragment;
    private FragmentTransaction fragmentTransaction;
    private boolean isBattle = false;

    private View.OnClickListener changePicListener;
    private View.OnClickListener addItemListener;
    private View.OnClickListener addAmmountListener;
    private View.OnClickListener addPlListener;
    private View.OnClickListener addPvListener;
    private View.OnClickListener addPmListener;
    private View.OnClickListener goBackListener;
    private View.OnClickListener changeFragmentListener;

    public static final int PICK_IMAGE = 1;
    //endregion

    private void createNotificationChanel(String name, String desc, int importance, String id){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel(id, name, importance);
            channel.setDescription(desc);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_sheet);

        EasyAccess.assignDB(this);

        createNotificationChanel(getString(R.string.chanel_name), getString(R.string.chanel_description), NotificationManager.IMPORTANCE_HIGH, Constants.CANAL_HIGH);

        assignFragments();

        assignViewFields();

        refresh();

        showCurrentCharacterInfo();
        showCurrentCharacterImage();

        emptyInvetory();

        createListeners();
        assignListeners();

        dice.setAnimation(R.raw.dice);
    }

    private void assignListeners(){
        picture.setOnClickListener(changePicListener);

        addItem.setOnClickListener(addItemListener);

        addAmmount.setOnClickListener(addAmmountListener);

        addPl.setOnClickListener(addPlListener);

        addPv.setOnClickListener(addPvListener);

        addPm.setOnClickListener(addPmListener);

        back.setOnClickListener(goBackListener);

        btnChangeFragment.setOnClickListener(changeFragmentListener);
    }

    private void createListeners(){
         changePicListener = new View.OnClickListener() {
            public void onClick(View view) {
                changePic();
            }
        };

        addItemListener = new View.OnClickListener() {
            public void onClick(View view) {
                EasyAccess.checkForEmpty(newItem);
                createItem();
                newItem.setText("");
                emptyInvetory();
            }
        };

        addAmmountListener = new View.OnClickListener() {
            public void onClick(View view) {
                EasyAccess.checkForEmpty(newAmmount);
                EasyAccess.getCurrentCharacter().setMoney(Float.parseFloat(newAmmount.getText().toString()));
                ManageDDBB.updateCharacter(EasyAccess.getCurrentCharacter(), EasyAccess.getDb());
                newAmmount.setText("");
                money.setText(Float.toString(EasyAccess.getCurrentCharacter().getMoney()));
            }
        };

        addPlListener = new View.OnClickListener() {
            public void onClick(View view) {
                EasyAccess.checkForEmpty(pl);
                EasyAccess.getCurrentCharacter().set_current_stats(Constants.MAD, Integer.parseInt(pl.getText().toString()));
                ManageDDBB.updateCharacter(EasyAccess.getCurrentCharacter(), EasyAccess.getDb());
                pl.setText("");
                refresh();}
        };

        addPvListener = new View.OnClickListener() {
            public void onClick(View view) {
                EasyAccess.checkForEmpty(pv);
                EasyAccess.getCurrentCharacter().set_current_stats(Constants.LIFE, Integer.parseInt(pv.getText().toString()));
                ManageDDBB.updateCharacter(EasyAccess.getCurrentCharacter(), EasyAccess.getDb());
                pv.setText("");
                refresh();}
        };

        addPmListener = new View.OnClickListener() {
            public void onClick(View view) {
                EasyAccess.checkForEmpty(pm);
                EasyAccess.getCurrentCharacter().set_current_stats(Constants.MAGIC, Integer.parseInt(pm.getText().toString()));
                ManageDDBB.updateCharacter(EasyAccess.getCurrentCharacter(), EasyAccess.getDb());
                pm.setText("");
                refresh();}
        };

        goBackListener = new View.OnClickListener() {
            public void onClick(View view) {
                finish();
                Intent connector = new Intent(CharacterSheet.this, MainActivity.class);

                startActivity(connector);}
        };

        changeFragmentListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentTransaction = getSupportFragmentManager().beginTransaction();

                if(isBattle){
                    btnChangeFragment.setText(Constants.BATTLE);
                    fragmentTransaction.replace(R.id.fragmentContainer, rolFragment);
                }
                else{
                    btnChangeFragment.setText(Constants.ROL);
                    fragmentTransaction.replace(R.id.fragmentContainer, battleFragment);
                }
                isBattle = !isBattle;

                fragmentTransaction.commit();
            }
        };


    }

    private void assignFragments(){
        rolFragment = new RolActions();
        battleFragment = new BattleActions();
    }

    private void assignViewFields(){
        name = findViewById(R.id.name);
        fue = findViewById(R.id.fue);
        des = findViewById(R.id.des);
        con = findViewById(R.id.con);
        inte = findViewById(R.id.inte);
        pod = findViewById(R.id.pod);
        ate = findViewById(R.id.ate);
        lvl = findViewById(R.id.lvl);
        life = findViewById(R.id.life);
        mad = findViewById(R.id.mad);
        magic = findViewById(R.id.magic);
        strenght = findViewById(R.id.strenght);
        dexterity = findViewById(R.id.dexterity);
        intelligence = findViewById(R.id.intelligence);
        attention = findViewById(R.id.attention);
        money = findViewById(R.id.money);
        inventory = findViewById(R.id.invetory);
        addItem= findViewById(R.id.addItem);
        newItem= findViewById(R.id.newItem);
        addAmmount = findViewById(R.id.addAmmount);
        newAmmount= findViewById(R.id.newAmmount);
        pv= findViewById(R.id.pv);
        pl = findViewById(R.id.pl);
        pm= findViewById(R.id.pm);
        addPv= findViewById(R.id.addpv);
        addPl = findViewById(R.id.addpl);
        addPm= findViewById(R.id.addpm);
        picture = findViewById(R.id.picture);
        dice = findViewById(R.id.dice);
        back = findViewById(R.id.back);
        btnChangeFragment = findViewById(R.id.changeActions);
    }

    private void showCurrentCharacterInfo(){
        name.setText(EasyAccess.getCurrentCharacter().getName() + Constants.SPACE + EasyAccess.getCurrentCharacter().getSurname());

        int stat = EasyAccess.getCurrentCharacter().get_attr(Constants.STRG);
        fue.setText(Integer.toString(stat));
        stat = EasyAccess.getCurrentCharacter().get_attr(Constants.DEX);
        des.setText(Integer.toString(stat));
        stat = EasyAccess.getCurrentCharacter().get_attr(Constants.CONS);
        con.setText(Integer.toString(stat));
        stat = EasyAccess.getCurrentCharacter().get_attr(Constants.INTE);
        inte.setText(Integer.toString(stat));
        stat = EasyAccess.getCurrentCharacter().get_attr(Constants.POW);
        pod.setText(Integer.toString(stat));
        stat = EasyAccess.getCurrentCharacter().get_attr(Constants.ATE);
        ate.setText(Integer.toString(stat));

        stat = EasyAccess.getCurrentCharacter().getLvl();
        lvl.setText(Integer.toString(stat));

        stat = EasyAccess.getCurrentCharacter().get_s_attr(Constants.STRG);
        strenght.setText(Integer.toString(stat));
        stat = EasyAccess.getCurrentCharacter().get_s_attr(Constants.DEX);
        dexterity.setText(Integer.toString(stat));
        stat = EasyAccess.getCurrentCharacter().get_s_attr(Constants.INTE);
        intelligence.setText(Integer.toString(stat));
        stat = EasyAccess.getCurrentCharacter().get_s_attr(Constants.ATE);
        attention.setText(Integer.toString(stat));

        money.setText(Float.toString(EasyAccess.getCurrentCharacter().getMoney()));
    }

    private void showCurrentCharacterImage(){
        EasyAccess.getCurrentCharacter().getImage();
        if(EasyAccess.getCurrentCharacter().getImage() != null) {
            picture.setImageBitmap(EasyAccess.getCurrentCharacter().getImage());
        }
        else{
            picture.setImageDrawable(getDrawable(R.drawable.character_icon));
        }
    }

    private void changePic(){
        Intent intent = new Intent();
        intent.setType(Constants.SET_TYPE_IMAGE);
        intent.setAction(Intent.ACTION_PICK);
        startActivityForResult(Intent.createChooser(intent, Constants.CHOSE_IMAGE), PICK_IMAGE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == PICK_IMAGE){
            if(resultCode == MainActivity.RESULT_OK){
                final Uri uri = data.getData();
                InputStream in;
                try{
                    in = getContentResolver().openInputStream(uri);
                    Bitmap selected_img = BitmapFactory.decodeStream(in);

                    int width = selected_img.getWidth();
                    int height = selected_img.getHeight();
                    int crop;
                    Bitmap cropImg;

                    if(width > height){
                        crop = (width-height) / 2;
                        cropImg =  Bitmap.createBitmap(selected_img, crop, 0, height, height);
                    }
                    else{
                        crop = (height-width) / 2;
                        cropImg =  Bitmap.createBitmap(selected_img, 0, crop, width, width);
                    }

                    EasyAccess.getCurrentCharacter().setImage(cropImg);
                    ManageDDBB.updateCharacter(EasyAccess.getCurrentCharacter(), EasyAccess.getDb());

                    refresh();

                }catch(FileNotFoundException e ){
                    e.printStackTrace();
                    Toast.makeText(this, String.format(getString(R.string.error), getString(R.string.error1)), Toast.LENGTH_LONG).show();
                }
            }
            else{
                Toast.makeText(this, String.format(getString(R.string.error), getString(R.string.error2)), Toast.LENGTH_LONG).show();
            }
        }

    }

    private void rollDice(){
        dice.setVisibility(View.VISIBLE);

        dice.removeAllAnimatorListeners();
        dice.addAnimatorListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                dice.setVisibility(View.GONE);
                dice.setFrame(0);

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        dice.playAnimation();


    }

    public void generateNotification(String dice_string, int result){

        rollDice();

        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), Constants.CANAL_HIGH)
                .setSmallIcon(R.drawable.notify)
                .setContentTitle(dice_string + Constants.SPACE + getString(R.string.results))
                .setContentText(Integer.toString(result))
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getApplicationContext());
        notificationManager.notify(1, builder.build());
    }

    public void refresh(){
        mad.setText(Integer.toString(EasyAccess.getCurrentCharacter().get_current_stats(Constants.MAD)));
        magic.setText(Integer.toString(EasyAccess.getCurrentCharacter().get_current_stats(Constants.MAGIC)));
        life.setText(Integer.toString(EasyAccess.getCurrentCharacter().get_current_stats(Constants.LIFE)));

        picture.setImageBitmap(EasyAccess.getCurrentCharacter().getImage());
    }

    private void addInventory(){
        ArrayList<Item> inventoryItems = EasyAccess.getCurrentCharacter().getInventory();
        for(int i=0; i < inventoryItems.size(); i++){


            TextView item = new TextView(this);
            item.setTextSize(22);
            LinearLayout.LayoutParams contentParameters = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

            contentParameters.gravity = Gravity.CENTER;
            contentParameters.setMargins(0,0,0,10);
            item.setLayoutParams(contentParameters);
            item.setText(inventoryItems.get(i).getName());
            item.setTextColor(Color.rgb(255, 238, 223));
            item.setClickable(true);

            item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    TextView thisButton = (TextView) view;
                    for(int i=0; i < inventoryItems.size(); i++){
                        if(inventoryItems.get(i).getName().equals(thisButton.getText().toString())){

                           System.out.println(i+ "  "+ inventoryItems.get(i).getName());

                            //TODO:delete item
                            String query = Constants.SELECT_FROM_INVENTORY_WHERE__ID;
                            String[] args = new String[]{Integer.toString(inventoryItems.get(i).getId())};
                            Cursor c = EasyAccess.getDb().rawQuery(query, args);

                            while(c.moveToNext()) {
                                ManageDDBB.deleteItem(c, EasyAccess.getDb());
                            }

                            EasyAccess.getCurrentCharacter().removeItemFromInventory(inventoryItems.get(i));

                            emptyInvetory();
                            break;
                        }
                    }
                }
            });
            inventory.addView(item);
        }
    }

    private void emptyInvetory(){
        inventory.removeAllViews();
        addInventory();
    }

    private void createItem(){

        Item item = new Item(newItem.getText().toString(), EasyAccess.getCurrentCharacter().getId());
        int itemId = (int)ManageDDBB.createItem(item, EasyAccess.getDb());

        item.setId(itemId);

        EasyAccess.getCurrentCharacter().setItemToInventory(item);
    }
}