package com.javaadash.tc2.core;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.javaadash.tc2.core.board.CardLocation;
import com.javaadash.tc2.core.card.Card;
import com.javaadash.tc2.core.card.condition.Condition;
import com.javaadash.tc2.core.context.GameContext;
import com.javaadash.tc2.core.exceptions.TC2CoreException;
import com.javaadash.tc2.core.interfaces.player.Player;

/**
 * Interacts with the player when choices are needed
 * 
 * @author b2floo
 *
 */
public class PlayManager 
{
	private Logger log = LoggerFactory.getLogger(Player.class);
	
	public void play(GameContext context)
	{
		switch (context.getState()) 
		{
			case GameState.BEGINNING:
				startGame(context);
			case GameState.PLAYER_CHOOSE_CHARACTER:
				chooseCharacter(context);
				break;

			case GameState.PLAYER_CHOOSE_ACTIONS:
				chooseAction(context);
				break;
			
			case GameState.PLAYER_CHOOSE_DISCARD:
				chooseDiscard(context);
				break;
				
			default:
				throw new IllegalStateException("Illegal game state : "+context.getState());
		}
	}
	
	protected void startGame(GameContext context) {
		log.info("Starting the game");
		try {
			context.getFirstPlayer().getPlayerInterface().startGame();
			context.getSecondPlayer().getPlayerInterface().startGame();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TC2CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	protected void chooseCharacter(GameContext context)
	{
		Player currentPlayer = context.getCurrentPlayer();
		log.info("{} chooses a character", currentPlayer.getName());
		
		// select a character
		List<Card> availableCharacters = currentPlayer.getIngameDeck().getCards(CardType.CHARACTER, CardLocation.HAND);
		log.info("Available characters : {}", availableCharacters);
		Card selectedCharacter = null;
		if(currentPlayer.getPlayerInterface() != null && availableCharacters.size() > 0)
			try {
				selectedCharacter = currentPlayer.getPlayerInterface().selectCharacter(availableCharacters);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		log.info("Selected character : {}", selectedCharacter);
		
		if(selectedCharacter != null)
			currentPlayer.getIngameDeck().setCardLocation(selectedCharacter, CardLocation.BOARD);
	}
	
	protected void chooseAction(GameContext context)
	{
		Player currentPlayer = context.getCurrentPlayer();
		log.info("{} chooses an action",  currentPlayer.getName());
		
		// select an action
		Collection<Card> availableActions = getAvailableActions(context);
		log.info("Available actions : {}", availableActions);
		Card selectedAction = null;
		if(currentPlayer.getPlayerInterface() != null && availableActions.size() > 0)
			try {
				selectedAction = currentPlayer.getPlayerInterface().selectAction(availableActions);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		log.info("Selected action : {}", selectedAction );
		
		if(selectedAction != null)
			currentPlayer.getIngameDeck().setCardLocation(selectedAction, CardLocation.BOARD);
	}
	
	protected void chooseDiscard(GameContext context)
	{
		Player currentPlayer = context.getCurrentPlayer();
		log.debug("{} chooses discard",  currentPlayer.getName());
	}
	
	Collection<Card> getAvailableActions(GameContext context)
	{
		List<Card> availableActions = new ArrayList<Card>();
		for(Card action : context.getCurrentPlayer().getIngameDeck().getCards(CardType.ACTION, CardLocation.HAND))
		{
			log.debug("Check restriction on action {} ", action);
			boolean available = true;
			for(Condition condition:action.getConditions())
			{
				log.debug("Condition : {}", condition);
				if(! condition.isFulfilled(context))
					available = false;
			}
			if(available)
				availableActions.add(action);
		}
		return availableActions;
	}

}
