package com.javaadash.tc2.core;

import junit.framework.TestCase;

import com.javaadash.tc2.core.board.CardLocation;
import com.javaadash.tc2.core.board.InGameDeck;
import com.javaadash.tc2.core.context.GameContext;
import com.javaadash.tc2.core.interfaces.player.Player;

public class DealerTest extends TestCase {
  public void testDealPlayerCards() throws Exception {
    int maxActions = 10;
    int handsize = 2;
    InGameDeck inGameDeck = GameUtils.getInGameDeck(0, maxActions, handsize);

    // deal cards
    dealer.dealCards(inGameDeck);

    assertEquals(handsize, inGameDeck.getCards(CardType.ACTION, CardLocation.HAND).size());
    assertEquals(maxActions - handsize, inGameDeck.getCards(CardType.ACTION, CardLocation.STOCK)
        .size());

    // check deal again does not change the hand
    dealer.dealCards(inGameDeck);

    assertEquals(handsize, inGameDeck.getCards(CardType.ACTION, CardLocation.HAND).size());
    assertEquals(maxActions - handsize, inGameDeck.getCards(CardType.ACTION, CardLocation.STOCK)
        .size());
  }

  public void testDealNoMoreCards() throws Exception {
    int maxActions = 0;
    int handsize = 2;
    InGameDeck inGameDeck = GameUtils.getInGameDeck(0, maxActions, handsize);

    // deal cards
    dealer.dealCards(inGameDeck);

    assertEquals(0, inGameDeck.getCards(CardType.ACTION, CardLocation.HAND).size());
    assertEquals(0, inGameDeck.getCards(CardType.ACTION, CardLocation.STOCK).size());
  }

  public void testDealCards() throws Exception {
    int maxActions = 10;
    int handsize1 = 5;
    int handsize2 = 6;

    Player p1 = GameUtils.getPlayer("junit1", 0, maxActions, handsize1);
    Player p2 = GameUtils.getPlayer("junit2", 0, maxActions, handsize2);

    GameContext context = new GameContext(p1, p2);

    // deal cards
    dealer.dealCards(context);

    assertEquals(handsize1, p1.getIngameDeck().getCards(CardType.ACTION, CardLocation.HAND).size());
    assertEquals(maxActions - handsize1,
        p1.getIngameDeck().getCards(CardType.ACTION, CardLocation.STOCK).size());
    assertEquals(handsize2, p2.getIngameDeck().getCards(CardType.ACTION, CardLocation.HAND).size());
    assertEquals(maxActions - handsize2,
        p2.getIngameDeck().getCards(CardType.ACTION, CardLocation.STOCK).size());
  }

  private Dealer dealer = new Dealer();
}
