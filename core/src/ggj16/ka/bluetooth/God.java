package ggj16.ka.bluetooth;

public class God {

    public final int id;
    public final String name, description, spell, spellDescription;
    public final boolean male;

    public God(int id, String name, String description, boolean male, String spell, String spellDescription) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.male = male;
        this.spell = spell;
        this.spellDescription = spellDescription;
    }
}