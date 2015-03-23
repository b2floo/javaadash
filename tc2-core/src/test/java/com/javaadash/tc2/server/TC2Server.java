package com.javaadash.tc2.server;

import java.util.HashSet;
import java.util.Set;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.DataListener;

public class TC2Server {

  public static void main(String[] args) throws InterruptedException {

    Configuration config = new Configuration();
    config.setHostname("localhost");
    config.setPort(9092);

    final SocketIOServer server = new SocketIOServer(config);

    server.addEventListener("list_games", Object.class, new DataListener<Object>() {

      @Override
      public void onData(final SocketIOClient client, Object data, final AckRequest ackRequest) {

        System.out.println("Received data!!!");
        // check is ack requested by client,
        // but it's not required check
        if (ackRequest.isAckRequested()) {
          // send ack response with data to client
          ackRequest.sendAckData("client message was delivered to server!", "yeah!");
        }

        // send object to socket.io client
        Set<String> games = new HashSet<String>();
        games.add("GAME1");
        games.add("GAME2");
        client.sendEvent("list_games_result", games);
      }
    });

    server.start();

    Thread.sleep(Integer.MAX_VALUE);

    server.stop();
  }
}
