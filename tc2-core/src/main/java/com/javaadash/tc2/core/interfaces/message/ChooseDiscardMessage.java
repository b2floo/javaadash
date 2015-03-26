package com.javaadash.tc2.core.interfaces.message;

import java.util.List;

import com.javaadash.tc2.core.card.CardDescription;

public class ChooseDiscardMessage {

  private List<CardDescription> discardCards;

  public List<CardDescription> getDiscardCards() {
    return discardCards;
  }

  public void setDiscardCards(List<CardDescription> discardCards) {
    this.discardCards = discardCards;
  }

  @Override
  public String toString() {
    return "ChooseDiscardMessage [discardCards=" + discardCards + "]";
  }

}
