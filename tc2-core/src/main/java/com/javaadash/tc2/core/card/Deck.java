package com.javaadash.tc2.core.card;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.javaadash.tc2.core.board.InGameDeck;
import com.javaadash.tc2.core.exceptions.TC2CoreException;

/**
 * This class provides the implementation of a deck used by a player.
 * It is basically a set of cards divided into types.
 * @see InGameDeck
 */
public class Deck implements Serializable
{
	private static final long serialVersionUID = 1451869680805861287L;
	
	protected Map<Integer, Integer> typeLimits = new HashMap<Integer, Integer>();
    protected Map<Integer, List<Card>> cardsByType = new HashMap<Integer, List<Card>>();
    
    /**
     * Default constructor
     */
    public Deck() {
    }
    
    /**
     * Initialization constructor
     */
    public Deck(Map<Integer, Integer> typeLimits) {
    	this.typeLimits = typeLimits;
    }
    
    /**
     * Add a card to this deck
     * @throws TC2CoreException If the deck already contains the maximum number of card for the given type
     */
    public void addCard(Card card)
        throws TC2CoreException {
    	Integer type = card.getType();
        Integer limit = typeLimits.get(type);
        if(limit == null)
            throw new TC2CoreException("Undefined limit for card type : "+type);
        
        List<Card> cards = cardsByType.get(type);
        if(cards == null) {
        	cards = new ArrayList<Card>();
        	cardsByType.put(type, cards);
        }
        if(cards.size() >= limit)
        	throw new TC2CoreException("This Deck already contains the maximum number of card for type "+type);
        
        cards.add(card);
    }
    
    /**
     * Returns deck's cards from the given type
     * @return Cards of this deck from the given type
     */
	public List<Card> getCards(Integer type)  {
		List<Card> cards = new ArrayList<Card>();
		if(cardsByType.get(type) != null) {
			cards.addAll(cardsByType.get(type));
		}
        return cards;
    }
	
    /**
     * Returns deck's cards ordered by type
     * @return Cards of this deck ordered by type
     */
	public Map<Integer, List<Card>> getCardsByType()  {
    	return cardsByType;
    }

    /**
     * Returns whether this deck contains its maximum number of cards.
     * @return True if the deck is full, false otherwise.
     */
    public boolean isFull() {
        for(Integer type:typeLimits.keySet()) {
        	List<Card> cards = cardsByType.get(type);
        	Integer limit = typeLimits.get(type);
        	if((cards == null && limit != 0) || (cards != null && limit != cards.size()))
        		return false;
        }
        return true;
    }
}
