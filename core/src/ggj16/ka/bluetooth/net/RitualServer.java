package ggj16.ka.bluetooth.net;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;

import java.util.HashMap;

import ggj16.ka.bluetooth.QuestFactory;

public class RitualServer extends ServerInterface {

    // ritual -> frequency
    HashMap<Integer, Integer> knownRituals;
    HashMap<Client,Integer> clientSteps;

    public RitualServer() {
        knownRituals = new HashMap<>();
        clientSteps= new HashMap<>();
    }

    @Override
    public void clientConnected(Client client) {
        super.clientConnected(client);
        Gdx.app.log("RitualServer", "client connected");

        clientSteps.put(client, 0);
    }

    @Override
    public void messageReceived(Client client, Message message) {
        Gdx.app.log("Ritual server", message.toString());
        switch (message.t) {
            case I_KNOW_RITUALS:
                // the client knows these rituals

                for (int r = 0; r < message.ia.size; r++) {
                    int ritual = message.ia.get(r);
                    Gdx.app.log("RitualServer", "contains: " + ritual);
                    if (knownRituals.containsKey(ritual)) {
                        knownRituals.put(ritual, knownRituals.get(ritual) + 1);
                    } else {
                        knownRituals.put(ritual, 1); // first time of this ritual
                    }
                }

                break;
            case STEP:
                clientSteps.put(client, message.i);

                if(!sycron() || !message.b){
                    sendToAllClients(new Message(Message.Type.LOST));
                    for (Client cli: clientSteps.keySet()) {
                        clientSteps.put(cli, 0);
                    }
                    return;
                }

                // have we won?
                boolean won = true;
                for (int clientStep: clientSteps.values()) {
                    if (QuestFactory.symbols.size-1 != clientStep) {
                        won = false;
                        break;
                    }
                }

                if (won) {
                    sendToAllClients(new Message(Message.Type.WIN));
                }



                // step is in message.i
                // successful in message.b
                // TODO: check if all clients submitted their steps in the right order.
                break;
        }
    }

    // start the game
    public void startGame() {
        // select a random ritual
        //  TODO priotize less frequent rituals

        Array<Integer> rituals = new Array<>();

        for (Integer rit : knownRituals.keySet()) {
            rituals.add(rit);
            Gdx.app.log("RitualServer", "add "  +rit + " [quant: " + knownRituals.get(rit));
        }

        int rnd = (int) (Math.random() * rituals.size);
        int selectedRitual = rituals.get(rnd);

        // tell all clients that we want to start this ritual
        sendToAllClients(new Message(Message.Type.START_GAME, selectedRitual));
    }

    private boolean sycron(){
        Gdx.app.log("syncron", clientSteps.values().size()+" :O");

        for (int step:clientSteps.values()) {
            for (int other:clientSteps.values()){
                Gdx.app.log("RitualServer", "diff: " + (step-other));
                if(Math.abs(step-other)>1){
                    return false;
                }
            }
        }
        return true;
    }
}
