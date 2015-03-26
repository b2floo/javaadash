package com.javaadash.tc2.core;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.javaadash.tc2.core.board.CardLocation;
import com.javaadash.tc2.core.board.CardsToDescriptionHelper;
import com.javaadash.tc2.core.card.Card;
import com.javaadash.tc2.core.card.CardDescription;
import com.javaadash.tc2.core.card.condition.Condition;
import com.javaadash.tc2.core.context.GameContext;
import com.javaadash.tc2.core.exceptions.TC2CoreException;
import com.javaadash.tc2.core.interfaces.message.EndGameMessage;
import com.javaadash.tc2.core.interfaces.message.StartGameMessage;
import com.javaadash.tc2.core.interfaces.message.UpdateGameMessage;
import com.javaadash.tc2.core.interfaces.player.Player;

/**
 * Interacts with the player when choices are needed
 * 
 * @author b2floo
 * 
 */
public class PlayManager {
  private Logger log = LoggerFactory.getLogger(Player.class);

  public void play(GameContext context) {
    switch (context.getState()) {
      case GameState.BEGIN_TURN:
        startGame(context);
      case GameState.PLAYER_CHOOSE_CHARACTER:
        chooseCharacter(context);
        break;

      case GameState.PLAYER_CHOOSE_ACTION:
        chooseAction(context);
        break;

      case GameState.PLAYER_CHOOSE_DISCARD:
        chooseDiscard(context);
        break;

      default:
        throw new IllegalStateException("Illegal game state : " + context.getState());
    }
  }

