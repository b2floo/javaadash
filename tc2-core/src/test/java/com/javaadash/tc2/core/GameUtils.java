package com.javaadash.tc2.core;

import java.util.HashMap;
import java.util.Map;

import com.javaadash.tc2.core.board.InGameDeck;
import com.javaadash.tc2.core.card.Card;
import com.javaadash.tc2.core.card.CardType;
import com.javaadash.tc2.core.card.Deck;
import com.javaadash.tc2.core.exceptions.TC2CoreException;
import com.javaadash.tc2.core.interfaces.player.Player;

public class GameUtils {

  public static Player getPlayer(String name, int maxNbCharacters, int maxNbActions,
      int initialHandSize, Card... cards) throws TC2CoreException {
    return new Player(name, initDeck(maxNbCharacters, maxNbActions, cards), initialHandSize, null);
  }

  public static InGameDeck getInGameDeck(int maxNbCharacters, int maxNbActions, int initialHandSize)
      throws TC2CoreException {
    return new InGameDeck(initDeck(maxNbCharacters, maxNbActions), initialHandSize);
  }

  public static Deck initDeck(int maxNbCharacters, int maxNbActions, Card... cards)
      throws TC2CoreException {
    Map<Integer, Integer> limits = new HashMap<Integer, Integer>();
    limits.put(CardType.CHARACTER, maxNbCharacters);
    limits.put(CardType.ACTION, maxNbActions);

    Deck deck = new Deck(limits);

    for (Card card : cards) {
      deck.addCard(card);
    }

    for (int i = deck.getCards(CardType.ACTION).size(); i < maxNbActions; i++) {
      deck.addCard(new Card(CardType.ACTION, "Action[" + i + "]"));
    }

    for (int i = deck.getCards(CardType.CHARACTER).size(); i < maxNbCharacters; i++) {
      deck.addCard(new Card(CardType.CHARACTER, "Character[" + i + "]"));
    }
    return deck;
  }
}
