package institute.immune.playersheet;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;

import java.nio.charset.StandardCharsets;

public class ManageDDBB {

    public static void createCharacter(Character character, SQLiteDatabase db){

        ContentValues newCharacter = new ContentValues();
        newCharacter.put(Constants.NAME, character.getName());
        newCharacter.put(Constants.SURNAME, character.getSurname());
        newCharacter.put(Constants.LVL, character.getLvl());
        newCharacter.put(Constants.MONEY, character.getMoney());

        newCharacter.put(Constants.DEX, character.get_attr(Constants.DEX));
        newCharacter.put(Constants.INTE, character.get_attr(Constants.INTE));
        newCharacter.put(Constants.CONS, character.get_attr(Constants.CONS));
        newCharacter.put(Constants.STRG, character.get_attr(Constants.STRG));
        newCharacter.put(Constants.POW, character.get_attr(Constants.POW));
        newCharacter.put(Constants.ATE, character.get_attr(Constants.ATE));

        newCharacter.put(Constants.S_DEX, character.get_s_attr(Constants.DEX));
        newCharacter.put(Constants.S_INTE, character.get_s_attr(Constants.INTE));
        newCharacter.put(Constants.S_STRG, character.get_s_attr(Constants.STRG));
        newCharacter.put(Constants.S_ATE, character.get_s_attr(Constants.ATE));

        newCharacter.put(Constants.MAX_MAD, character.get_max_stats(Constants.MAD));
        newCharacter.put(Constants.MAX_LIFE, character.get_max_stats(Constants.LIFE));
        newCharacter.put(Constants.MAX_MAGIC, character.get_max_stats(Constants.MAGIC));

        newCharacter.put(Constants.MAD, character.get_current_stats(Constants.MAD));
        newCharacter.put(Constants.LIFE, character.get_current_stats(Constants.LIFE));
        newCharacter.put(Constants.MAGIC, character.get_current_stats(Constants.MAGIC));


        db.insert(Constants.CHARACTER, null, newCharacter);
    }

    public static long createItem(Item item, SQLiteDatabase db){

        ContentValues newInventoryItem = new ContentValues();
        newInventoryItem.put(Constants.CHARACTER, item.getCharacter());
        newInventoryItem.put(Constants.ITEM, item.getName());


       return db.insert(Constants.INVENTORY, null, newInventoryItem);
    }

    public static Character readCharacter(Cursor cursor){

        int id = cursor.getColumnIndex(Constants._ID);
        int name = cursor.getColumnIndex(Constants.NAME);
        int surname = cursor.getColumnIndex(Constants.SURNAME);
        int lvl = cursor.getColumnIndex(Constants.LVL);
        int money = cursor.getColumnIndex(Constants.MONEY);

        int dex = cursor.getColumnIndex(Constants.DEX);
        int strg = cursor.getColumnIndex(Constants.STRG);
        int cons = cursor.getColumnIndex(Constants.CONS);
        int ate = cursor.getColumnIndex(Constants.ATE);
        int pow = cursor.getColumnIndex(Constants.POW);
        int inte = cursor.getColumnIndex(Constants.INTE);

        int s_dex = cursor.getColumnIndex(Constants.S_DEX);
        int s_strg = cursor.getColumnIndex(Constants.S_STRG);
        int s_inte = cursor.getColumnIndex(Constants.S_INTE);
        int s_ate = cursor.getColumnIndex(Constants.S_ATE);

        int max_life = cursor.getColumnIndex(Constants.MAX_LIFE);
        int max_magic = cursor.getColumnIndex(Constants.MAX_MAGIC);
        int max_mad = cursor.getColumnIndex(Constants.MAX_MAD);

        int life = cursor.getColumnIndex(Constants.LIFE);
        int magic = cursor.getColumnIndex(Constants.MAGIC);
        int mad = cursor.getColumnIndex(Constants.MAD);

        int image = cursor.getColumnIndex(Constants.IMAGE);

        Character newCharacter = new Character(cursor.getString(name),
                cursor.getString(surname),
                Float.parseFloat(cursor.getString(money)),
                Integer.parseInt(cursor.getString(lvl)),
                Integer.parseInt(cursor.getString(dex)),
                Integer.parseInt(cursor.getString(inte)),
                Integer.parseInt(cursor.getString(cons)),
                Integer.parseInt(cursor.getString(strg)),
                Integer.parseInt(cursor.getString(pow)),
                Integer.parseInt(cursor.getString(ate)));

        newCharacter.setId(Integer.parseInt(cursor.getString(id)));

        newCharacter.setSecundaries(Integer.parseInt(cursor.getString(s_dex)), Integer.parseInt(cursor.getString(s_inte)), Integer.parseInt(cursor.getString(s_strg)), Integer.parseInt(cursor.getString(s_ate)));

        newCharacter.setMaxedStats(Integer.parseInt(cursor.getString(max_life)), Integer.parseInt(cursor.getString(max_magic)), Integer.parseInt(cursor.getString(max_mad)));

        newCharacter.set_current_stats(Constants.LIFE, Integer.parseInt(cursor.getString(life)));
        newCharacter.set_current_stats(Constants.MAGIC, Integer.parseInt(cursor.getString(magic)));
        newCharacter.set_current_stats(Constants.MAD, Integer.parseInt(cursor.getString(mad)));

        if((cursor.getBlob(image))!=null){
        newCharacter.setImage(EasyAccess.ByteArrayToBitmap(cursor.getBlob(image)));
        }

        return newCharacter;
    }
    public static Item readItem(Cursor cursor){
        int id = cursor.getColumnIndex(Constants._ID);
        int name = cursor.getColumnIndex(Constants.ITEM);
        int character = cursor.getColumnIndex(Constants.CHARACTER);

        Item newItem = new Item(cursor.getString(name),
                Integer.parseInt(cursor.getString(character)));

        newItem.setId(Integer.parseInt(cursor.getString(id)));

        return newItem;
    }

