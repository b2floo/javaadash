package com.javaadash.tc2.core.interfaces.message;

import com.javaadash.tc2.core.card.CardDescription;

public class ChooseCharacterMessage {

  private CardDescription characterCard;

  public CardDescription getCharacterCard() {
    return characterCard;
  }

  public void setCharacterCard(CardDescription characterCard) {
    this.characterCard = characterCard;
  }
}
