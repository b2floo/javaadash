package com.javaadash.tc2.core.card.effect.log;

public class SettingChange {
  private Integer characterId;
  private String characterName;
  private String setting;
  private String newValue;
  private String diff;

  public SettingChange(Integer characterId, String characterName, String setting) {
    this(characterId, characterName, setting, null, null);
  }

  public SettingChange(Integer characterId, String characterName, String setting, String newValue,
      String diff) {
    this.characterId = characterId;
    this.characterName = characterName;
    this.setting = setting;
    this.newValue = newValue;
    this.diff = diff;
  }

  public Integer getCharacterId() {
    return characterId;
  }

  public String getSetting() {
    return setting;
  }

  public String getNewValue() {
    return newValue;
  }

  public String getDiff() {
    return diff;
  }

  public String getCharacterName() {
    return characterName;
  }

  public void setNewValue(String newValue) {
    this.newValue = newValue;
  }

  public void setDiff(String diff) {
    this.diff = diff;
  }

  @Override
  public String toString() {
    return "SettingChange [characterId=" + characterId + ", characterName=" + characterName
        + ", setting=" + setting + ", newValue=" + newValue + ", diff=" + diff + "]";
  }
}
