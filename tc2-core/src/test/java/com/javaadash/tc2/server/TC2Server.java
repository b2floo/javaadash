package com.javaadash.tc2.server;

import java.util.HashMap;
import java.util.Map;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.DataListener;
import com.javaadash.tc2.core.CardType;
import com.javaadash.tc2.core.GameState;
import com.javaadash.tc2.core.TC2AsynchronousGameManager;
import com.javaadash.tc2.core.card.Card;
import com.javaadash.tc2.core.card.Deck;
import com.javaadash.tc2.core.context.GameContext;
import com.javaadash.tc2.core.exceptions.TC2CoreException;
import com.javaadash.tc2.core.interfaces.player.Player;

public class TC2Server {

  static TC2Lobby lobby = new TC2Lobby();
  static Map<Integer, Integer> limits = new HashMap<Integer, Integer>();
  static int nbActions = 2;
  static int nbCharacters = 2;
  static {

    limits.put(CardType.ACTION, nbActions);
    limits.put(CardType.CHARACTER, nbCharacters);
  }


  public static void main(String[] args) throws InterruptedException {

    Configuration config = new Configuration();
    config.setHostname("localhost");
    config.setPort(9092);
    config.setOrigin("http://localhost");

    final SocketIOServer server = new SocketIOServer(config);

    server.addEventListener("join_any_game", JoinGameAttrs.class,
        new DataListener<JoinGameAttrs>() {

          @Override
          public void onData(final SocketIOClient client, JoinGameAttrs attrs,
              final AckRequest ackRequest) {

            System.out.println("Received msg from " + attrs.getUsername());

            Map.Entry<String, Player> pendingGameRequest = lobby.getPendingGameRequest();

            if (pendingGameRequest == null) {
              System.out.println("pendingGameRequest == null");

              Deck deck1 = new Deck(limits);
              Player p1 = null;
              try {
                deck1.addCard(new Card(CardType.CHARACTER, "ABI"));
                deck1.addCard(new Card(CardType.CHARACTER, "ABO"));
                for (int i = 0; i < nbActions; i++) {
                  deck1.addCard(new Card(CardType.ACTION, "ACTION" + i));
                }
                p1 = new Player(attrs.getUsername(), deck1, 5, new SocketIoPlayerInterface(client));
              } catch (TC2CoreException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
              }

              lobby.addPendingGameRequest(client.getSessionId().toString(), p1);
              client.joinRoom(client.getSessionId().toString());
            } else {
              String roomId = pendingGameRequest.getKey();
              client.joinRoom(roomId);

              Deck deck1 = new Deck(limits);
              Player p1 = null;
              try {
                deck1.addCard(new Card(CardType.CHARACTER, "ABI"));
                deck1.addCard(new Card(CardType.CHARACTER, "ABO"));
                for (int i = 0; i < nbActions; i++) {
                  deck1.addCard(new Card(CardType.ACTION, "ACTION" + i));
                }
                p1 = new Player(attrs.getUsername(), deck1, 5, new SocketIoPlayerInterface(client));
              } catch (TC2CoreException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
              }

              GameContext gameContext = new GameContext();
              gameContext.setFirstPlayer(pendingGameRequest.getValue());
              gameContext.setSecondPlayer(p1);
              gameContext.setState(GameState.BEGINNING);
              lobby.removePendingGameRequest(roomId);
              lobby.getCurrentGames().put(roomId, gameContext);

              TC2AsynchronousGameManager gameManager = new TC2AsynchronousGameManager();
              gameManager.handleGame(gameContext);
            }
          }
        });

    server.start();

    Thread.sleep(Integer.MAX_VALUE);

    server.stop();
  }
}
