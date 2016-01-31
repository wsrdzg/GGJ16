package ggj16.ka.bluetooth.net;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;

import java.util.HashMap;

import ggj16.ka.bluetooth.Main;
import ggj16.ka.bluetooth.QuestFactory;

public class RitualServer extends ServerInterface {

    // ritual -> frequency
    HashMap<Integer, Integer> knownRituals;
    HashMap<Client,Integer> clientSteps;
    public Thread ping;
    public int lowestCategorieOfPlayers=16;

    public RitualServer() {
        knownRituals = new HashMap<>();
        clientSteps= new HashMap<>();
    }

    @Override
    public void clientConnected(Client client) {
        super.clientConnected(client);
        Gdx.app.log("RitualServer", "client connected");

        clientSteps.put(client, 0);

        // keep the connection alive
        ping = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        return;
                    }
                    sendToAllClients(new Message(Message.Type.PING));
                }
            }
        });
        ping.start();
    }

    @Override
    public void messageReceived(Client client, Message message) {
        Gdx.app.log("Ritual server", message.toString());
        switch (message.t) {
            case I_KNOW_RITUALS:
                // the client knows these rituals
                int heighest=0;
                for (int r = 0; r < message.ia.size; r++) {
                    int ritual = message.ia.get(r);
                    if(ritual>heighest){
                        heighest=ritual;
                    }
                    Gdx.app.log("RitualServer", "contains: " + ritual);
                    if (knownRituals.containsKey(ritual)) {
                        knownRituals.put(ritual, knownRituals.get(ritual) + 1);
                    } else {
                        knownRituals.put(ritual, 1); // first time of this ritual
                    }
                }
                if(heighest<lowestCategorieOfPlayers){
                    lowestCategorieOfPlayers=heighest;
                }

                break;
            case CONTINUE:
                sendToAllClients(new Message(Message.Type.CONTINUE));
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
                    int max=(lowestCategorieOfPlayers/4+2)*4;

                    int newRitual= MathUtils.random((lowestCategorieOfPlayers/4)*4,max<16?max:16);
                    knownRituals.put(newRitual,4);
                    sendToAllClients(new Message(Message.Type.WIN,newRitual));
                }



                // step is in message.i
                // successful in message.b
                // TODO: check if all clients submitted their steps in the right order.
                break;
            case PING:
                Gdx.app.log("RitualServer", "PING from " + client.getName());
                client.sendMessage(new Message(Message.Type.PONG));
                break;
            case PONG:
                Gdx.app.log("RitualServer", "PONG from " + client.getName());
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

    // if one client disconnects
    public void disconnected(String name) {
        clients.clear();
        ((Main)Gdx.app.getApplicationListener()).disconnected(name);
    }
}
