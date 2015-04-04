package com.javaadash.tc2.core.interfaces.message;

import java.util.List;

import com.javaadash.tc2.core.card.CardDescription;

public class UpdateSettingsMessage {
  private List<CardDescription> myCharacterSettings;
  private List<CardDescription> opponentCharacterSettings;
  private String message;
  private Integer myScore;
  private Integer opponentScore;

  public List<CardDescription> getMyCharacterSettings() {
    return myCharacterSettings;
  }

  public void setMyCharacterSettings(List<CardDescription> myCharacterSettings) {
    this.myCharacterSettings = myCharacterSettings;
  }

  public List<CardDescription> getOpponentCharacterSettings() {
    return opponentCharacterSettings;
  }

  public void setOpponentCharacterSettings(List<CardDescription> opponentCharacterSettings) {
    this.opponentCharacterSettings = opponentCharacterSettings;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public Integer getMyScore() {
    return myScore;
  }

  public void setMyScore(Integer myScore) {
    this.myScore = myScore;
  }

  public Integer getOpponentScore() {
    return opponentScore;
  }

  public void setOpponentScore(Integer opponentScore) {
    this.opponentScore = opponentScore;
  }
}
