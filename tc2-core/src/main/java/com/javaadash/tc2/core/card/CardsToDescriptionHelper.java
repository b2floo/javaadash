package com.javaadash.tc2.core.card;

import java.util.ArrayList;
import java.util.List;


public class CardsToDescriptionHelper {
  public static List<CardDescription> toCardsDescription(List<Card> cards) {
    List<CardDescription> cardDescriptions = new ArrayList<CardDescription>();
    for (Card card : cards) {
      CardDescription desc = new CardDescription(card.getId(), card.getCardCode());
      desc.setSettings(card.getSettings());
      desc.setAvailable(card.getAvailable());
      cardDescriptions.add(desc);
    }
    return cardDescriptions;
  }
}
