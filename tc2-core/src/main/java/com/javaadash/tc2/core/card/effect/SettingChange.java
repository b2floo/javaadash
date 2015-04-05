package com.javaadash.tc2.core.card.effect;

public class SettingChange {
  private Integer characterId;
  private String characterName;
  private String setting;
  private String newValue;
  private String diff;

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

  public String getNewValue() {
    return newValue;
  }

  public void setNewValue(String newValue) {
    this.newValue = newValue;
  }

  public String getDiff() {
    return diff;
  }

  public void setDiff(String diff) {
    this.diff = diff;
  }

  public String getCharacterName() {
    return characterName;
  }

  public void setCharacterName(String characterName) {
    this.characterName = characterName;
  }

  @Override
  public String toString() {
    return "SettingChange [characterId=" + characterId + ", characterName=" + characterName
        + ", setting=" + setting + ", newValue=" + newValue + ", diff=" + diff + "]";
  }
}
