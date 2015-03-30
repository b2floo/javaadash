package com.javaadash.tc2.core.board;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.javaadash.tc2.core.CardType;
import com.javaadash.tc2.core.card.Card;
import com.javaadash.tc2.core.context.GameContext;

/**
 * This class provides utility methods to clear the game board: ie put back cards in right location
 * after a turn.
 */
public class BoardClearer {
  private Logger log = LoggerFactory.getLogger(BoardClearer.class);

  /**
   * Clear the game board for both players available in context. This means putting all actions on
   * board in discard. The character is also set as discarded unless player's hand is empty. In that
   * case the character and all discarded characters are put back into player's hand.
   * 
   * @param context Game context
   */
  public void clearBoard(GameContext context) {
    log.info("Clearing {} board ...", context.getFirstPlayer());
    clearPlayerBoard(context.getFirstPlayer().getIngameDeck());
    log.info("Clearing {} board ...", context.getSecondPlayer());
    clearPlayerBoard(context.getSecondPlayer().getIngameDeck());
  }

  void clearPlayerBoard(InGameDeck inGameDeck) {
    List<Card> onBoardChars = inGameDeck.getCards(CardType.CHARACTER, CardLocation.BOARD);

    for (Card characterr : onBoardChars) {
      inGameDeck.setCardLocation(characterr, CardLocation.HAND);
      characterr.setAvailable(Boolean.FALSE);
    }

    // Check if no more character available, then reset all available
    List<Card> inHandChars = inGameDeck.getCards(CardType.CHARACTER, CardLocation.HAND);
    boolean available = false;
    for (Card characterr : inHandChars) {
      if (characterr.getAvailable()) {
        available = true;
        break;
      }
    }
    if (!available) {
      log.debug("No more characters available, putting all characters available");
      for (Card characterr : inHandChars) {
        characterr.setAvailable(Boolean.TRUE);
      }
    } else {
      log.debug("Characters available in hand");
    }

    // Clean actions
    List<Card> onBoardActions = inGameDeck.getCards(CardType.ACTION, CardLocation.BOARD);
    for (Card action : onBoardActions) {
      inGameDeck.setCardLocation(action, CardLocation.DISCARD);
    }
  }
}
