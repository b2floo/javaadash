package com.javaadash.tc2.core;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

  public void handleGame(GameContext context) {
    log.info("Handling game, current state [" + context.getState() + "]");
    // TODO add a state to shuffle players??
    switch (context.getState()) {
      case GameState.BEGINNING:
        new Dealer().dealCards(context);
        new PlayManager().startGame(context);
        context.setState(GameState.PLAYER_CHOOSE_CHARACTER);
        break;

      case GameState.PLAYER_CHOOSE_CHARACTER:
        boolean choicesOver = true;
        for (PlayerData data : context.getPlayerDatas()) {
          if (data.getPlayerState() < GameState.PLAYER_CHOOSE_CHARACTER)
            choicesOver = false;
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
              log.debug("Character matched a character in hand, put it on board");
              Card inHandCharacter = inHandCharacters.get(index);
              data.getPlayer().getIngameDeck().setCardLocation(inHandCharacter, CardLocation.BOARD);
            } else {
              throw new IllegalStateException("Selected character [" + characterCard.getId()
                  + "] does not match any character in hand");
            }
            context.setState(GameState.PLAYER_CHOOSE_ACTION);
            // TODO send a message for authorized actions
          }
        }
        break;
      case GameState.PLAYER_CHOOSE_ACTION:
        choicesOver = true;
        for (PlayerData data : context.getPlayerDatas()) {
          if (data.getPlayerState() < GameState.PLAYER_CHOOSE_ACTION)
            choicesOver = false;
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
                log.debug("Action matched an action in hand, put it on board");
                Card inHandAction = inHandActions.get(index);
                data.getPlayer().getIngameDeck().setCardLocation(inHandAction, CardLocation.BOARD);
              } else {
                throw new IllegalStateException("Selected action [" + actionCard.getId()
                    + "] does not match any character in hand");
              }
            }
          }
          log.info("All cards on board, can start resolution");
          // after cards are decided, now we resolve the turn
          context.setState(GameState.TURN_RESOLUTION);
          new TurnResolver().resolveTurn(context);
          log.info("Turn resolved!!");
        }
        break;
      default:
        break;
    }
  }
}
