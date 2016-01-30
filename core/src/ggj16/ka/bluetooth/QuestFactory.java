package ggj16.ka.bluetooth;

import com.badlogic.gdx.utils.Array;

/**
 * Created by floatec on 30/01/16.
 */
public class QuestFactory {

    public static Array<God> gods;

    static {
        gods = new Array<God>();
        gods.add(new God("Apu","God or spirit of mountains.","God","Spirit of the mountains","This ritual will help for mountain gods"));
        gods.add(new God("Cavillace","Virgin Goddess","Goddess","Venus chant","You will need it once you mit a vergin goddess"));
        gods.add(new God("Chasca","Goddess of dawn and twilight.","Goddess","Vanish of the sun","brake the spirit of the dawn"));
        gods.add(new God("Coniraya","Moon God.","God","low tide dance","Helps you against the force of the moon"));
        gods.add(new God("Ekkeko","God of the hearth and wealth.","God","clapping of the heavy rain","helps you to turn out the fire"));
        gods.add(new God("Illapa","Weather God","God","Shield of Protect","Protects you of storm and lightning"));
        gods.add(new God("Inti","Sun God","God","Force of reflection","Helps to reflect light"));
        gods.add(new God("Kon","God of rain.","God","whistle of storm","Helps you to fight rain"));
        gods.add(new God("Kuka Mama","Goddess of health and joy.","Goddess","plague of illness","This ritual will kill eaven a goddess of health"));
        gods.add(new God("Mama Cocha","Sea and fish goddess","Goddess","Ritual of the sea monster","This will bring the horror of the sea to live."));
       /* gods.add(new God("Mama Quilla","Goodess of marriage and festival","Goddess",""));
        gods.add(new God("Mama Zara","Goddess of grain.","Goddess"));
        gods.add(new God("Pacha Camac","Chthonic creator god.","God"));
        gods.add(new God("Pariacaca","God of water and rainstorms.","God"));
        gods.add(new God("Supay","God of death.","God"));
        gods.add(new God("Urcuchillay","God that watched over animals.","God"));
        gods.add(new God("Viracocha","God of everything.","God"));*/
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
