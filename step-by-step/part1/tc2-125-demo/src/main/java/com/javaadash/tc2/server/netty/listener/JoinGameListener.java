package com.javaadash.tc2.server.netty.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.listener.DataListener;
import com.javaadash.tc2.server.message.JoinGameMessage;

public class JoinGameListener implements DataListener<JoinGameMessage> {

  private Logger log = LoggerFactory.getLogger(JoinGameListener.class);

  @Override
  public void onData(final SocketIOClient client, JoinGameMessage msg, final AckRequest ackRequest) {

  }
}
