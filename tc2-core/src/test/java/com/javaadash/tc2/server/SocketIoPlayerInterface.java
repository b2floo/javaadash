package com.javaadash.tc2.server;

import java.io.IOException;
import java.util.Collection;

import com.corundumstudio.socketio.SocketIOClient;
import com.javaadash.tc2.core.card.Card;
import com.javaadash.tc2.core.exceptions.TC2CoreException;
import com.javaadash.tc2.core.interfaces.message.EndGameMessage;
import com.javaadash.tc2.core.interfaces.message.StartGameMessage;
import com.javaadash.tc2.core.interfaces.message.UpdateGameMessage;
import com.javaadash.tc2.core.interfaces.player.TC2PlayerInterface;

public class SocketIoPlayerInterface implements TC2PlayerInterface {

  private static final long serialVersionUID = 1704548344081679272L;
  private SocketIOClient client = null;

  public SocketIoPlayerInterface(SocketIOClient client) {
    this.client = client;
  }

  @Override
  public void startGame(StartGameMessage msg) throws IOException, TC2CoreException {
    client.sendEvent("start_game", msg);
  }

  @Override
  public Card selectCharacter(Collection<Card> availableCharacters) throws IOException,
      TC2CoreException {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Card selectAction(Collection<Card> availableActions) throws IOException, TC2CoreException {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void updateGameStatus(UpdateGameMessage msg) throws IOException, TC2CoreException {
    client.sendEvent("update_game", msg);
  }

  @Override
  public void endGame(EndGameMessage msg) {
    client.sendEvent("end_game", msg);
  }
}
