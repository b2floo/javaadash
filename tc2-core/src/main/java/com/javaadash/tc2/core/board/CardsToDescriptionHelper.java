package com.javaadash.tc2.core.board;

import java.util.ArrayList;
import java.util.List;

import com.javaadash.tc2.core.card.Card;
import com.javaadash.tc2.core.card.CardDescription;

public class CardsToDescriptionHelper {
	public static List<CardDescription> toCardsDescription(List<Card> cards) {
		List<CardDescription> cardDescriptions = new ArrayList<CardDescription>();
		for(Card card: cards) {
			cardDescriptions.add(new CardDescription(card.getId()));
		}
		return cardDescriptions;
	}
}
