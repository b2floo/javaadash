package com.javaadash.tc2.server.netty.main;

import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOServer;
import com.javaadash.tc2.core.game.server.TC2Server;
import com.javaadash.tc2.server.message.JoinGameMessage;
import com.javaadash.tc2.server.netty.listener.JoinGameListener;

public class TC2NettyMain {

  public static void main(String[] args) throws InterruptedException {

    Configuration config = new Configuration();
    config.setHostname("localhost");
    config.setPort(9092);

    final SocketIOServer server = new SocketIOServer(config);

    TC2Server tc2Server = new TC2Server();
    server
        .addEventListener("join_any_game", JoinGameMessage.class, new JoinGameListener(tc2Server));

    server.start();
  }
}
