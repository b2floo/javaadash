package com.javaadash.tc2.client;

import java.io.IOException;
import java.io.Serializable;

import com.javaadash.tc2.client.message.StartGameMessage;

public interface TC2PlayerInterface extends Serializable {
  /**
   * Send description of the game to start
   */
  void startGame(StartGameMessage message) throws IOException;
}
