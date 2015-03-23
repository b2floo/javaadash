package com.javaadash.tc2.core.card.effect;

import java.util.List;

import junit.framework.TestCase;

import com.javaadash.tc2.core.CardType;
import com.javaadash.tc2.core.GameUtils;
import com.javaadash.tc2.core.board.CardLocation;
import com.javaadash.tc2.core.card.Card;
import com.javaadash.tc2.core.card.effect.Target;
import com.javaadash.tc2.core.card.effect.TargetResolver;
import com.javaadash.tc2.core.context.GameContext;
import com.javaadash.tc2.core.interfaces.player.Player;

public class TargetResolverTest extends TestCase
{
    public void testGetSelfTarget()	throws Exception
    {
    	GameContext context = new GameContext();

    	Player p1 = GameUtils.getPlayer("Junit1", 2, 0, 0);
    	Card char1 = p1.getIngameDeck().getCard(CardType.CHARACTER,CardLocation.HAND);
    	p1.getIngameDeck().setCardLocation(char1, CardLocation.BOARD);
    	Player p2 = GameUtils.getPlayer("Junit2", 2, 0, 0);
    	Card char2 = p2.getIngameDeck().getCard(CardType.CHARACTER,CardLocation.HAND);
    	p2.getIngameDeck().setCardLocation(char2, CardLocation.BOARD);
    	
    	context.setStartPlayer(p1);
    	context.setNextPlayer(p2);
    	
    	//current player= p1
    	context.setCurrentPlayer(p1);
    	List<Card> targets = targetResolver.getCharactersFromTarget(Target.SELF, context);
        assertEquals(1, targets.size());
        assertEquals(char1, targets.get(0));

        //current player= p2
    	context.setCurrentPlayer(p2);
    	targets = targetResolver.getCharactersFromTarget(Target.SELF, context);
        assertEquals(1, targets.size());
        assertEquals(char2, targets.get(0));
    }
    
    public void testGetOpponentTarget()	throws Exception
    {
    	GameContext context = new GameContext();

    	Player p1 = GameUtils.getPlayer("Junit1", 2, 0, 0);
    	Card char1 = p1.getIngameDeck().getCard(CardType.CHARACTER,CardLocation.HAND);
    	p1.getIngameDeck().setCardLocation(char1, CardLocation.BOARD);
    	Player p2 = GameUtils.getPlayer("Junit2", 2, 0, 0);
    	Card char2 = p2.getIngameDeck().getCard(CardType.CHARACTER,CardLocation.HAND);
    	p2.getIngameDeck().setCardLocation(char2, CardLocation.BOARD);
    	
    	context.setStartPlayer(p1);
    	context.setNextPlayer(p2);
    	
    	//current player= p1
    	context.setCurrentPlayer(p1);
    	List<Card> targets = targetResolver.getCharactersFromTarget(Target.OPPONENT, context);
        assertEquals(1, targets.size());
        assertEquals(char2, targets.get(0));

        //current player= p2
    	context.setCurrentPlayer(p2);
    	targets = targetResolver.getCharactersFromTarget(Target.OPPONENT, context);
        assertEquals(1, targets.size());
        assertEquals(char1, targets.get(0));
    }
    
    protected TargetResolver targetResolver = new TargetResolver();
}
