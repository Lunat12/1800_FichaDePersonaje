package institute.immune.playersheet;


import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;

public class Character implements Serializable {
    //region ATTRIBUTES
    private String name, surname;
    private int lvl;
    private int dex, inte, cons, strg, pow, ate;
    private float money;
    private ArrayList<Item> inventory = new ArrayList<Item>();
    private int s_dex, s_inte, s_strg, s_ate;
    private int max_mad, mad, max_life, life, max_magic, magic;
    private Bitmap image;
    private int id;

    private boolean isFilled;
    //endregion

    Character(String _name, String _surname, float _money, int _lvl, int _dex, int _inte, int _cons, int _strg, int _pow, int _ate){
        name = _name;
        surname = _surname;
        dex = _dex;
        inte = _inte;
        cons = _cons;
        strg = _strg;
        pow = _pow;
        ate = _ate;
        lvl = _lvl;
        money = _money;

        isFilled = false;

    }

    public boolean isFilled() {
        return isFilled;
    }

    public void setFilled(){
        isFilled = !isFilled;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setImage(Bitmap _bit){
        image = _bit;
    }

    public Bitmap getImage(){
        return image;
    }

    public ArrayList<Item> getInventory(){return inventory;}
    public void setItemToInventory(Item _item){inventory.add(_item);}
    public void removeItemFromInventory(Item _item){
        for(int i = 0; i < inventory.size(); i++){
            if(inventory.get(i) == _item){
                inventory.remove(i);
            }
        }
    }

    public void setMoney(float _money){money += _money;}
    public float getMoney(){return money;}

    public void sleep(){
        magic = max_magic;
        life += cons * 5;
        if(life > max_life){life -= life - max_life;}
    }

    public String getName(){
        return name;
    }

    public String getSurname(){
        return surname;
    }

    public int getLvl(){
        return lvl;
    }

    public void setSecundaries(int _dex, int _inte, int _strg, int _ate){
        s_dex = _dex;
        s_inte = _inte;
        s_strg = _strg;
        s_ate = _ate;
    }

    public void setMaxedStats(int _max_life, int _max_magic, int _max_mad){
        max_life = _max_life;
        max_mad = _max_mad;
        max_magic = _max_magic;

        life = max_life;
        mad = max_mad;
        magic = max_magic;
    }

    public int get_attr(String _attr){

        switch(_attr) {
            case Constants.DEX:
                return dex;
            case Constants.INTE:
                return inte;
            case Constants.CONS:
                return cons;
            case Constants.STRG:
                return strg;
            case Constants.POW:
                return pow;
            case Constants.ATE:
                return ate;
            default:
                return 0;
        }
    }

    public int get_s_attr(String _attr){
        switch(_attr) {
            case Constants.DEX:
                return s_dex;
            case Constants.INTE:
                return s_inte;
            case Constants.STRG:
                return s_strg;
            case Constants.ATE:
                return s_ate;
            default:
                return 0;
        }
    }

    public int get_max_stats(String _stat){
        switch(_stat) {
            case Constants.LIFE:
                return max_life;
            case Constants.MAD:
                return max_mad;
            case Constants.MAGIC:
                return max_magic;
            default:
                return 0;

        }
    }

    public int get_current_stats(String _stat){
        switch(_stat) {
            case Constants.LIFE:
                return life;
            case Constants.MAD:
                return mad;
            case Constants.MAGIC:
                return magic;
            default:
                return 0;

        }
    }

    public void set_current_stats(String _stat, int _points){

        switch(_stat) {
            case Constants.LIFE:
                life += _points;
                if(life > max_life){life -= (life - max_life);}
                break;
            case Constants.MAD:
                mad += _points;
                if(mad > max_mad){mad -= (mad - max_mad);}
                break;
            case Constants.MAGIC:
                magic += _points;
                if(magic > max_magic){magic -= (magic - max_magic);}
                break;

        }
    }

    public int get_madness_penalties(){
        if(max_mad == 0){
            return 0;
        }

        float madnessPercentage = (mad * 100) / max_mad;
        if(madnessPercentage > 50){
            return 0;
        }

        if(madnessPercentage <= 25){
            return -5;
        }
        else{
            return -2;
        }

    }

}
