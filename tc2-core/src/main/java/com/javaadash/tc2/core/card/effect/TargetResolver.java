package com.javaadash.tc2.core.card.effect;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.javaadash.tc2.core.CardType;
import com.javaadash.tc2.core.board.CardLocation;
import com.javaadash.tc2.core.card.Card;
import com.javaadash.tc2.core.context.GameContext;
import com.javaadash.tc2.core.interfaces.player.Player;

public class TargetResolver implements Serializable
{
	private static final long serialVersionUID = -3677606210358398448L;

	public List<Card> getCharactersFromTarget(Target target, GameContext context) {
        List<Card> chars = new ArrayList<Card>();
        Player p = context.getCurrentPlayer();
        switch(target) {
        case SELF: 
            chars.addAll(p.getIngameDeck().getCards(CardType.CHARACTER, CardLocation.BOARD));
            break;
        case OPPONENT:
            if(p == context.getFirstPlayer())
                p = context.getSecondPlayer();
            else
            	p = context.getFirstPlayer();
            chars.addAll(p.getIngameDeck().getCards(CardType.CHARACTER, CardLocation.BOARD));
            break;
        default:
            break;
        }                
        return chars;
    }
}
