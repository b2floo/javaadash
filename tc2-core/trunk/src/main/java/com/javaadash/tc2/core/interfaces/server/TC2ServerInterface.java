package com.javaadash.tc2.core.interfaces.server;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import com.javaadash.tc2.core.card.Deck;
import com.javaadash.tc2.core.exceptions.TC2CoreException;

public interface TC2ServerInterface extends Serializable {
	
	/**
	 * Initialize a new game
	 * @param playerName The player that create the game
	 * @return The game id
	 */
	Long createGame(String playerName, Deck deck) throws TC2CoreException, IOException;
	
	/**
	 * List games waiting for users to join
	 * @return A list of game ids
	 */
	List<Long> listPendingGames() throws IOException;
	
	/**
	 * Join an existing game
	 */
	void joinGame(Long gameId, String playerName, Deck deck) throws TC2CoreException, IOException;
}
