package ggj16.ka.bluetooth;

/**
 * Created by floatec on 29/01/16.
 */
public class QuestSolver {
    public Quest quest;
    public int position=0;
    public boolean solved;

    public boolean next(Symbol clickedSymbol){
        if(quest.symbols.get(position)==clickedSymbol){
            position++;
            solved=quest.symbols.size==position;
            // TODO bluetooth here result should be returned
            return true;
        }
        return false;
    }

}
