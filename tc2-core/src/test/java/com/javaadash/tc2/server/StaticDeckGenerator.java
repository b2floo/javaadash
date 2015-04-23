package com.javaadash.tc2.server;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.javaadash.tc2.core.card.Card;
import com.javaadash.tc2.core.card.CardType;
import com.javaadash.tc2.core.card.Deck;
import com.javaadash.tc2.core.card.dictionnary.CardDictionary;
import com.javaadash.tc2.core.card.effect.Effect;
import com.javaadash.tc2.core.card.effect.Target;
import com.javaadash.tc2.core.card.effect.character.setting.modification.CharacterSettingModificationEffect;
import com.javaadash.tc2.core.exceptions.TC2CoreException;

public class StaticDeckGenerator {

  public static Deck getDeck(int nbActions) throws TC2CoreException {
    Deck deck = new Deck();

    Map<Integer, Integer> limits = new HashMap<Integer, Integer>();
    limits.put(CardType.CHARACTER, 3);
    limits.put(CardType.ACTION, nbActions);

    deck = new Deck(limits);

    deck.addCard(CardDictionary.getCard("90004"));
    deck.addCard(CardDictionary.getCard("90005"));
    deck.addCard(CardDictionary.getCard("90006"));

    Effect effect00041 = new CharacterSettingModificationEffect("DEF", "-1", Target.OPPONENT);
    Effect effect00042 = new CharacterSettingModificationEffect("ATT", "-1", Target.OPPONENT);

    Effect effect00071 = new CharacterSettingModificationEffect("DEF", "1", Target.SELF);
    Effect effect00072 = new CharacterSettingModificationEffect("ATT", "1", Target.SELF);

    Effect effect000261 = new CharacterSettingModificationEffect("ATT", "2", Target.SELF);

    for (int i = 0; i < nbActions; i++) {
      if (i % 5 == 0) {
        Card card0004 =
            new Card("0004", CardType.ACTION, "BARRACUDA", Arrays.asList(new Effect[] {effect00041,
                effect00042}));
        deck.addCard(card0004);
      } else if (i % 5 == 1) {
        Card card0007 =
            new Card("0007", CardType.ACTION, "KUBI", Arrays.asList(new Effect[] {effect00071,
                effect00072}));
        deck.addCard(card0007);
      } else if (i % 5 == 2) {
        Card card00026 =
            new Card("00026", CardType.ACTION, "BRUDAL", Arrays.asList(new Effect[] {effect000261}));
        deck.addCard(card00026);
      } else if (i % 5 == 3) {
        deck.addCard(CardDictionary.getCard("00027"));
      } else if (i % 5 == 4) {
        deck.addCard(CardDictionary.getCard("00028"));
      }
    }

    return deck;
  }
}
