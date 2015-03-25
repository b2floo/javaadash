package com.javaadash.tc2.server.listener;

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

  static Map<Integer, Integer> limits = new HashMap<Integer, Integer>();
  static int nbActions = 2;
  static int nbCharacters = 2;
  static {

    limits.put(CardType.ACTION, nbActions);
    limits.put(CardType.CHARACTER, nbCharacters);
  }

  private TC2Lobby lobby;
  private TC2AsynchronousGameManager gameManager = new TC2AsynchronousGameManager();

  public JoinGameListener(TC2Lobby lobby) {
    this.lobby = lobby;
  }

  @Override
  public void onData(final SocketIOClient client, JoinGameMessage msg, final AckRequest ackRequest) {

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
      client.set("roomId", client.getSessionId().toString());
      client.set("username", msg.getUsername());
    } else {
      String roomId = pendingGameRequest.getKey();
      client.set("roomId", roomId);
      client.set("username", msg.getUsername());

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
      gameContext.setState(GameState.BEGINNING);
      gameContext.setTurn(0);
      gameContext.setFirstPlayer(pendingGameRequest.getValue());
      gameContext.setSecondPlayer(p1);

      lobby.removePendingGameRequest(roomId);
      lobby.getCurrentGames().put(roomId, gameContext);

      gameManager.handleGame(gameContext);
    }
  }
}
