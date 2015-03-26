package com.javaadash.tc2.core.server.netty.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.listener.DataListener;
import com.javaadash.tc2.core.GameState;
import com.javaadash.tc2.core.TC2AsynchronousGameManager;
import com.javaadash.tc2.core.context.GameContext;
import com.javaadash.tc2.core.interfaces.message.ChooseDiscardMessage;
import com.javaadash.tc2.core.interfaces.player.PlayerData;
import com.javaadash.tc2.server.TC2Lobby;

public class ChooseDiscardListener implements DataListener<ChooseDiscardMessage> {
  private Logger log = LoggerFactory.getLogger(ChooseDiscardListener.class);

  private TC2Lobby lobby;
  private TC2AsynchronousGameManager gameManager = new TC2AsynchronousGameManager();

  public ChooseDiscardListener(TC2Lobby lobby) {
    this.lobby = lobby;
  }

  @Override
  public void onData(SocketIOClient client, ChooseDiscardMessage msg, AckRequest ack)
      throws Exception {
    log.debug("Received choose discard from " + client.get("username"));

    GameContext context = lobby.getCurrentGames().get(client.get("roomId"));
    if (context == null) {
      throw new IllegalStateException("Unable to find context associated to id: "
          + client.get("roomId"));
    }

    PlayerData playerData = context.getPlayerData((String) client.get("username"));
    if (playerData == null) {
      throw new IllegalStateException("Unable to find playerData associated to username: "
          + client.get("username"));
    }

    if (playerData.getPlayerState() >= GameState.PLAYER_CHOOSE_DISCARD) {
      throw new IllegalStateException("Current players state [" + playerData.getPlayerState()
          + "] can' handle a choose_discard message from : " + client.get("username"));
    }

    playerData.setPlayerState(GameState.PLAYER_CHOOSE_DISCARD);
    playerData.getPlayedCards().addAll(msg.getDiscardCards());

    gameManager.handleGame(context);
  }
}
