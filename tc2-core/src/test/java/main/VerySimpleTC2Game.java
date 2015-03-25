package main;

import java.util.HashMap;
import java.util.Map;

import com.javaadash.tc2.core.CardType;
import com.javaadash.tc2.core.TC2GameManager;
import com.javaadash.tc2.core.TurnResolver;
import com.javaadash.tc2.core.card.Card;
import com.javaadash.tc2.core.card.Deck;
import com.javaadash.tc2.core.context.GameContext;
import com.javaadash.tc2.core.interfaces.player.Player;

/**
 * This is an example of a simple TC2 game. Players both have 2 characters and 4 cards in hand. They
 * choose their characters, then the action. Turn resolver is a mock one so no settings are modified
 * at character level. The game end when a player dont have enough cards to fill their hand (append
 * at same time for both players so game end up with a tie)
 * 
 * @author b2floo
 *
 */
public class VerySimpleTC2Game {

  public static void main(String[] args) throws Exception {
    int nbActions = 2;
    int nbCharacters = 2;
    Map<Integer, Integer> limits = new HashMap<Integer, Integer>();
    limits.put(CardType.ACTION, nbActions);
    limits.put(CardType.CHARACTER, nbCharacters);

    Deck deck1 = new Deck(limits);

    deck1.addCard(new Card(CardType.CHARACTER, "ABI"));
    deck1.addCard(new Card(CardType.CHARACTER, "ABO"));
    for (int i = 0; i < nbActions; i++) {
      deck1.addCard(new Card(CardType.ACTION, "ACTION" + i));
    }
    Player p1 = new Player("Aladdin", deck1, 5, new AutomaticPlayerInterface());

    Deck deck2 = new Deck(limits);
    deck2.addCard(new Card(CardType.CHARACTER, "YLI"));
    deck2.addCard(new Card(CardType.CHARACTER, "YLO"));
    for (int i = 0; i < nbActions; i++) {
      deck2.addCard(new Card(CardType.ACTION, "ACTION" + i));
    }
    Player p2 = new Player("Yvette", deck2, 5, new AutomaticPlayerInterface());

    TC2GameManager gm = new TC2GameManager();
    gm.getStateResolver().setTurnResolver(new TurnResolver());
    gm.startGame(p1, p2);

  }

  class MockTurnResolver extends TurnResolver {
    public void resolveTurn(GameContext context) {
      // there we dont decrement life or anything
    }
  }
}
