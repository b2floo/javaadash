package com.javaadash.tc2.core;

import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.javaadash.tc2.core.board.CardLocation;
import com.javaadash.tc2.core.board.InGameDeck;
import com.javaadash.tc2.core.card.Card;
import com.javaadash.tc2.core.card.CardType;
import com.javaadash.tc2.core.context.GameContext;
import com.javaadash.tc2.core.interfaces.player.Player;

public class Dealer {
  public void dealCards(GameContext context) {
    Player firstPlayer = context.getFirstPlayer();
    log.debug("Dealing {} cards ...", firstPlayer);
    dealCards(context.getFirstPlayer().getIngameDeck());

    Player secondPlayer = context.getSecondPlayer();
    log.debug("Dealing {} cards ...", secondPlayer);
    dealCards(secondPlayer.getIngameDeck());
  }

  void dealCards(InGameDeck inGameDeck) {
    int handsize = inGameDeck.getHandsize();
    List<Card> stockActions = inGameDeck.getCards(CardType.ACTION, CardLocation.STOCK);
    Collections.shuffle(stockActions);

    while (inGameDeck.getCards(CardType.ACTION, CardLocation.HAND).size() < handsize
        && stockActions.size() > 0) {
      log.info("Adding card {} to hand", stockActions.get(0));
      inGameDeck.setCardLocation(stockActions.get(0), CardLocation.HAND);
      stockActions.remove(0);
    }
  }

  private Logger log = LoggerFactory.getLogger(Dealer.class);
}
