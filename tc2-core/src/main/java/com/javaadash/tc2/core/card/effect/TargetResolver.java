package com.javaadash.tc2.core.card.effect;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.javaadash.tc2.core.board.CardLocation;
import com.javaadash.tc2.core.card.Card;
import com.javaadash.tc2.core.card.CardType;
import com.javaadash.tc2.core.context.GameContext;
import com.javaadash.tc2.core.interfaces.player.Player;

public class TargetResolver implements Serializable {
  private static final long serialVersionUID = -3677606210358398448L;

  public static List<Card> getCharactersFromTarget(Target target, GameContext context) {
    List<Card> chars = new ArrayList<Card>();
    Player p = context.getCurrentPlayer();
    switch (target) {
      case SELF:
        chars.addAll(p.getIngameDeck().getCards(CardType.CHARACTER, CardLocation.BOARD));
        break;
      case OPPONENT:
        chars.addAll(getOpponent(p, context).getIngameDeck().getCards(CardType.CHARACTER,
            CardLocation.BOARD));
        break;
      case ALL_PLAYER_CHARACTERS:
        chars.addAll(p.getIngameDeck().getCards(CardType.CHARACTER));
        break;
      case ALL_OPPONENTS:
        chars.addAll(getOpponent(p, context).getIngameDeck().getCards(CardType.CHARACTER));
        break;
      case ALL:
        chars.addAll(p.getIngameDeck().getCards(CardType.CHARACTER));
        chars.addAll(getOpponent(p, context).getIngameDeck().getCards(CardType.CHARACTER));
        break;
    }
    return chars;
  }

  public static Player getOpponent(Player p, GameContext context) {
    if (p == context.getFirstPlayer())
      return context.getSecondPlayer();
    else
      return context.getFirstPlayer();
  }
}
