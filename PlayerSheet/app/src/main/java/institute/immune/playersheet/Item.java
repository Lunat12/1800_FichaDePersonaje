package institute.immune.playersheet;

public class Item {
    //region ATTRIBUTES
    private String name;
    private int id;
    private int character;
    //endregion

    Item(String _name, int _character){
        name = _name;
        character = _character;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getCharacter() {
        return character;
    }

    public void setId(int id) {
        this.id = id;
    }
}
