package ggj16.ka.bluetooth;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.IntArray;

public class QuestFactory {

    public final static Array<God> GODS = new Array<>();

    public final static IntArray myRituals = new IntArray();

    public static God god;
    public static Array<Symbol> symbols = new Array<>();

    public static int position;
    public static boolean solved;
    public static boolean learMode;

    public static long maxTime;

    static {
        GODS.add(new God(0, "Apu", "God or spirit of mountains.", "God", "Spirit of the mountains", "This ritual will help for mountain gods"));
        GODS.add(new God(1, "Cavillace", "Virgin Goddess", "Goddess", "Venus chant", "You will need it once you mit a vergin goddess"));
        GODS.add(new God(2, "Chasca", "Goddess of dawn and twilight.", "Goddess", "Vanish of the sun", "brake the spirit of the dawn"));
        GODS.add(new God(3, "Coniraya", "Moon God.", "God", "low tide dance", "Helps you against the force of the moon"));
        GODS.add(new God(4, "Ekkeko", "God of the hearth and wealth.", "God", "clapping of the heavy rain", "helps you to turn out the fire"));
        GODS.add(new God(5, "Illapa", "Weather God", "God", "Shield of Protect", "Protects you of storm and lightning"));
        GODS.add(new God(6, "Inti", "Sun God", "God", "Force of reflection", "Helps to reflect light"));
        GODS.add(new God(7, "Kon", "God of rain.", "God", "whistle of storm", "Helps you to fight rain"));
        GODS.add(new God(8, "Kuka Mama", "Goddess of health and joy.", "Goddess", "plague of illness", "This ritual will kill eaven a goddess of health"));
        GODS.add(new God(9, "Mama Cocha", "Sea and fish goddess", "Goddess", "Ritual of the sea monster", "This will bring the horror of the sea to live."));
       /* GODS.add(new God("Mama Quilla","Goodess of marriage and festival","Goddess",""));
        GODS.add(new God("Mama Zara","Goddess of grain.","Goddess"));
        GODS.add(new God("Pacha Camac","Chthonic creator god.","God"));
        GODS.add(new God("Pariacaca","God of water and rainstorms.","God"));
        GODS.add(new God("Supay","God of death.","God"));
        GODS.add(new God("Urcuchillay","God that watched over animals.","God"));
        GODS.add(new God("Viracocha","God of everything.","God"));*/
    }

    public static void createQuest(int questNumber, boolean learMode) {
        god = GODS.get(questNumber);
        maxTime = 5000;
        solved = false;
        QuestFactory.learMode = learMode;
    }

    public static void startQuest(Array<Symbol> symbols) {
        position = 0;

        QuestFactory.symbols.clear();
        QuestFactory.symbols.add(symbols.get((187*(god.id+9))%symbols.size));
        QuestFactory.symbols.add(symbols.get((1127*(god.id+19))%symbols.size));
        QuestFactory.symbols.add(symbols.get((1843*(god.id+999))%symbols.size));
        QuestFactory.symbols.add(symbols.get((1874*(god.id+90))%symbols.size));
        QuestFactory.symbols.add(symbols.get((1311*(god.id+29))%symbols.size));
        QuestFactory.symbols.add(symbols.get((122*(god.id+29))%symbols.size));
        QuestFactory.symbols.add(symbols.get((181 * (god.id + 39)) % symbols.size));

        if (learMode)
            QuestFactory.symbols.get(position).setHighlight(true);
    }

    public static boolean next(Symbol clickedSymbol) {
        if (symbols.get(position).equals(clickedSymbol)) {
            if (learMode)
                symbols.get(position).setHighlight(false);
            solved = symbols.size == position + 1;
            position = (position + 1) % symbols.size;
            if (learMode)
                symbols.get(position).setHighlight(true);

            // TODO bluetooth here result should be returned
            return true;
        }
        return false;
    }
}