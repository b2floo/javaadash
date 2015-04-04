package com.javaadash.tc2.core.card.effect;

public class ResolutionDescription {

  private String message;
  private Integer characterId;
  private String setting;
  private String oldValue;
  private String newValue;
  private Integer diff;

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public Integer getCharacterId() {
    return characterId;
  }

  public void setCharacterId(Integer characterId) {
    this.characterId = characterId;
  }

  public String getSetting() {
    return setting;
  }

  public void setSetting(String setting) {
    this.setting = setting;
  }

  public String getOldValue() {
    return oldValue;
  }

  public void setOldValue(String oldValue) {
    this.oldValue = oldValue;
  }

  public String getNewValue() {
    return newValue;
  }

  public void setNewValue(String newValue) {
    this.newValue = newValue;
  }

  public Integer getDiff() {
    return diff;
  }

  public void setDiff(Integer diff) {
    this.diff = diff;
  }

}
