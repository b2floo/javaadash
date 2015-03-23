package com.javaadash.tc2.core.context;

import com.javaadash.tc2.core.interfaces.player.Player;

public class GameContext 
{
	private int turn = 0;
	private Player firstPlayer = null;
	private Player secondPlayer = null; 
    private Player currentPlayer = null;
    private int state = 0;
    private Winner winner = null;
    
	public enum Winner
    {
        START_PLAYER, NEXT_PLAYER, TIE, NOT_YET;
    }
	
	public int getTurn() 
	{
		return turn;
	}
	
	public void setTurn(int turn) 
	{
		this.turn = turn;
	}
	
	public Player getFirstPlayer() 
	{
		return firstPlayer;
	}
	
	public void setFirstPlayer(Player firstPlayer) 
	{
		this.firstPlayer = firstPlayer;
	}
	
	public Player getSecondPlayer() 
	{
		return secondPlayer;
	}
	
	public void setSecondPlayer(Player secondPlayer) 
	{
		this.secondPlayer = secondPlayer;
	}
    
    public void setCurrentPlayer(Player p)
    {
        currentPlayer = p;
    }
    
    public Player getCurrentPlayer()
    {
        return currentPlayer;
    }
    
	public int getState() 
	{
		return state;
	}

	public void setState(int state) 
	{
		this.state = state;
	}
	
	public Winner getWinner() 
	{
		return winner;
	}

	public void setWinner(Winner winner) 
	{
		this.winner = winner;
	}
}
