package com.javaadash.tc2.core.server.netty.listener;

import java.util.Map;

import main.DeckGenerator;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.listener.DataListener;
import com.javaadash.tc2.core.GameState;
import com.javaadash.tc2.core.TC2AsynchronousGameManager;
import com.javaadash.tc2.core.card.Deck;
import com.javaadash.tc2.core.context.GameContext;
import com.javaadash.tc2.core.exceptions.TC2CoreException;
import com.javaadash.tc2.core.interfaces.message.JoinGameMessage;
import com.javaadash.tc2.core.interfaces.player.Player;
import com.javaadash.tc2.server.SocketIoPlayerInterface;
import com.javaadash.tc2.server.TC2Lobby;

public class JoinGameListener implements DataListener<JoinGameMessage> {

  static int nbActions = 2;

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

      Player p1 = null;
      try {
        Deck deck1 = DeckGenerator.getDeck(nbActions);
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

      Player p2 = null;
      try {
        Deck deck2 = DeckGenerator.getDeck(nbActions);
        p2 = new Player(msg.getUsername(), deck2, 5, new SocketIoPlayerInterface(client));
      } catch (TC2CoreException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }

      GameContext gameContext = new GameContext();
      gameContext.setState(GameState.BEGINNING);
      gameContext.setTurn(0);
      gameContext.setFirstPlayer(pendingGameRequest.getValue());
      gameContext.setSecondPlayer(p2);

      lobby.removePendingGameRequest(roomId);
      lobby.getCurrentGames().put(roomId, gameContext);

      gameManager.handleGame(gameContext);
    }
  }
}
