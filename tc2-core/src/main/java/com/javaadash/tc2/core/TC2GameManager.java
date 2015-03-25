package com.javaadash.tc2.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.javaadash.tc2.core.context.GameContext;
import com.javaadash.tc2.core.interfaces.player.Player;

/**
 * This class is the entry point for a game to start
 * 
 * 
 * @author b2floo
 */
public class TC2GameManager 
{
	private GameStateResolver stateResolver = new GameStateResolver();
	private Logger log = LoggerFactory.getLogger(TC2GameManager.class);
	
	public void startGame(Player player1, Player player2)
	{
		// create the game context
		GameContext context = new GameContext();
		
		// who starts?
		whoStarts(context, player1, player2);
		
		// then alternate turns
		boolean endGame = false;
        Player tmpPlayer = null;
		
		while(!endGame)
		{
			log.info(" = Turn {} starts = ", context.getTurn());
			
			// get cards for both players
			context.setState(GameState.CARDS_DISTRIBUTION);
			stateResolver.resolve(context);
			
			// players choose a character one after the other
			context.setState(GameState.PLAYER_CHOOSE_CHARACTER);
			setupConcurrentAction(context, stateResolver);
			
			
			// players choose an action one after the other
			context.setState(GameState.PLAYER_CHOOSE_ACTION);
			setupConcurrentAction(context, stateResolver);
			
			// after cards are decided, now we resolve the turn
			context.setState(GameState.TURN_RESOLUTION);
			stateResolver.resolve(context);
			
			// players discard cards one after the other
			context.setState(GameState.PLAYER_CHOOSE_DISCARD);
			setupConcurrentAction(context, stateResolver);
            
			context.setState(GameState.CLEAR_BOARD);
			stateResolver.resolve(context);
			
			context.setState(GameState.CHECK_WINNER);
			stateResolver.resolve(context);
			
			log.debug("End turn {}", context.getTurn());
            switch (context.getWinner()) 
            {
                case FIRST_PLAYER:
                    log.info("Winner is {}", context.getFirstPlayer());
                    endGame = true;
                    break;
                case SECOND_PLAYER:
                    log.info("Winner is {}", context.getSecondPlayer());
                    endGame = true;
                    break;
                case TIE:
                    log.info("Game ends with a tie");
                    endGame = true;
                    break;
                case NOT_YET:
                    // start next Turn
                    context.setTurn(context.getTurn()+1);
                    tmpPlayer = context.getFirstPlayer();
                    context.setFirstPlayer(context.getSecondPlayer());
                    context.setSecondPlayer(tmpPlayer);
                    break;
            }
            
            log.info("Score {}:{} - {}:{}", 
                    new Object[]{context.getFirstPlayer(), 
                                 context.getFirstPlayer().getScore(), 
                                 context.getSecondPlayer(), 
                                 context.getSecondPlayer().getScore()});
		}
	}

	void whoStarts(GameContext context, Player player1, Player player2)
	{
		if(Math.random() < 0.5 )
		{
			context.setFirstPlayer(player1);
			context.setSecondPlayer(player2);
		}
		else
		{
			context.setFirstPlayer(player2);
			context.setSecondPlayer(player1);
		}
		log.info("Player {} starts the game", context.getFirstPlayer());
	}
	
	
	void setupConcurrentAction(GameContext context,
			GameStateResolver stateResolver) {
		
		context.setCurrentPlayer(context.getFirstPlayer());
		stateResolver.resolve(context);
		context.setCurrentPlayer(context.getSecondPlayer());
		stateResolver.resolve(context);
	}

	// TODO remove this as public
	public GameStateResolver getStateResolver() {
		return stateResolver;
	}
}
