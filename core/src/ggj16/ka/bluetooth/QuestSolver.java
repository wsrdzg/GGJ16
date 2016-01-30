package ggj16.ka.bluetooth;

/**
 * Created by floatec on 29/01/16.
 */
public class QuestSolver {
    public Quest quest;
    public int position=0;
    public boolean solved;
    private boolean learMode=false;



    public boolean next(Symbol clickedSymbol){
        if(quest.symbols.get(position)==clickedSymbol){
            if(learMode){
                unheighlightNext();
            }
            solved=quest.symbols.size==position+1;
            position=(position+1)%quest.symbols.size;
            if(learMode){
                heighlightNext();
            }

            // TODO bluetooth here result should be returned
            return true;
        }
        return false;
    }

    public void heighlightNext(){
        quest.symbols.get(position).scaleFactor*=2f;
    }
    public void unheighlightNext(){
        quest.symbols.get(position).scaleFactor/=2f;
    }

    public boolean isLearMode() {
        return learMode;
    }

    public void setLearMode(boolean learMode) {
        if(this.learMode!=learMode) {
            if(learMode){
                heighlightNext();
            }else{
                unheighlightNext();
            }
            this.learMode = learMode;
        }
    }
}
