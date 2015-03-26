package com.javaadash.tc2.core.interfaces.message;

import java.util.List;

import com.javaadash.tc2.core.card.CardDescription;

public class ChooseActionMessage {

  private List<CardDescription> actionCards;

  public List<CardDescription> getActionCards() {
    return actionCards;
  }

  public void setActionCards(List<CardDescription> actionCards) {
    this.actionCards = actionCards;
  }

  @Override
  public String toString() {
    return "ChooseActionMessage [actionCards=" + actionCards + "]";
  }
}
