package main;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.javaadash.tc2.core.CardType;
import com.javaadash.tc2.core.card.Card;
import com.javaadash.tc2.core.card.Deck;
import com.javaadash.tc2.core.card.condition.Condition;
import com.javaadash.tc2.core.card.dictionnary.CardsGenerator;
import com.javaadash.tc2.core.card.effect.Effect;
import com.javaadash.tc2.core.exceptions.TC2CoreException;

public class DeckGenerator {
  public static Deck deck = null;

  public static Deck getDeck(int nbActions) throws TC2CoreException {
    if (deck == null) {
      CardsGenerator generator = new CardsGenerator();

      Map<Integer, Integer> limits = new HashMap<Integer, Integer>();
      limits.put(CardType.CHARACTER, 2);
      limits.put(CardType.ACTION, nbActions);

      deck = new Deck(limits);

      Collection<Effect> effects = generator.getEffets(1);
      deck.addCard(new Card(-1, CardType.CHARACTER, "ABI", effects, new ArrayList<Condition>(),
          Card.createSettings("ATT:5 - DEF:3 - LIFE: 8 - CLASS: WIZARD - GUILD: ZIL")));
      effects = generator.getEffets(1);
      deck.addCard(new Card(-1, CardType.CHARACTER, "ABO", effects, new ArrayList<Condition>(),
          Card.createSettings("ATT:4 - DEF:3 - LIFE: 6 - CLASS: WIZARD - GUILD: ZIL")));
      // deck1.addCard(new Card(-1, CardType.CHARACTER, "ABU", effects, new ArrayList<Condition>(),
      // Card.createSettings("ATT:5 - DEF:1 - LIFE: 7 - CLASS: THIEF - GUILD: ZIL")));
      for (int i = 0; i < nbActions; i++) {
        effects = generator.getEffets(1);
        Collection<Condition> conditions = generator.getConditions();
        deck.addCard(new Card(-1, CardType.ACTION, "ACTION" + i, effects, conditions,
            new HashMap<String, String>()));
      }
    }
    return deck;
  }

}
