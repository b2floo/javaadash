package com.javaadash.tc2.core.board;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.javaadash.tc2.core.MockCardLocation;
import com.javaadash.tc2.core.board.InGameDeck;
import com.javaadash.tc2.core.card.Card;
import com.javaadash.tc2.core.card.CardType;
import com.javaadash.tc2.core.card.Deck;


import junit.framework.TestCase;

public class InGameDeckTest extends TestCase
{
    private Deck deck;
    private List<Card> actions;
    private List<Card> chars;
    private int maxActions = 5;
    private int maxCharacterrs = 10;
    
	@Override
	protected void setUp() throws Exception 
	{
		Map<Integer, Integer> limits = new HashMap<Integer, Integer>();
        limits.put(CardType.ACTION, maxActions);
        limits.put(CardType.CHARACTER, maxCharacterrs);
        
		deck = new Deck(limits);

    	chars = new ArrayList<Card>();
    	actions = new ArrayList<Card>();
    	for(int i=0; i<maxCharacterrs; i++) {
    		Card c = new Card();
    		c.setType(CardType.CHARACTER);
    		chars.add(c);
    		deck.addCard(c);

    	}
    	for(int i=0; i<maxActions; i++) {
    		Card a = new Card();
    		a.setType(CardType.ACTION);
    		actions.add(a);
    		deck.addCard(a);
    	}
	}
	
	// Check actions and characters are in the right location
    public void testCreateInGameDeck() throws Exception
    {
    	// Create a deck with no ACTION in HAND
    	int initialHandSize = 2;
    	InGameDeck inGameDeck = new InGameDeck(deck, initialHandSize);
    	
    	// check all characters are in HAND
    	assertEquals(maxCharacterrs, inGameDeck.getCards(CardType.CHARACTER).size());
    	
    	for(Integer cardLocation:MockCardLocation.values()) {
    		if(cardLocation == CardLocation.HAND) {
    			assertEquals(maxCharacterrs, inGameDeck.getCards(CardType.CHARACTER,cardLocation).size());
    			for(Card c: chars)
    				assertTrue(inGameDeck.getCards(CardType.CHARACTER,cardLocation).contains(c));
    		}
    		else
    			assertEquals(0, inGameDeck.getCards(CardType.CHARACTER,cardLocation).size());
    	}
    	
    	// check actions - We should have 2 in HAND  
    	// and all others in STOCK
    	assertEquals(maxActions, inGameDeck.getCards(CardType.ACTION).size());
    	for(Integer cardLocation:MockCardLocation.values()) {
    		if(cardLocation == CardLocation.STOCK) {
    			assertEquals(maxActions - initialHandSize, inGameDeck.getCards(CardType.ACTION,cardLocation).size());
    		} else if(cardLocation == CardLocation.HAND) {
    			assertEquals(initialHandSize, inGameDeck.getCards(CardType.ACTION,cardLocation).size());
    		}
    	}
    }
	
    public void testSetCharacterLocation() throws Exception {
    	Deck deck2 = new Deck(Collections.singletonMap(CardType.CHARACTER, 1));
    	Card c = new Card();
    	c.setType(CardType.CHARACTER);
    	deck2.addCard(c);
    	
    	InGameDeck inGameDeck = new InGameDeck(deck2, 5);
    	Integer cardLocation = CardLocation.HAND;
    	assertTrue(inGameDeck.getCards(CardType.CHARACTER,cardLocation).contains(c));
    	
    	// check setting the same location:
    	inGameDeck.setCardLocation(c, cardLocation);
    	// check no other location retain the card
    	for(Integer cardLocation_it:MockCardLocation.values())
    		if(cardLocation_it != cardLocation)
    			assertEquals(0, inGameDeck.getCards(CardType.CHARACTER,cardLocation_it).size());
    		else
    			assertTrue(inGameDeck.getCards(CardType.CHARACTER,cardLocation_it).contains(c));
    	
    	// then change location
    	for(Integer cardLocation_it:MockCardLocation.values()) {
    		inGameDeck.setCardLocation(c, cardLocation_it);
    		for(Integer cardLocation_it2:MockCardLocation.values())
        		if(cardLocation_it2 != cardLocation_it)
        			assertEquals(0, inGameDeck.getCards(CardType.CHARACTER,cardLocation_it2).size());
        		else
        			assertTrue(inGameDeck.getCards(CardType.CHARACTER,cardLocation_it2).contains(c));
    	}
    }
    
    public void testSetActionLocation() throws Exception
    {
    	Deck deck2 = new Deck(Collections.singletonMap(CardType.ACTION, 1));
    	Card a = new Card();
    	a.setType(CardType.ACTION);
    	deck2.addCard(a);
    	
    	// Create a deck with no ACTION in HAND
    	InGameDeck inGameDeck = new InGameDeck(deck2, 0);
    	Integer cardLocation = CardLocation.STOCK;
    	assertTrue(inGameDeck.getCards(CardType.ACTION,cardLocation).contains(a));
    	
    	// check setting the same location:
    	inGameDeck.setCardLocation(a, cardLocation);
    	// check no other location retain the card
    	for(Integer cardLocation_it:MockCardLocation.values())
    		if(cardLocation_it != cardLocation)
    			assertEquals(0, inGameDeck.getCards(CardType.ACTION,cardLocation_it).size());
    		else
    			assertTrue(inGameDeck.getCards(CardType.ACTION,cardLocation_it).contains(a));
    	
    	// then change location
    	for(Integer cardLocation_it:MockCardLocation.values()) {
    		inGameDeck.setCardLocation(a, cardLocation_it);
    		for(Integer cardLocation_it2:MockCardLocation.values())
        		if(cardLocation_it2 != cardLocation_it)
        			assertEquals(0, inGameDeck.getCards(CardType.ACTION,cardLocation_it2).size());
        		else
        			assertTrue(inGameDeck.getCards(CardType.ACTION,cardLocation_it2).contains(a));
    	}
    }
}
