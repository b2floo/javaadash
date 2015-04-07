package com.javaadash.tc2.core.server.netty.listener;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
import com.javaadash.tc2.server.StaticDeckGenerator;
import com.javaadash.tc2.server.TC2Lobby;

public class JoinGameListener implements DataListener<JoinGameMessage> {

  private Logger log = LoggerFactory.getLogger(JoinGameListener.class);
  static int nbActions = 10;

  private TC2Lobby lobby;
  private TC2AsynchronousGameManager gameManager = new TC2AsynchronousGameManager();

  public JoinGameListener(TC2Lobby lobby) {
    this.lobby = lobby;
  }

  @Override
  public void onData(final SocketIOClient client, JoinGameMessage msg, final AckRequest ackRequest) {

    log.debug("Received join request from " + msg.getUsername());

    Map.Entry<String, Player> pendingGameRequest = lobby.getPendingGameRequest();

    if (pendingGameRequest == null) {
      log.debug("No game request pending, creating a new room ");

      Player p1 = null;
      try {
        Deck deck1 = StaticDeckGenerator.getDeck(nbActions);
        p1 = new Player(msg.getUsername(), deck1, 5, new SocketIoPlayerInterface(client));
      } catch (TC2CoreException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }

      lobby.addPendingGameRequest(client.getSessionId().toString(), p1);
      client.set("roomId", client.getSessionId().toString());
      client.set("username", msg.getUsername());
      log.debug("New room created with id: " + client.getSessionId());
    } else {
      String roomId = pendingGameRequest.getKey();
      log.debug("Joining pending game " + roomId);
      client.set("roomId", roomId);
      client.set("username", msg.getUsername());

      Player p2 = null;
      try {
        Deck deck2 = StaticDeckGenerator.getDeck(nbActions);
        p2 = new Player(msg.getUsername(), deck2, 5, new SocketIoPlayerInterface(client));
      } catch (TC2CoreException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }

      GameContext gameContext = new GameContext(pendingGameRequest.getValue(), p2);
      gameContext.setState(GameState.BEGIN_GAME);
      gameContext.setTurn(1);

      lobby.removePendingGameRequest(roomId);
      lobby.getCurrentGames().put(roomId, gameContext);

      gameManager.handleGame(gameContext);
    }
  }
}
