package com.javaadash.tc2.core;

import com.javaadash.tc2.core.board.BoardClearer;
import com.javaadash.tc2.core.context.GameContext;

public class GameStateResolver 
{
	private Dealer dealer = new Dealer();
	private TurnResolver turnResolver = new TurnResolver();
	private BoardClearer boardClearer = new BoardClearer();
	private WinnerCheck winnerChecker = new WinnerCheck();
	private PlayManager playManager = new PlayManager();
	
	public void resolve(GameContext context)
	{
		switch (context.getState()) 
		{
			case GameState.CARDS_DISTRIBUTION:
				dealer.dealCards(context);
				break;
				
			case GameState.PLAYER_CHOOSE_CHARACTER:
			case GameState.PLAYER_CHOOSE_ACTION:
			case GameState.PLAYER_CHOOSE_DISCARD:
				playManager.play(context);
				break;
				
			case GameState.TURN_RESOLUTION:
				turnResolver.resolveTurn(context);
				break;

			case GameState.CLEAR_BOARD:
				boardClearer.clearBoard(context);
				break;
				
			case GameState.CHECK_WINNER:
				winnerChecker.checkWinner(context);
				break;
				
			default:
				throw new IllegalStateException("Illegal game state : " + context.getState());
		}
	}

	// TODO remove this as public
	public void setTurnResolver(TurnResolver turnResolver) {
		this.turnResolver = turnResolver;
	}
}
