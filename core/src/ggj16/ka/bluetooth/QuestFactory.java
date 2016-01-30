package ggj16.ka.bluetooth;

import com.badlogic.gdx.utils.Array;

/**
 * Created by floatec on 30/01/16.
 */
public class QuestFactory {
    public static Quest getQuest(int questNumer, Array<Symbol> symbols){
        Quest quest = new Quest();
        quest.id = "Kill Xardas";
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
