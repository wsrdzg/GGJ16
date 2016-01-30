package ggj16.ka.bluetooth;

import com.badlogic.gdx.utils.Array;

/**
 * Created by floatec on 30/01/16.
 */
public class QuestFactory {

    public static Array<God> gods;

    static {
        gods = new Array<God>();
        gods.add(new God("Apu","God or spirit of mountains.","God"));
        gods.add(new God("Cavillace","Virgin Goddess","Goddess"));
        gods.add(new God("Chasca","Goddess of dawn and twilight and Venus.","Goddess"));
        gods.add(new God("Coniraya","Moon God.","God"));
        gods.add(new God("Ekkeko","God of the hearth and wealth.","God"));
        gods.add(new God("Illapa","Weather God","God"));
        gods.add(new God("Inti","Sun God","God"));
        gods.add(new God("Kon","God of rain.","God"));
        gods.add(new God("Kuka Mama","Goddess of health and joy.","Goddess"));
        gods.add(new God("Mama Cocha","Sea and fish goddess, protectress of sailors and fishermen.","Goddess"));
        gods.add(new God("Mama Quilla","Goodess of marriage, festival and the moon.","Goddess"));
        gods.add(new God("Mama Zara","Goddess of grain.","Goddess"));
        gods.add(new God("Pacha Camac","Chthonic creator god.","God"));
        gods.add(new God("Pariacaca","God of water and rainstorms.","God"));
        gods.add(new God("Supay","God of death.","God"));
        gods.add(new God("Urcuchillay","God that watched over animals.","God"));
        gods.add(new God("Viracocha","God of everything.","God"));
    }

    public static Quest getQuest(int questNumer, Array<Symbol> symbols){
        Quest quest = new Quest();
        quest.id = "Fight against "+gods.get(questNumer).name;
        quest.maxTime = 5000;
        quest.symbols = new Array<>();
        quest.symbols.add(symbols.get((187*(questNumer+9))%symbols.size));
        quest.symbols.add(symbols.get((1127*(questNumer+19))%symbols.size));
        quest.symbols.add(symbols.get((1843*(questNumer+999))%symbols.size));
        quest.symbols.add(symbols.get((1874*(questNumer+90))%symbols.size));
        quest.symbols.add(symbols.get((1311*(questNumer+29))%symbols.size));
        quest.symbols.add(symbols.get((122*(questNumer+29))%symbols.size));
        quest.symbols.add(symbols.get((181 *(questNumer+39)) % symbols.size));

        return quest;
    }
}
