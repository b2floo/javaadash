package com.javaadash.tc2.core.context;

import java.util.Collection;

import org.apache.commons.collections4.map.LinkedMap;

import com.javaadash.tc2.core.GameState;
import com.javaadash.tc2.core.player.Player;
import com.javaadash.tc2.core.player.PlayerData;

public class GameContext {
  // store everything related to players
  private LinkedMap<String, PlayerData> playersData = new LinkedMap<String, PlayerData>();
  private boolean alternate = false;

  private int turn = 1;
  private Player currentPlayer = null;
  private int state = 0;

  public GameContext() {

  }

  public GameContext(Player player1, Player player2) {
    PlayerData data = new PlayerData();
    data.setPlayer(player1);
    data.setPlayerState(GameState.BEGIN_TURN);
    playersData.put(player1.getName(), data);

    data = new PlayerData();
    data.setPlayer(player2);
    data.setPlayerState(GameState.BEGIN_TURN);
    playersData.put(player2.getName(), data);
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
    return alternate ? playersData.getValue(1).getPlayer() : playersData.getValue(0).getPlayer();
  }

  public Player getSecondPlayer() {
    return alternate ? playersData.getValue(0).getPlayer() : playersData.getValue(1).getPlayer();
  }

  public void alternatePlayers() {
    alternate = !alternate;
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
}
