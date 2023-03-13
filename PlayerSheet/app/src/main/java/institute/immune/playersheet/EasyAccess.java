package institute.immune.playersheet;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.widget.EditText;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class EasyAccess {
    //region STATIC VARIABLES
    private static Character CurrentCharacter;
    private static ArrayList<Character> Characters = new ArrayList<Character>();
    private static boolean ListInitiated;
    private static SQLiteDatabase db;
    private static boolean isErasing = false;
    //endregion


    public static boolean isIsErasing() {
        return isErasing;
    }

    public static void setIsErasing(){
        isErasing = !isIsErasing();
    }

    public static SQLiteDatabase getDb() {return db;}

    public static void setListInitiated(){ListInitiated = true;}

    public static boolean getListInitiated(){return ListInitiated;}

    public static void setCurrentCharacter(Character _current){
        CurrentCharacter = _current;
    }

    public static Character getCurrentCharacter(){
        return CurrentCharacter;
    }

    public static ArrayList<Character> getCharacters(){
        return Characters;
    }

    public static void setCharacters(Character _character){
        Characters.add(_character);
    }

    public static int rolld20(){
        return ThreadLocalRandom.current().nextInt(1,21);}

    public static int rolld10(){
        return ThreadLocalRandom.current().nextInt(1,11);}

    public static byte[] bitmapToByteArray(Bitmap bitmap){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }

    public static Bitmap ByteArrayToBitmap(byte[] byteArray){
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
    }

    public static void assignDB(Context context){
        MySQLiteHelper dbHelper = new MySQLiteHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public static void checkForEmpty(EditText textField){
        if(TextUtils.isEmpty(textField.getText())) {
            textField.setText(Constants.ZERO);
        }
    }
}
