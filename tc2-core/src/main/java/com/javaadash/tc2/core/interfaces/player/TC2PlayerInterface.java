package com.javaadash.tc2.core.interfaces.player;

import java.io.IOException;
import java.io.Serializable;
import java.util.Collection;

import com.javaadash.tc2.core.card.Card;
import com.javaadash.tc2.core.exceptions.TC2CoreException;
import com.javaadash.tc2.core.interfaces.message.EndGameMessage;
import com.javaadash.tc2.core.interfaces.message.StartGameMessage;
import com.javaadash.tc2.core.interfaces.message.UpdateGameMessage;

public interface TC2PlayerInterface extends Serializable {
  /**
   * Setup the game to start
   */
  void startGame(StartGameMessage message) throws IOException, TC2CoreException;

  /**
   * Update cards location and settings
   */
  void updateGameStatus(UpdateGameMessage message) throws IOException, TC2CoreException;

  /**
   * Notify end of game
   */
  void endGame(EndGameMessage msg);

  /**
   * Select a character from a list
   * 
   * @param availableCharacters
   * @return the selected character
   */
  Card selectCharacter(Collection<Card> availableCharacters) throws IOException, TC2CoreException;

  /**
   * Select an action from a list
   * 
   * @param availableActions
   * @return the selected action
   */
  Card selectAction(Collection<Card> availableActions) throws IOException, TC2CoreException;



}
