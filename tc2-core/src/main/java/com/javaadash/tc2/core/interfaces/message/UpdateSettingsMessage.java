package com.javaadash.tc2.core.interfaces.message;

import java.util.List;

import com.javaadash.tc2.core.card.effect.CardEffectLog;

public class UpdateSettingsMessage {
  private List<CardEffectLog> cardEffectLogs;

  public List<CardEffectLog> getCardEffectLogs() {
    return cardEffectLogs;
  }

  public void setCardEffectLogs(List<CardEffectLog> cardEffectLogs) {
    this.cardEffectLogs = cardEffectLogs;
  }

  @Override
  public String toString() {
    return "UpdateSettingsMessage [cardEffectLogs=" + cardEffectLogs + "]";
  }
}
