package main;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.javaadash.tc2.core.CardType;
import com.javaadash.tc2.core.TC2GameManager;
import com.javaadash.tc2.core.card.Card;
import com.javaadash.tc2.core.card.Deck;
import com.javaadash.tc2.core.card.condition.Condition;
import com.javaadash.tc2.core.card.dictionnary.CardsGenerator;
import com.javaadash.tc2.core.card.effect.Effect;
import com.javaadash.tc2.core.interfaces.player.Player;

public class Tc2Main {
  public static void main(String[] args) throws Exception {
    CardsGenerator generator = new CardsGenerator();
    int nbActions = 10;
    int nbCharacters = 3;
    Map<Integer, Integer> limits = new HashMap<Integer, Integer>();
    limits.put(CardType.ACTION, nbActions);
    limits.put(CardType.CHARACTER, nbCharacters);

    Deck deck1 = new Deck(limits);


    Collection<Effect> effects = generator.getEffets(1);
    deck1.addCard(new Card("-1", CardType.CHARACTER, "ABI", effects, new ArrayList<Condition>(),
        Card.createSettings("ATT:5 - DEF:3 - LIFE: 8 - CLASS: WIZARD - GUILD: ZIL")));
    effects = generator.getEffets(1);
    deck1.addCard(new Card("-1", CardType.CHARACTER, "ABO", effects, new ArrayList<Condition>(),
        Card.createSettings("ATT:4 - DEF:3 - LIFE: 6 - CLASS: WIZARD - GUILD: ZIL")));
    effects = generator.getEffets(1);
    deck1.addCard(new Card("-1", CardType.CHARACTER, "ABU", effects, new ArrayList<Condition>(),
        Card.createSettings("ATT:5 - DEF:1 - LIFE: 7 - CLASS: THIEF - GUILD: ZIL")));
    for (int i = 0; i < nbActions; i++) {
      effects = generator.getEffets(1);
      Collection<Condition> conditions = generator.getConditions();
      deck1.addCard(new Card("-1", CardType.ACTION, "ACTION" + i, effects, conditions,
          new HashMap<String, String>()));
    }

    Player p1 = new Player("Aladdin", deck1, 5, new InteractiveConsolePlayerInterface());

    Deck deck2 = new Deck(limits);

    effects = generator.getEffets(1);
    deck2.addCard(new Card("-1", CardType.CHARACTER, "YLI", effects, new ArrayList<Condition>(),
        Card.createSettings("ATT:4 - DEF:1 - LIFE: 9 - CLASS:WARRIOR - GUILD: NOZ")));
    effects = generator.getEffets(1);
    deck2.addCard(new Card("-1", CardType.CHARACTER, "YLO", effects, new ArrayList<Condition>(),
        Card.createSettings("ATT:4 - DEF:3 - LIFE: 5 - CLASS:PRIEST - GUILD:NOZ")));
    effects = generator.getEffets(1);
    deck2.addCard(new Card("-1", CardType.CHARACTER, "YLU", effects, new ArrayList<Condition>(),
        Card.createSettings("ATT:5 - DEF:2 - LIFE: 5 - CLASS: PRIEST - GUILD: NOZ")));
    for (int i = 0; i < nbActions; i++) {
      effects = generator.getEffets(1);
      Collection<Condition> conditions = generator.getConditions();
      deck2.addCard(new Card("-1", CardType.ACTION, "ACTION" + i, effects, conditions,
          new HashMap<String, String>()));
    }
    Player p2 = new Player("Yvette", deck2, 5, new AutomaticPlayerInterface());

    TC2GameManager gm = new TC2GameManager();
    gm.startGame(p1, p2);
  }
}
