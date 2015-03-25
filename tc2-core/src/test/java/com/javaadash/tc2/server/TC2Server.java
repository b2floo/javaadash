package com.javaadash.tc2.server;

import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOServer;
import com.javaadash.tc2.core.interfaces.message.ChooseCharacterMessage;
import com.javaadash.tc2.core.interfaces.message.JoinGameMessage;
import com.javaadash.tc2.server.listener.ChooseCharacterListener;
import com.javaadash.tc2.server.listener.JoinGameListener;

public class TC2Server {


  static TC2Lobby lobby = new TC2Lobby();

  public static void main(String[] args) throws InterruptedException {

    Configuration config = new Configuration();
    config.setHostname("localhost");
    config.setPort(9092);
    // config.setOrigin("http://localhost");

    final SocketIOServer server = new SocketIOServer(config);

    server.addEventListener("join_any_game", JoinGameMessage.class, new JoinGameListener(lobby));

    server.addEventListener("choose_character", ChooseCharacterMessage.class,
        new ChooseCharacterListener(lobby));

    server.start();
  }
}
