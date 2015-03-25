package com.javaadash.tc2.core.interfaces.message;

public class EndGameMessage {
  private String winner;
  private Integer myScore;
  private Integer myOpponentScore;

  public String getWinner() {
    return winner;
  }

  public void setWinner(String winner) {
    this.winner = winner;
  }

  public Integer getMyScore() {
    return myScore;
  }

  public void setMyScore(Integer myScore) {
    this.myScore = myScore;
  }

  public Integer getMyOpponentScore() {
    return myOpponentScore;
  }

  public void setMyOpponentScore(Integer myOpponentScore) {
    this.myOpponentScore = myOpponentScore;
  }
}
