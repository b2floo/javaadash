package com.javaadash.tc2.core.interfaces.player;

import java.util.Collections;

import junit.framework.TestCase;

import com.javaadash.tc2.core.CardType;
import com.javaadash.tc2.core.card.Card;
import com.javaadash.tc2.core.card.Deck;
import com.javaadash.tc2.core.exceptions.TC2CoreException;

public class PlayerTest extends TestCase
{
	public void testInvalidDeck() throws Exception {
		Deck deck = new Deck(Collections.singletonMap(CardType.ACTION, 1));
		
		try {
			new Player("junit", deck, 5, null);
			fail("InvalidActionException expected");
		}
		catch (TC2CoreException e) { /* OK */ }
	}
	
	public void testValidDeck() throws Exception
	{
		Deck deck = new Deck(Collections.singletonMap(CardType.ACTION, 1));
		Card c = new Card(CardType.ACTION);
		deck.addCard(c);
		
		TC2PlayerInterface mockPlayerInterface = new MockTC2PlayerInterface();
		Player p = new Player("junit", deck, 5, mockPlayerInterface);
		assertEquals(p.getName(), "junit");
		assertEquals(p.getScore(), 0);
		assertEquals(p.getPlayerInterface(), mockPlayerInterface);
		
		assertEquals(1, p.getIngameDeck().getCards(CardType.ACTION).size());
		assertEquals(c, p.getIngameDeck().getCards(CardType.ACTION).iterator().next());
	}

}
