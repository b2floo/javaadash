package com.javaadash.tc2.server;

import com.javaadash.tc2.client.TC2PlayerInterface;
import com.javaadash.tc2.server.message.JoinGameMessage;

public interface TC2ServerInterface {

  public void joinAnyGame(JoinGameMessage joinGameMessage, TC2PlayerInterface playerInterface);
}
