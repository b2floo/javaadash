package com.javaadash.tc2.server.netty.listener;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.listener.DataListener;
import com.javaadash.tc2.client.netty.SocketIoPlayerInterface;
import com.javaadash.tc2.server.TC2ServerInterface;
import com.javaadash.tc2.server.message.JoinGameMessage;

public class JoinGameListener implements DataListener<JoinGameMessage> {
  private TC2ServerInterface tc2Server;

  public JoinGameListener(TC2ServerInterface tc2Server) {
    this.tc2Server = tc2Server;
  }

  @Override
  public void onData(final SocketIOClient client, JoinGameMessage msg, final AckRequest ackRequest) {
    tc2Server.joinAnyGame(msg, new SocketIoPlayerInterface(client));
  }
}
