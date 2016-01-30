package ggj16.ka.bluetooth;

public class God {

    public final int id;
    public final String name, description, type, spell, spellDescription;

    public God(int id, String name, String description, String type, String spell, String spellDescription) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.type = type;
        this.spell = spell;
        this.spellDescription = spellDescription;
    }
}