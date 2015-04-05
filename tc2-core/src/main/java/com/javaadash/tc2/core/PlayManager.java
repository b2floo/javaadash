package com.javaadash.tc2.core;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.javaadash.tc2.core.board.CardLocation;
import com.javaadash.tc2.core.card.CardDescription;
import com.javaadash.tc2.core.card.CardType;
import com.javaadash.tc2.core.card.CardsToDescriptionHelper;
import com.javaadash.tc2.core.card.effect.CardEffectLog;
import com.javaadash.tc2.core.context.GameContext;
import com.javaadash.tc2.core.exceptions.TC2CoreException;
import com.javaadash.tc2.core.interfaces.message.EndGameMessage;
import com.javaadash.tc2.core.interfaces.message.StartGameMessage;
import com.javaadash.tc2.core.interfaces.message.UpdateGameMessage;
import com.javaadash.tc2.core.interfaces.message.UpdateSettingsMessage;
import com.javaadash.tc2.core.interfaces.player.Player;

/**
 * Interacts with the player when choices are needed
 * 
 * @author b2floo
 * 
 */
public class PlayManager {
  private static Logger log = LoggerFactory.getLogger(Player.class);

  public void startGame(GameContext context) {
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
      log.debug(player1msg + "  message sent to user " + context.getFirstPlayer().getName());

      StartGameMessage player2msg = new StartGameMessage(context.getFirstPlayer().getName());
      player2msg.setMyHand(player2Hand);
      player2msg.setMyCharacters(player2Characters);
      player2msg.setMyOpponentCharacters(player1Characters);
      context.getSecondPlayer().getPlayerInterface().startGame(player2msg);
      log.debug(player2msg + "  message sent to user " + context.getSecondPlayer().getName());
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (TC2CoreException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  public void updateGameStatus(GameContext context) {
    log.debug("Sending a update_game message");
    try {
      // TODO creation of description might be very time consuming, remove this
      List<CardDescription> player1Hand =
          CardsToDescriptionHelper.toCardsDescription(context.getFirstPlayer().getIngameDeck()
              .getCards(CardType.ACTION, CardLocation.HAND));
      List<CardDescription> player1Characters =
          CardsToDescriptionHelper.toCardsDescription(context.getFirstPlayer().getIngameDeck()
              .getCards(CardType.CHARACTER, CardLocation.HAND));
      List<CardDescription> player1Board =
          CardsToDescriptionHelper.toCardsDescription(context.getFirstPlayer().getIngameDeck()
              .getCards(CardType.ACTION, CardLocation.BOARD));
      List<CardDescription> player1CharacterBoard =
          CardsToDescriptionHelper.toCardsDescription(context.getFirstPlayer().getIngameDeck()
              .getCards(CardType.CHARACTER, CardLocation.BOARD));
      List<CardDescription> player1Discard =
          CardsToDescriptionHelper.toCardsDescription(context.getFirstPlayer().getIngameDeck()
              .getCards(CardType.ACTION, CardLocation.DISCARD));

      List<CardDescription> player2Hand =
          CardsToDescriptionHelper.toCardsDescription(context.getSecondPlayer().getIngameDeck()
              .getCards(CardType.ACTION, CardLocation.HAND));
      List<CardDescription> player2Characters =
          CardsToDescriptionHelper.toCardsDescription(context.getSecondPlayer().getIngameDeck()
              .getCards(CardType.CHARACTER, CardLocation.HAND));
      List<CardDescription> player2Board =
          CardsToDescriptionHelper.toCardsDescription(context.getSecondPlayer().getIngameDeck()
              .getCards(CardType.ACTION, CardLocation.BOARD));
      List<CardDescription> player2CharacterBoard =
          CardsToDescriptionHelper.toCardsDescription(context.getSecondPlayer().getIngameDeck()
              .getCards(CardType.CHARACTER, CardLocation.BOARD));
      List<CardDescription> player2Discard =
          CardsToDescriptionHelper.toCardsDescription(context.getSecondPlayer().getIngameDeck()
              .getCards(CardType.ACTION, CardLocation.DISCARD));

      Integer player1score = context.getFirstPlayer().getScore();
      Integer player2score = context.getSecondPlayer().getScore();

      UpdateGameMessage player1msg = new UpdateGameMessage();
      player1msg.setMyHand(player1Hand);
      player1msg.setMyCharacters(player1Characters);
      player1msg.setMyBoard(player1Board);
      player1msg.setMyCharacterBoard(player1CharacterBoard);
      player1msg.setMyDiscard(player1Discard);
      player1msg.setMyOpponentCharacters(player2Characters);
      player1msg.setMyOpponentBoard(player2Board);
      player1msg.setMyOpponentDiscard(player2Discard);
      player1msg.setMyOpponentCharacterBoard(player2CharacterBoard);
      player1msg.setGameState(context.getState());
      player1msg.setTurn(context.getTurn());
      player1msg.setMyScore(player1score);
      player1msg.setOpponentScore(player2score);
      context.getFirstPlayer().getPlayerInterface().updateGameStatus(player1msg);
      log.debug(player1msg + " sent to user " + context.getFirstPlayer().getName());

      UpdateGameMessage player2msg = new UpdateGameMessage();
      player2msg.setMyHand(player2Hand);
      player2msg.setMyCharacters(player2Characters);
      player2msg.setMyBoard(player2Board);
      player2msg.setMyCharacterBoard(player2CharacterBoard);
      player2msg.setMyDiscard(player2Discard);
      player2msg.setMyOpponentCharacters(player1Characters);
      player2msg.setMyOpponentBoard(player1Board);
      player2msg.setMyOpponentCharacterBoard(player1CharacterBoard);
      player2msg.setMyOpponentDiscard(player1Discard);
      player2msg.setGameState(context.getState());
      player2msg.setTurn(context.getTurn());
      player2msg.setMyScore(player2score);
      player2msg.setOpponentScore(player1score);
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

  public void updateSettings(GameContext context, List<CardEffectLog> cardEffectLogs) {
    log.debug("Sending a update_settings message");
    UpdateSettingsMessage msg = new UpdateSettingsMessage();
    msg.setCardEffectLogs(cardEffectLogs);

    context.getFirstPlayer().getPlayerInterface().updateSettings(msg);
    log.debug(msg + " sent to user " + context.getFirstPlayer().getName());

    context.getSecondPlayer().getPlayerInterface().updateSettings(msg);
    log.debug(msg + " sent to user " + context.getSecondPlayer().getName());
  }

  public void endGame(GameContext context) {
    log.debug("Sending an end_game message");
    String winner = "_TIE_";
    switch (context.getWinner()) {
      case FIRST_PLAYER:
        winner = context.getFirstPlayer().getName();
        break;
      case SECOND_PLAYER:
        winner = context.getSecondPlayer().getName();
        break;
      case TIE:
        break;
      default:
        throw new IllegalStateException("No winner defined when trying to send end_game message");
    }

    Integer player1Score = context.getFirstPlayer().getScore();
    Integer player2Score = context.getSecondPlayer().getScore();

    EndGameMessage player1msg = new EndGameMessage();
    player1msg.setWinner(winner);
    player1msg.setMyScore(player1Score);
    player1msg.setMyOpponentScore(player2Score);
    context.getFirstPlayer().getPlayerInterface().endGame(player1msg);
    log.debug(player1msg + " message sent to user " + context.getFirstPlayer().getName());

    EndGameMessage player2msg = new EndGameMessage();
    player2msg.setWinner(winner);
    player2msg.setMyScore(player2Score);
    player2msg.setMyOpponentScore(player1Score);
    context.getSecondPlayer().getPlayerInterface().endGame(player2msg);
    log.debug(player2msg + " message sent to user " + context.getSecondPlayer().getName());
  }

}
