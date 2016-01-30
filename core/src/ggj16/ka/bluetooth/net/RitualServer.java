package ggj16.ka.bluetooth.net;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;

import java.util.HashMap;

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


    }

    @Override
    public void messageReceived(Client client, Message message) {

        switch (message.t) {
            case I_KNOW_RITUALS:
                // the client knows these rituals

                for (int ritual : message.ia.items) {
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
                    clientSteps= new HashMap<>();
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
        }

        int rnd = (int) (Math.random() * rituals.size);
        int selectedRitual = rituals.get(rnd);

        // tell all clients that we want to start this ritual
        sendToAllClients(new Message(Message.Type.START_GAME, selectedRitual));
    }

    private boolean sycron(){
        for (int step:clientSteps.values()) {
            for (int other:clientSteps.values()){
                if(Math.abs(step-other)>1){
                    return false;
                }
            }
        }
        return true;
    }
}
