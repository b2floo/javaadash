package com.javaadash.tc2.server.netty.main;

import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOServer;
import com.javaadash.tc2.server.message.JoinGameMessage;
import com.javaadash.tc2.server.netty.listener.JoinGameListener;

public class TC2NettyServer {

  public static void main(String[] args) throws InterruptedException {

    Configuration config = new Configuration();
    config.setHostname("localhost");
    config.setPort(9092);

    final SocketIOServer server = new SocketIOServer(config);

    server.addEventListener("join_any_game", JoinGameMessage.class, new JoinGameListener());

    server.start();
  }
}
