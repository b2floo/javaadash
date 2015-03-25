package com.javaadash.tc2.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.javaadash.tc2.core.board.CardLocation;
import com.javaadash.tc2.core.card.Card;
import com.javaadash.tc2.core.context.GameContext;
import com.javaadash.tc2.core.context.GameContext.Winner;
import com.javaadash.tc2.core.interfaces.player.Player;

public class WinnerCheck {
  public void checkWinner(GameContext context) {
    log.info("Resolving the winner ...");
    boolean p1out = resolveOut(context.getFirstPlayer());
    log.info("Player {} out : [{}]", context.getFirstPlayer().getName(), p1out);
    boolean p2out = resolveOut(context.getSecondPlayer());
    log.info("Player {} out : [{}]", context.getSecondPlayer().getName(), p2out);
    if (p1out) {
      if (p2out) {
        // check scores
        if (context.getFirstPlayer().getScore() > context.getSecondPlayer().getScore()) {
          context.setWinner(Winner.FIRST_PLAYER);
          log.info("Winner is {}", context.getFirstPlayer());
        } else if (context.getSecondPlayer().getScore() > context.getFirstPlayer().getScore()) {
          context.setWinner(Winner.SECOND_PLAYER);
          log.info("Winner is {}", context.getSecondPlayer());
        } else {
          log.info("Game ends with a tie");
          context.setWinner(Winner.TIE);
        }
      } else {
        log.info("Winner is {}", context.getSecondPlayer());
        context.setWinner(Winner.SECOND_PLAYER);
      }
    } else if (p2out) {
      log.info("Winner is {}", context.getFirstPlayer());
      context.setWinner(Winner.FIRST_PLAYER);
    } else {
      context.setWinner(Winner.NOT_YET);
    }
  }

  boolean resolveOut(Player p) {
    if (p.getIngameDeck().getCards(CardType.ACTION, CardLocation.STOCK).size() == 0) {
      log.debug("Player {} has no more cards in stock", p);
      return true;
    }
    return resolveCharactersOut(p);
  }

  private boolean resolveCharactersOut(Player p) {
    boolean out = true;
    for (Card character : p.getIngameDeck().getCards(CardType.CHARACTER)) {
      if (character.getIntSetting("LIFE") <= 0) {
        log.debug("Character {} is out of game", character);
        p.getIngameDeck().setCardLocation(character, CardLocation.OUTOFGAME);
      } else
        out = false;
    }
    return out;
  }

  private Logger log = LoggerFactory.getLogger(WinnerCheck.class);
}