    public static void updateCharacter(Character character, SQLiteDatabase db){
        ContentValues newCharacter = new ContentValues();
        newCharacter.put(Constants.NAME, character.getName());
        newCharacter.put(Constants.SURNAME, character.getSurname());
        newCharacter.put(Constants.LVL, character.getLvl());
        newCharacter.put(Constants.MONEY, character.getMoney());

        newCharacter.put(Constants.DEX, character.get_attr(Constants.DEX));
        newCharacter.put(Constants.INTE, character.get_attr(Constants.INTE));
        newCharacter.put(Constants.CONS, character.get_attr(Constants.CONS));
        newCharacter.put(Constants.STRG, character.get_attr(Constants.STRG));
        newCharacter.put(Constants.POW, character.get_attr(Constants.POW));
        newCharacter.put(Constants.ATE, character.get_attr(Constants.ATE));

        newCharacter.put(Constants.S_DEX, character.get_s_attr(Constants.DEX));
        newCharacter.put(Constants.S_INTE, character.get_s_attr(Constants.INTE));
        newCharacter.put(Constants.S_STRG, character.get_s_attr(Constants.STRG));
        newCharacter.put(Constants.S_ATE, character.get_s_attr(Constants.ATE));

        newCharacter.put(Constants.MAX_MAD, character.get_max_stats(Constants.MAD));
        newCharacter.put(Constants.MAX_LIFE, character.get_max_stats(Constants.LIFE));
        newCharacter.put(Constants.MAX_MAGIC, character.get_max_stats(Constants.MAGIC));

        newCharacter.put(Constants.MAD, character.get_current_stats(Constants.MAD));
        newCharacter.put(Constants.LIFE, character.get_current_stats(Constants.LIFE));
        newCharacter.put(Constants.MAGIC, character.get_current_stats(Constants.MAGIC));

        newCharacter.put(Constants.IMAGE, character.getImage() != null ? EasyAccess.bitmapToByteArray(character.getImage()) : null);

        db.update(Constants.CHARACTER, newCharacter, Constants.QUESTION_ID, new String[]{Integer.toString(character.getId())});

    }

    public static void deleteCharacter(Character character, SQLiteDatabase db){

        db.delete(Constants.CHARACTER,  Constants.QUESTION_ID, new String[]{Integer.toString(character.getId())} );

    }

    public static void deleteItem(Cursor cursor, SQLiteDatabase db){

        db.delete(Constants.INVENTORY,  Constants.QUESTION_ID, new String[]{cursor.getString(0)} );

    }

}
