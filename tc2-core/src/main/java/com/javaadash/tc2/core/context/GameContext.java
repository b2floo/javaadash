package com.javaadash.tc2.core.context;

import java.util.Collection;

import org.apache.commons.collections4.map.LinkedMap;

import com.javaadash.tc2.core.GameState;
import com.javaadash.tc2.core.interfaces.player.Player;
import com.javaadash.tc2.core.interfaces.player.PlayerData;

public class GameContext {

  // store everything related to players
  private LinkedMap<String, PlayerData> playersData = new LinkedMap<String, PlayerData>();

  private int turn = 0;
  private Player currentPlayer = null;
  private int state = 0;
  private Winner winner = null;

  public enum Winner {
    START_PLAYER, NEXT_PLAYER, TIE, NOT_YET;
  }

  public int getTurn() {
    return turn;
  }

  public void setTurn(int turn) {
    this.turn = turn;
  }

  public Collection<PlayerData> getPlayerDatas() {
    return playersData.values();
  }

  public PlayerData getPlayerData(String name) {
    return playersData.get(name);
  }

  public Player getFirstPlayer() {
    return playersData.getValue(0).getPlayer();
  }

  public void setFirstPlayer(Player firstPlayer) {
    PlayerData data = new PlayerData();
    data.setPlayer(firstPlayer);
    data.setPlayerState(GameState.BEGINNING);
    playersData.put(firstPlayer.getName(), data);
  }

  public Player getSecondPlayer() {
    return playersData.getValue(1).getPlayer();
  }

  public void setSecondPlayer(Player secondPlayer) {
    PlayerData data = new PlayerData();
    data.setPlayer(secondPlayer);
    data.setPlayerState(GameState.BEGINNING);
    playersData.put(secondPlayer.getName(), data);
  }

  public void setCurrentPlayer(Player p) {
    currentPlayer = p;
  }

  public Player getCurrentPlayer() {
    return currentPlayer;
  }

  public int getState() {
    return state;
  }

  public void setState(int state) {
    this.state = state;
  }

  public Winner getWinner() {
    return winner;
  }

  public void setWinner(Winner winner) {
    this.winner = winner;
  }
}
