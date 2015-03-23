package com.javaadash.tc2.core.card;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

import com.javaadash.tc2.core.CardType;
import com.javaadash.tc2.core.exceptions.TC2CoreException;

public class DeckTest extends TestCase
{
    public void testAddCharacter() throws TC2CoreException {
    	Deck  deck = new Deck(Collections.singletonMap(CardType.CHARACTER, 1));
        
        Card charr = new Card();
        charr.setType(CardType.CHARACTER);
        deck.addCard(charr);
        assertEquals(1, deck.getCards(CardType.CHARACTER).size());
        assertSame(deck.getCards(CardType.CHARACTER).get(0), charr);
    }

    public void testAddCharacterWhenDeckFull() throws TC2CoreException {
        Deck deck = new Deck(Collections.singletonMap(CardType.CHARACTER, 1));
        Card charr = new Card();
        charr.setType(CardType.CHARACTER);
        
        deck.addCard(charr);
        try {
            deck.addCard(charr);
            fail("InvalidActionException expected");
        }
        catch(TC2CoreException e) {/* ok */}
    }

    public void testAddAction() throws TC2CoreException {
    	Deck  deck = new Deck(Collections.singletonMap(CardType.ACTION, 1));
    	Card charr = new Card();
        charr.setType(CardType.ACTION);
        deck.addCard(charr);
        assertEquals(1, deck.getCards(CardType.ACTION).size());
        assertSame(deck.getCards(CardType.ACTION).get(0), charr);
    }

    public void testAddActionWhenDeckFull() throws TC2CoreException {
    	Deck deck = new Deck(Collections.singletonMap(CardType.ACTION, 1));
        Card charr = new Card();
        charr.setType(CardType.ACTION);
        
        deck.addCard(charr);
        try {
            deck.addCard(charr);
            fail("InvalidActionException expected");
        }
        catch(TC2CoreException e) {/* ok */}
    }

    public void testIsFull() throws TC2CoreException {
    	Map<Integer, Integer> limits = new HashMap<Integer, Integer>();
        limits.put(CardType.ACTION, 1);
        limits.put(CardType.CHARACTER, 1);
        Deck deck = new Deck(limits);
        
        assertFalse(deck.isFull());
        Card charr = new Card();
        charr.setType(CardType.ACTION);
        deck.addCard(charr);
        assertFalse(deck.isFull());
        
        charr = new Card();
        charr.setType(CardType.CHARACTER);
        deck.addCard(charr);
        assertTrue(deck.isFull());
    }

}
