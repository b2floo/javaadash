package com.javaadash.tc2.server.listeners;

import java.util.HashMap;
import java.util.Map;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.listener.DataListener;
import com.javaadash.tc2.core.CardType;
import com.javaadash.tc2.core.GameState;
import com.javaadash.tc2.core.TC2AsynchronousGameManager;
import com.javaadash.tc2.core.card.Card;
import com.javaadash.tc2.core.card.Deck;
import com.javaadash.tc2.core.context.GameContext;
import com.javaadash.tc2.core.exceptions.TC2CoreException;
import com.javaadash.tc2.core.interfaces.message.JoinGameMessage;
import com.javaadash.tc2.core.interfaces.player.Player;
import com.javaadash.tc2.server.SocketIoPlayerInterface;
import com.javaadash.tc2.server.TC2Lobby;

public class JoinGameListener implements DataListener<JoinGameMessage> {

  static TC2Lobby lobby = new TC2Lobby();
  static Map<Integer, Integer> limits = new HashMap<Integer, Integer>();
  static int nbActions = 2;
  static int nbCharacters = 2;
  static {

    limits.put(CardType.ACTION, nbActions);
    limits.put(CardType.CHARACTER, nbCharacters);
  }

  @Override
  public void onData(SocketIOClient client, JoinGameMessage msg, AckRequest ack) throws Exception {
    System.out.println("Received msg from " + msg.getUsername());

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
        p1 = new Player(msg.getUsername(), deck1, 5, new SocketIoPlayerInterface(client));
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
        p1 = new Player(msg.getUsername(), deck1, 5, new SocketIoPlayerInterface(client));
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

}
