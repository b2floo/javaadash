package com.javaadash.tc2.core.context;

import java.util.List;

import com.javaadash.tc2.core.card.CardDescription;
import com.javaadash.tc2.core.interfaces.player.Player;

public class GameContext 
{
	private int turn = 0;
	private Player firstPlayer = null;
	private List<CardDescription> player1cards;
	private List<CardDescription> player2cards;
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
	
    public List<CardDescription> getPlayer1cards() {
		return player1cards;
	}

	public void setPlayer1cards(List<CardDescription> player1cards) {
		this.player1cards = player1cards;
	}

	public List<CardDescription> getPlayer2cards() {
		return player2cards;
	}

	public void setPlayer2cards(List<CardDescription> player2cards) {
		this.player2cards = player2cards;
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
