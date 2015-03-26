package com.javaadash.tc2.core;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.javaadash.tc2.core.board.BoardClearer;
import com.javaadash.tc2.core.board.CardLocation;
import com.javaadash.tc2.core.board.CardsToDescriptionHelper;
import com.javaadash.tc2.core.card.Card;
import com.javaadash.tc2.core.card.CardDescription;
import com.javaadash.tc2.core.context.GameContext;
import com.javaadash.tc2.core.interfaces.player.PlayerData;

/**
 * This class is the entry point for a game to start
 * 
 * 
 * @author b2floo
 */
public class TC2AsynchronousGameManager {

  private Logger log = LoggerFactory.getLogger(TC2AsynchronousGameManager.class);
  private PlayManager playManager = new PlayManager();
  private Dealer dealer = new Dealer();

  public void handleGame(GameContext context) {

    switch (context.getState()) {
      // TODO dont do twice the deal send message
      case GameState.BEGIN_GAME:
        log.debug("Handling game, current state [BEGIN_GAME]");
        whoStarts(context);
        dealer.dealCards(context);
        playManager.startGame(context);
        context.setState(GameState.PLAYER_CHOOSE_CHARACTER);
        break;
      case GameState.BEGIN_TURN:
        log.debug("Handling game, current state [BEGIN_TURN]");
        context.alternatePlayers();
        dealer.dealCards(context);
        context.setState(GameState.PLAYER_CHOOSE_CHARACTER);
        for (PlayerData data : context.getPlayerDatas()) {
          data.setPlayerState(GameState.BEGIN_TURN);
        }
        playManager.updateGameStatus(context);
        break;
      case GameState.PLAYER_CHOOSE_CHARACTER:
        log.debug("Handling game, current state [PLAYER_CHOOSE_CHARACTER]");
        boolean choicesOver = true;
        for (PlayerData data : context.getPlayerDatas()) {
          if (data.getPlayerState() < GameState.PLAYER_CHOOSE_CHARACTER) {
            log.debug("All players have not yet made their choice");
            choicesOver = false;
          }
        }
        if (choicesOver) {
          // time to check cards and add to deck
          for (PlayerData data : context.getPlayerDatas()) {
            if (data.getPlayedCards().size() == 0)
              throw new IllegalStateException(
                  "No card selected where as the players state is PLAYER_CHOOSE_CHARACTER");
            CardDescription characterCard = data.getPlayedCards().poll();
            // retrieve the current players hand
            // TODO fix those crazy back and forward between descriptions and cards
            List<Card> inHandCharacters =
                data.getPlayer().getIngameDeck().getCards(CardType.CHARACTER, CardLocation.HAND);
            List<CardDescription> inHandCharactersDesc =
                CardsToDescriptionHelper.toCardsDescription(inHandCharacters);

            int index = inHandCharactersDesc.indexOf(characterCard);
            if (index >= 0) {
              log.debug("Character matched a character in hand of player "
                  + data.getPlayer().getName() + ", put it on board");
              Card inHandCharacter = inHandCharacters.get(index);
              data.getPlayer().getIngameDeck().setCardLocation(inHandCharacter, CardLocation.BOARD);
            } else {
              throw new IllegalStateException("Selected character [" + characterCard.getId()
                  + "] does not match any character in hand  of player "
                  + data.getPlayer().getName());
            }
            context.setState(GameState.PLAYER_CHOOSE_ACTION);
            // TODO send a message for authorized actions
          }
        }
        break;
      case GameState.PLAYER_CHOOSE_ACTION:
        log.debug("Handling game, current state [PLAYER_CHOOSE_ACTION]");
        choicesOver = true;
        for (PlayerData data : context.getPlayerDatas()) {
          if (data.getPlayerState() < GameState.PLAYER_CHOOSE_ACTION) {
            choicesOver = false;
            log.debug("All players have not yet made their choice");
          }
        }
        if (choicesOver) {
          // time to check cards and add to deck
          for (PlayerData data : context.getPlayerDatas()) {
            if (data.getPlayedCards().size() == 0)
              throw new IllegalStateException(
                  "No card selected where as the players state is PLAYER_CHOOSE_ACTION");
            List<CardDescription> actionCards = data.getPlayedCards();
            // retrieve the current players hand
            // TODO fix those crazy back and forward between descriptions and cards
            List<Card> inHandActions =
                data.getPlayer().getIngameDeck().getCards(CardType.ACTION, CardLocation.HAND);
            List<CardDescription> inHandActionsDesc =
                CardsToDescriptionHelper.toCardsDescription(inHandActions);

            for (CardDescription actionCard : actionCards) {
              int index = inHandActionsDesc.indexOf(actionCard);
              // TODO also check restrictions
              if (index >= 0) {
                log.debug("Action matched an action in hand of player "
                    + data.getPlayer().getName() + ", put it on board");
                Card inHandAction = inHandActions.get(index);
                data.getPlayer().getIngameDeck().setCardLocation(inHandAction, CardLocation.BOARD);
              } else {
                throw new IllegalStateException("Selected action [" + actionCard.getId()
                    + "] does not match any character in hand of player "
                    + data.getPlayer().getName());
              }
            }
            data.getPlayedCards().clear();
          }
          log.info("All cards on board, can start resolution");
          // after cards are decided, now we resolve the turn
          context.setState(GameState.TURN_RESOLUTION);
          new TurnResolver().resolveTurn(context);
          log.info("Turn resolved!!");

          // TODO check if game is over and send appropriate message
          context.setState(GameState.PLAYER_CHOOSE_DISCARD);
          playManager.updateGameStatus(context);
        }
        break;
      case GameState.PLAYER_CHOOSE_DISCARD:
        log.debug("Handling game, current state [PLAYER_CHOOSE_DISCARD]");
        choicesOver = true;
        for (PlayerData data : context.getPlayerDatas()) {
          if (data.getPlayerState() < GameState.PLAYER_CHOOSE_DISCARD) {
            choicesOver = false;
            log.debug("All players have not yet made their choice");
          }
        }
        if (choicesOver) {
          // time to check cards and add to deck
          for (PlayerData data : context.getPlayerDatas()) {
            List<CardDescription> discardCards = data.getPlayedCards();
            // retrieve the current players hand
            // TODO fix those crazy back and forward between descriptions and cards
            List<Card> inHandActions =
                data.getPlayer().getIngameDeck().getCards(CardType.ACTION, CardLocation.HAND);
            List<CardDescription> inHandActionsDesc =
                CardsToDescriptionHelper.toCardsDescription(inHandActions);

            for (CardDescription discardCard : discardCards) {
              int index = inHandActionsDesc.indexOf(discardCard);
              if (index >= 0) {
                log.debug("Discard matched an action in hand of player "
                    + data.getPlayer().getName() + ", put it in discard");
                Card inHandAction = inHandActions.get(index);
                data.getPlayer().getIngameDeck()
                    .setCardLocation(inHandAction, CardLocation.DISCARD);
              } else {
                throw new IllegalStateException("Selected action [" + discardCard.getId()
                    + "] for discard does not match any action in hand of player "
                    + data.getPlayer().getName());
              }
            }
            data.getPlayedCards().clear();
          }
          log.info("Discard part finished");
          // now check the winner
          log.info("Checking the winner");
          new WinnerCheck().checkWinner(context);

          log.info("Score {}:{} - {}:{}", new Object[] {context.getFirstPlayer(),
              context.getFirstPlayer().getScore(), context.getSecondPlayer(),
              context.getSecondPlayer().getScore()});

          log.debug("End turn {}", context.getTurn());
          switch (context.getWinner()) {
            case FIRST_PLAYER:
            case SECOND_PLAYER:
            case TIE:
              playManager.endGame(context);
              break;
            case NOT_YET:
              new BoardClearer().clearBoard(context);
              // start next Turn
              context.setTurn(context.getTurn() + 1);
              context.setState(GameState.BEGIN_TURN);
              handleGame(context);
              break;
          }
        }
        break;
      default:
        break;
    }
  }

  private void whoStarts(GameContext context) {
    if (Math.random() < 0.5) {
      context.alternatePlayers();
    }
    log.info("Player {} starts the game", context.getFirstPlayer());
  }
}