  protected void startGame(GameContext context) {
    log.debug("Sending a start_game message");
    try {
      // create list to be sent
      List<CardDescription> player1Hand =
          CardsToDescriptionHelper.toCardsDescription(context.getFirstPlayer().getIngameDeck()
              .getCards(CardType.ACTION, CardLocation.HAND));
      List<CardDescription> player1Characters =
          CardsToDescriptionHelper.toCardsDescription(context.getFirstPlayer().getIngameDeck()
              .getCards(CardType.CHARACTER, CardLocation.HAND));
      List<CardDescription> player2Hand =
          CardsToDescriptionHelper.toCardsDescription(context.getSecondPlayer().getIngameDeck()
              .getCards(CardType.ACTION, CardLocation.HAND));
      List<CardDescription> player2Characters =
          CardsToDescriptionHelper.toCardsDescription(context.getSecondPlayer().getIngameDeck()
              .getCards(CardType.CHARACTER, CardLocation.HAND));

      StartGameMessage player1msg = new StartGameMessage(context.getSecondPlayer().getName());
      player1msg.setMyHand(player1Hand);
      player1msg.setMyCharacters(player1Characters);
      player1msg.setMyOpponentCharacters(player2Characters);
      context.getFirstPlayer().getPlayerInterface().startGame(player1msg);
      log.debug("start_game message sent to user " + context.getFirstPlayer().getName());

      StartGameMessage player2msg = new StartGameMessage(context.getFirstPlayer().getName());
      player2msg.setMyHand(player2Hand);
      player2msg.setMyCharacters(player2Characters);
      player2msg.setMyOpponentCharacters(player1Characters);
      context.getSecondPlayer().getPlayerInterface().startGame(player2msg);
      log.debug("start_game message sent to user " + context.getSecondPlayer().getName());
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (TC2CoreException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  protected void updateGameStatus(GameContext context) {
    log.debug("Sending a update_game message");
    try {
      // create list to be sent
      List<CardDescription> player1Hand =
          CardsToDescriptionHelper.toCardsDescription(context.getFirstPlayer().getIngameDeck()
              .getCards(CardType.ACTION, CardLocation.HAND));
      List<CardDescription> player1Characters =
          CardsToDescriptionHelper.toCardsDescription(context.getFirstPlayer().getIngameDeck()
              .getCards(CardType.CHARACTER, CardLocation.HAND));

      List<CardDescription> player2Hand =
          CardsToDescriptionHelper.toCardsDescription(context.getSecondPlayer().getIngameDeck()
              .getCards(CardType.ACTION, CardLocation.HAND));
      List<CardDescription> player2Characters =
          CardsToDescriptionHelper.toCardsDescription(context.getSecondPlayer().getIngameDeck()
              .getCards(CardType.CHARACTER, CardLocation.HAND));

      UpdateGameMessage player1msg = new UpdateGameMessage();
      player1msg.setMyHand(player1Hand);
      player1msg.setMyCharacters(player1Characters);
      player1msg.setMyOpponentCharacters(player2Characters);
      player1msg.setGameState(context.getState());
      context.getFirstPlayer().getPlayerInterface().updateGameStatus(player1msg);
      log.debug(player1msg + " sent to user " + context.getFirstPlayer().getName());

      UpdateGameMessage player2msg = new UpdateGameMessage();
      player2msg.setMyHand(player2Hand);
      player2msg.setMyCharacters(player2Characters);
      player2msg.setMyOpponentCharacters(player1Characters);
      player2msg.setGameState(context.getState());
      context.getSecondPlayer().getPlayerInterface().updateGameStatus(player2msg);
      log.debug(player2msg + " sent to user " + context.getSecondPlayer().getName());
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (TC2CoreException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  protected void endGame(GameContext context) {
    log.debug("Sending an end_game message");
    String winner = "_TIE_";
    switch (context.getWinner()) {
      case FIRST_PLAYER:
        winner = context.getFirstPlayer().getName();
        break;
      case SECOND_PLAYER:
        winner = context.getSecondPlayer().getName();
        break;
    }

    Integer player1Score = context.getFirstPlayer().getScore();
    Integer player2Score = context.getSecondPlayer().getScore();

    EndGameMessage player1msg = new EndGameMessage();
    player1msg.setWinner(winner);
    player1msg.setMyScore(player1Score);
    player1msg.setMyOpponentScore(player2Score);
    context.getFirstPlayer().getPlayerInterface().endGame(player1msg);
    log.debug("end_game message sent to user " + context.getFirstPlayer().getName());

    EndGameMessage player2msg = new EndGameMessage();
    player2msg.setWinner(winner);
    player2msg.setMyScore(player2Score);
    player2msg.setMyOpponentScore(player1Score);
    context.getSecondPlayer().getPlayerInterface().endGame(player2msg);
    log.debug("end_game message sent to user " + context.getSecondPlayer().getName());
  }

  protected void chooseCharacter(GameContext context) {
    Player currentPlayer = context.getCurrentPlayer();
    log.info("{} chooses a character", currentPlayer.getName());

    // select a character
    List<Card> availableCharacters =
        currentPlayer.getIngameDeck().getCards(CardType.CHARACTER, CardLocation.HAND);
    log.info("Available characters : {}", availableCharacters);
    Card selectedCharacter = null;
    if (currentPlayer.getPlayerInterface() != null && availableCharacters.size() > 0)
      try {
        selectedCharacter = currentPlayer.getPlayerInterface().selectCharacter(availableCharacters);
      } catch (Exception e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    log.info("Selected character : {}", selectedCharacter);

    if (selectedCharacter != null)
      currentPlayer.getIngameDeck().setCardLocation(selectedCharacter, CardLocation.BOARD);
  }

  protected void chooseAction(GameContext context) {
    Player currentPlayer = context.getCurrentPlayer();
    log.info("{} chooses an action", currentPlayer.getName());

    // select an action
    Collection<Card> availableActions = getAvailableActions(context);
    log.info("Available actions : {}", availableActions);
    Card selectedAction = null;
    if (currentPlayer.getPlayerInterface() != null && availableActions.size() > 0)
      try {
        selectedAction = currentPlayer.getPlayerInterface().selectAction(availableActions);
      } catch (Exception e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    log.info("Selected action : {}", selectedAction);

    if (selectedAction != null)
      currentPlayer.getIngameDeck().setCardLocation(selectedAction, CardLocation.BOARD);
  }

  protected void chooseDiscard(GameContext context) {
    Player currentPlayer = context.getCurrentPlayer();
    log.debug("{} chooses discard", currentPlayer.getName());
  }

  Collection<Card> getAvailableActions(GameContext context) {
    List<Card> availableActions = new ArrayList<Card>();
    for (Card action : context.getCurrentPlayer().getIngameDeck()
        .getCards(CardType.ACTION, CardLocation.HAND)) {
      log.debug("Check restriction on action {} ", action);
      boolean available = true;
      for (Condition condition : action.getConditions()) {
        log.debug("Condition : {}", condition);
        if (!condition.isFulfilled(context))
          available = false;
      }
      if (available)
        availableActions.add(action);
    }
    return availableActions;
  }

}
