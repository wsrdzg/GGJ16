package ggj16.ka.bluetooth;

/**
 * Created by floatec on 29/01/16.
 */
public class QuestSolver {
    public Quest quest;
    public int position=0;
    public boolean solved;
    public boolean learMode=false;

    public boolean next(Symbol clickedSymbol){
        if(quest.symbols.get(position)==clickedSymbol){
            if(learMode){
                unheighlightNext();
            }
            position++;
            if(learMode){
                heighlightNext();
            }
            solved=quest.symbols.size==position;
            // TODO bluetooth here result should be returned
            return true;
        }
        return false;
    }

    public void heighlightNext(){
        quest.symbols.get(position).scale*=1.5f;
    }
    public void unheighlightNext(){
        quest.symbols.get(position).scale/=1.5f;
    }


}
