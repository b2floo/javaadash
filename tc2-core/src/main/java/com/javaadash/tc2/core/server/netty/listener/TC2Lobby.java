package com.javaadash.tc2.core.server.netty.listener;

import java.util.HashMap;
import java.util.Map;

import com.javaadash.tc2.core.context.GameContext;
import com.javaadash.tc2.core.interfaces.player.Player;

public class TC2Lobby {

  private Map<String, Player> pendingGameRequests = new HashMap<String, Player>();
  private Map<String, GameContext> currentGames = new HashMap<String, GameContext>();

  // TODO this is absolutely not thread safe!!!
  public Map.Entry<String, Player> getPendingGameRequest() {
    if (pendingGameRequests.size() > 0)
      return pendingGameRequests.entrySet().iterator().next();
    else
      return null;
  }

  public void addPendingGameRequest(String id, Player player) {
    pendingGameRequests.put(id, player);
  }

  public void removePendingGameRequest(String id) {
    pendingGameRequests.remove(id);
  }

  public Map<String, GameContext> getCurrentGames() {
    return currentGames;
  }

}
