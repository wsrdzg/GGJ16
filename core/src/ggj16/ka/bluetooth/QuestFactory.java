package ggj16.ka.bluetooth;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.IntArray;

public class QuestFactory {

    public final static Array<God> GODS = new Array<>();
    static {
        GODS.add(new God(0, "Apu", "God or spirit of mountains.", true, "Spirit of the mountains", "This ritual will help for mountain gods"));
        GODS.add(new God(1, "Cavillace", "Virgin Goddess", false, "Venus chant", "You will need it once you meet a virgin goddess"));
        GODS.add(new God(2, "Chasca", "Goddess of dawn and twilight.", false, "Vanish of the sun", "brake the spirit of the dawn"));
        GODS.add(new God(3, "Coniraya", "Moon God.", true, "low tide dance", "Helps you against the force of the moon"));
        GODS.add(new God(4, "Ekkeko", "God of the hearth and wealth.", true, "Clapping of the heavy rain", "helps you to turn out the fire"));
        GODS.add(new God(5, "Illapa", "Weather God", true, "Shield of Protect", "Protects you of storm and lightning"));
        GODS.add(new God(6, "Inti", "Sun God", true, "Force of reflection", "Helps to reflect light"));
        GODS.add(new God(7, "Kon", "God of rain.", true, "Whistle of storm", "Helps you to fight rain"));
        GODS.add(new God(8, "Kuka Mama", "Goddess of health and joy.", false, "Plague of illness", "This ritual will kill eaven a goddess of health"));
        GODS.add(new God(9, "Mama Cocha", "Sea and fish goddess", false, "Ritual of the sea monster", "This will bring the horror of the sea to live."));
        GODS.add(new God(10,"Mama Quilla","Goodess of marriage and festival",false,"Unhappy rain",""));
        GODS.add(new God(11,"Mama Zara","Goddess of grain.",false,"Ritual of the grasshopper",""));
        GODS.add(new God(12,"Pacha Camac","Chthonic creator god.",true,"Sing of the melodie",""));
        GODS.add(new God(13,"Pariacaca","God of water and rainstorms.",true,"Sound of the sun",""));
        GODS.add(new God(14,"Supay","God of death.",true,"Dance of life",""));
        GODS.add(new God(15,"Urcuchillay","God that watched over animals.",true,"Ritual of the hunter",""));
        //GODS.add(new God(16,"Viracocha","God of everything.",true,"",""));
    }


    public final static IntArray myRituals = new IntArray();

    public static God god;

    public static boolean solved, learMode;
    public static long maxTime;

    public static Array<Symbol> symbols = new Array<>();
    public static int position;


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
        /*QuestFactory.symbols.add(symbols.get((1874*(god.id+90))%symbols.size));
        QuestFactory.symbols.add(symbols.get((1311*(god.id+29))%symbols.size));
        QuestFactory.symbols.add(symbols.get((122*(god.id+29))%symbols.size));
        QuestFactory.symbols.add(symbols.get((181 * (god.id + 39)) % symbols.size));*/

        for (Symbol symbol : symbols)
            symbol.reset();

        if (learMode)
            QuestFactory.symbols.first().setHighlight(true);

        for (Symbol symbol : QuestFactory.symbols)
            symbol.spawn(QuestFactory.symbols);
    }

    public static boolean next(Symbol clickedSymbol) {
        //if (position == symbols.size)
        //    return false;
        if (symbols.get(position).equals(clickedSymbol)) {
            if (learMode)
                symbols.get(position).setHighlight(false);
            solved = symbols.size == position + 1;
            position++;
            if (learMode && position < symbols.size)
                symbols.get(position).setHighlight(true);

            // TODO bluetooth here result should be returned
            return true;
        }
        return false;
    }
}