package com.javaadash.tc2.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.javaadash.tc2.core.context.GameContext;

/**
 * This class is the entry point for a game to start
 * 
 * 
 * @author b2floo
 */
public class TC2AsynchronousGameManager 
{
	private Logger log = LoggerFactory.getLogger(TC2AsynchronousGameManager.class);
	
	public void handleGame(GameContext context)
	{
		log.info("Handling game, current state="+context.getState());
		// TODO add a state to shuffle players??
		switch(context.getState()) {
		case GameState.BEGINNING:
			new Dealer().dealCards(context);
			new PlayManager().startGame(context);
			context.setState(GameState.PLAYER_CHOOSE_CHARACTER);
		case GameState.PLAYER_CHOOSE_CHARACTER:
			
		default:
		}
	}
}
