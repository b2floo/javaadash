package main;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import com.javaadash.tc2.core.CardType;
import com.javaadash.tc2.core.card.Card;
import com.javaadash.tc2.core.card.Deck;
import com.javaadash.tc2.core.card.condition.Condition;
import com.javaadash.tc2.core.card.dictionnary.CardsGenerator;
import com.javaadash.tc2.core.card.effect.Effect;
import com.javaadash.tc2.core.exceptions.TC2CoreException;

public class StaticDeckGenerator {

  private static AtomicInteger idGenerator = new AtomicInteger();

  public static Deck getDeck(int nbActions) throws TC2CoreException {
    Deck deck = new Deck();
    CardsGenerator generator = new CardsGenerator();

    Map<Integer, Integer> limits = new HashMap<Integer, Integer>();
    limits.put(CardType.CHARACTER, 3);
    limits.put(CardType.ACTION, nbActions);

    deck = new Deck(limits);

    Collection<Effect> effects = generator.getEffets(1);
    deck.addCard(new Card("90001", CardType.CHARACTER, "ANASH", effects,
        new ArrayList<Condition>(), Card
            .createSettings("ATT:7 - DEF:2 - LIFE: 12 - CLASS: THIEF - GUILD: MERCENARY")));
    effects = generator.getEffets(1);
    deck.addCard(new Card("90002", CardType.CHARACTER, "NAYCUL", effects,
        new ArrayList<Condition>(), Card
            .createSettings("ATT:5 - DEF:2 - LIFE: 11 - CLASS: WARRIOR - GUILD: MERCENARY")));
    effects = generator.getEffets(1);
    deck.addCard(new Card("90003", CardType.CHARACTER, "TACOTSIRC", effects,
        new ArrayList<Condition>(), Card
            .createSettings("ATT:7 - DEF:2 - LIFE: 12 - CLASS: WARRIOR - GUILD: MERCENARY")));

    for (int i = 0; i < nbActions; i++) {
      effects = generator.getEffets(1);
      Collection<Condition> conditions = generator.getConditions();
      deck.addCard(new Card(Integer.toString(idGenerator.getAndIncrement()), CardType.ACTION,
          "ACTION" + i, effects, conditions, new HashMap<String, String>()));
    }
    return deck;
  }

}
