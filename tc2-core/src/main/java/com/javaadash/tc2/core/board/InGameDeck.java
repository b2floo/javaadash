package com.javaadash.tc2.core.board;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.javaadash.tc2.core.card.Card;
import com.javaadash.tc2.core.card.CardType;
import com.javaadash.tc2.core.card.Deck;

/**
 * This class wraps a Deck and keep a track of all cards location.
 */
public class InGameDeck implements Serializable
{
	private static final long serialVersionUID = 8077488890561611058L;
	
	private Map<Integer, CardsLocation> locationByType = new HashMap<Integer, CardsLocation>();
	private int handsize;
	
	/**
	 * Default constructor
	 */
	public InGameDeck() {
		
	}
	
	/**
	 * Initialize cards location for the deck given as parameter.
	 * @param deck The deck to wrap.
	 */
	public InGameDeck(Deck deck, int initialHandSize) {
		Map<Integer, List<Card>> cardsByType = deck.getCardsByType();
		for(Integer type:  cardsByType.keySet()) {
			CardsLocation cardsLocation = new CardsLocation();
			// shuffle cards for initialization
			Collections.shuffle(cardsByType.get(type));
			locationByType.put(type, cardsLocation);
			for(Card card: cardsByType.get(type))
				cardsLocation.addCard(card, getInitialLocation(card, initialHandSize));
			
		}
		this.handsize = initialHandSize;
	}
	
	public Card getCard(int type, int location) {
		CardsLocation cardsLocation = locationByType.get(type);
		if(cardsLocation != null)
			return cardsLocation.getCard(location);
		return null;
	}
	
	public List<Card> getCards(int type, int location) {
		CardsLocation cardsLocation = locationByType.get(type);
		List<Card> cards = new ArrayList<Card>();
		if(cardsLocation != null)
			cards.addAll(cardsLocation.getCards(location));
		return cards;
	}

	public List<Card> getCards(int type) {
		CardsLocation cardsLocation = locationByType.get(type);
		List<Card> cards = new ArrayList<Card>();
		if(cardsLocation != null)
			cards.addAll(cardsLocation.getCards());
		return cards;
    }
    
    public void setCardLocation(Card card, int newLocation) {
    	CardsLocation cardsLocation = locationByType.get(card.getType());
        cardsLocation.setLocation(card, newLocation);
    }
    
	public int getHandsize() {
		return handsize;
	}

	public void setHandsize(int handsize) {
		this.handsize = handsize;
	}

	private int getInitialLocation(Card card, int initialHandSize) {
		switch (card.getType())  {
			case CardType.ACTION: {
				int inHandNumber = getCards(CardType.ACTION, CardLocation.HAND).size();
				if( inHandNumber < initialHandSize)
					return CardLocation.HAND;
				else
					return CardLocation.STOCK;
			}
			case CardType.CHARACTER:
				return CardLocation.HAND;
		}
		return 0;
	}
}
