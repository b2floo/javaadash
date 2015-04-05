package com.javaadash.tc2.core.card.effect;

import java.util.ArrayList;
import java.util.List;

public class CardEffectLog {
  private String msg;
  private Integer cardId;
  private List<SettingChange> settingChanges = new ArrayList<SettingChange>();

  public CardEffectLog(Integer cardId, String msg) {
    this.cardId = cardId;
    this.msg = msg;
  }

  public Integer getCardId() {
    return cardId;
  }

  public List<SettingChange> getSettingChanges() {
    return settingChanges;
  }

  public void setSettingChanges(List<SettingChange> settingChanges) {
    this.settingChanges = settingChanges;
  }

  public String getMsg() {
    return msg;
  }

  @Override
  public String toString() {
    return "CardEffectLog [cardId=" + cardId + ", msg=" + msg + ", settingChanges="
        + settingChanges + "]";
  }
}
