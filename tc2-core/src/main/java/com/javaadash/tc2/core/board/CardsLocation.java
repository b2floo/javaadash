package com.javaadash.tc2.core.board;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.javaadash.tc2.core.card.Card;

public class CardsLocation implements Serializable
{
	private static final long serialVersionUID = -5697995029841025928L;
	
	private Map<Integer, List<Card>> cardsByLocation = new HashMap<Integer, List<Card>>();
    private Map<Card, Integer> locationByCard = new HashMap<Card, Integer>();
    
	public CardsLocation()
	{
	}
	
	public void addCard(Card card, Integer location)
	{
		List<Card> cards = cardsByLocation.get(location);
		if(cards == null)
		{
			cards = new ArrayList<Card>();
			cardsByLocation.put(location, cards);
		}
		cards.add(card);
		locationByCard.put(card, location);
	}

	public Card getCard(Integer location)
	{
		List<Card> cards = cardsByLocation.get(location);
		if(cards != null && cards.size() > 0)
			return cards.get(0);
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public List<Card> getCards(Integer location)
	{
		List<Card> cards = cardsByLocation.get(location);
		if(cards == null)
			cards = Collections.EMPTY_LIST;
		return cards;
	}
	
	public Collection<Card> getCards()
	{
		return locationByCard.keySet();
	}
	
	public Integer getLocation(Card card)
	{
		return locationByCard.get(card);
	}
	
	public void setLocation(Card card, Integer newLocation) {
		Integer currentLocation =  locationByCard.get(card);
		if(!newLocation.equals(currentLocation)) {
			cardsByLocation.get(currentLocation).remove(card);
			List<Card> cards = cardsByLocation.get(newLocation);
			if(cards == null) {
				cards = new ArrayList<Card>();
				cardsByLocation.put(newLocation, cards);
			}
			cards.add(card);
			locationByCard.put(card, newLocation);
		}
	}
}
