package com.javaadash.tc2.client.netty;

import java.io.IOException;

import com.corundumstudio.socketio.SocketIOClient;
import com.javaadash.tc2.client.TC2PlayerInterface;
import com.javaadash.tc2.client.message.StartGameMessage;

public class SocketIoPlayerInterface implements TC2PlayerInterface {
  private static final long serialVersionUID = 1704548344081679272L;
  private SocketIOClient client = null;

  public SocketIoPlayerInterface(SocketIOClient client) {
    this.client = client;
  }

  @Override
  public void startGame(StartGameMessage msg) throws IOException {
    client.sendEvent("start_game", msg);
  }
}
