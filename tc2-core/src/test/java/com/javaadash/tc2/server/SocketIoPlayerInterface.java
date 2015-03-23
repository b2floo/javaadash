package com.javaadash.tc2.server;

import java.io.IOException;
import java.util.Collection;

import com.javaadash.tc2.core.card.Card;
import com.javaadash.tc2.core.exceptions.TC2CoreException;
import com.javaadash.tc2.core.interfaces.player.TC2PlayerInterface;

public class SocketIoPlayerInterface implements TC2PlayerInterface {

  private static final long serialVersionUID = 1704548344081679272L;
  private String roomName = "";

  public SocketIoPlayerInterface(String roomName) {
    this.roomName = roomName;
  }

  public String getRoomName() {
    return roomName;
  }

  @Override
  public void startGame() throws IOException, TC2CoreException {
    // TODO Auto-generated method stub

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

}
