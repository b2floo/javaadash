package com.javaadash.tc2.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.javaadash.tc2.core.board.InGameDeck;
import com.javaadash.tc2.core.card.Card;
import com.javaadash.tc2.core.card.CardType;
import com.javaadash.tc2.core.card.Deck;
import com.javaadash.tc2.core.exceptions.TC2CoreException;
import com.javaadash.tc2.core.interfaces.player.Player;

public class GameUtils {
  public static Player getPlayer(String name, int maxNbCharacters, int maxNbActions,
      int initialHandSize) throws TC2CoreException {
    return new Player(name, initDeck(maxNbCharacters, maxNbActions), initialHandSize, null);
  }

  public static InGameDeck getInGameDeck(int maxNbCharacters, int maxNbActions, int initialHandSize)
      throws TC2CoreException {
    return new InGameDeck(initDeck(maxNbCharacters, maxNbActions), initialHandSize);
  }

  public static Deck initDeck(int maxNbCharacters, int maxNbActions) throws TC2CoreException {
    return initDeck(maxNbCharacters, maxNbActions, new ArrayList<Card>(), new ArrayList<Card>());
  }

  public static Deck initDeck(int maxNbCharacters, int maxNbActions, List<Card> characters,
      List<Card> actions) throws TC2CoreException {
    Map<Integer, Integer> limits = new HashMap<Integer, Integer>();
    limits.put(CardType.CHARACTER, maxNbCharacters);
    limits.put(CardType.ACTION, maxNbActions);

    Deck deck = new Deck(limits);

    for (int i = 0; i < maxNbActions; i++) {
      if (actions.size() > i) {
        deck.addCard(actions.get(i));
      } else {
        deck.addCard(new Card(CardType.ACTION, "Action[" + i + "]"));
      }
    }
    for (int i = 0; i < maxNbCharacters; i++) {
      if (characters.size() > i) {
        deck.addCard(characters.get(i));
      } else {
        deck.addCard(new Card(CardType.CHARACTER, "Character[" + i + "]"));
      }
    }
    return deck;
  }
}
