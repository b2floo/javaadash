package com.javaadash.tc2.core;

import com.javaadash.tc2.core.WinnerCheck;
import com.javaadash.tc2.core.board.CardLocation;
import com.javaadash.tc2.core.card.Card;
import com.javaadash.tc2.core.context.GameContext;
import com.javaadash.tc2.core.context.GameContext.Winner;
import com.javaadash.tc2.core.interfaces.player.Player;

import junit.framework.TestCase;

public class WinnerCheckTest extends TestCase
{
	private Player player;
	private int maxCards = 10;
	private int maxCharacters = 1;
	private WinnerCheck winnerCheck = new WinnerCheck();
	
	@Override
	protected void setUp() throws Exception 
	{
		player = GameUtils.getPlayer("junit",maxCharacters, maxCards, 0);
		
		for(Card c: player.getIngameDeck().getCards(CardType.CHARACTER))
			c.setIntSetting("LIFE", 1);
	}
	
	public void testNotOut() throws Exception
	{
		assertFalse(winnerCheck.resolveOut(player));
	}
	
	public void testNoCardInStock() throws Exception
	{
		int index=0;
		for(Card a: player.getIngameDeck().getCards(CardType.ACTION))
		{
			player.getIngameDeck().setCardLocation(a, CardLocation.DISCARD);
			if(index < player.getIngameDeck().getCards(CardType.ACTION).size() - 1)
				assertFalse(winnerCheck.resolveOut(player));
			else
				assertTrue(winnerCheck.resolveOut(player));
			index++;
		}
	}
	
	public void testAllCharactersOut() throws Exception
	{
		int index=0;
		for(Card c:player.getIngameDeck().getCards(CardType.CHARACTER))
		{
			c.setIntSetting("LIFE", -1);
			if(index < player.getIngameDeck().getCards(CardType.CHARACTER).size() - 1)
				assertFalse(winnerCheck.resolveOut(player));
			else
				assertTrue(winnerCheck.resolveOut(player));
			assertTrue(player.getIngameDeck().getCards(CardType.CHARACTER,CardLocation.OUTOFGAME).contains(c));
			index++;
		}
	}
	
	public void testCheckNoWinnerYet() throws Exception
	{
		int maxCards = 10;
		int maxCharacters = 3;
		
		Player p1 = GameUtils.getPlayer("junit1", maxCharacters, maxCards, 0);
		for(Card c:p1.getIngameDeck().getCards(CardType.CHARACTER))
			c.setIntSetting("LIFE", 1);
		Player p2 = GameUtils.getPlayer("junit2", maxCharacters, maxCards, 0);
		for(Card c:p2.getIngameDeck().getCards(CardType.CHARACTER))
			c.setIntSetting("LIFE", 1);
		
		GameContext context = new GameContext();
		context.setFirstPlayer(p1);
		context.setSecondPlayer(p2);
		
		// deal cards
		winnerCheck.checkWinner(context);
		assertEquals(Winner.NOT_YET, context.getWinner());
	}
	
	public void testCheckWinnerStartPlayer() throws Exception
	{
		int maxCards = 10;
		int maxCharacters = 3;
		
		Player p1 = GameUtils.getPlayer("junit1", maxCharacters, maxCards, 0);
		for(Card c:p1.getIngameDeck().getCards(CardType.CHARACTER))
			c.setIntSetting("LIFE", 1);
		Player p2 = GameUtils.getPlayer("junit2", maxCharacters, maxCards, 0);
		for(Card c:p2.getIngameDeck().getCards(CardType.CHARACTER))
			c.setIntSetting("LIFE", -1);
		
		GameContext context = new GameContext();
		context.setFirstPlayer(p1);
		context.setSecondPlayer(p2);
		
		// deal cards
		winnerCheck.checkWinner(context);
		assertEquals(Winner.START_PLAYER, context.getWinner());
	}
	
	public void testCheckWinnerNextPlayer() throws Exception
	{
		int maxCards = 10;
		int maxCharacters = 3;
		
		Player p1 = GameUtils.getPlayer("junit1", maxCharacters, maxCards, 0);
		for(Card c:p1.getIngameDeck().getCards(CardType.CHARACTER))
			c.setIntSetting("LIFE", -1);
		Player p2 = GameUtils.getPlayer("junit2", maxCharacters, maxCards, 0);
		for(Card c:p2.getIngameDeck().getCards(CardType.CHARACTER))
			c.setIntSetting("LIFE", 1);
		
		GameContext context = new GameContext();
		context.setFirstPlayer(p1);
		context.setSecondPlayer(p2);
		
		// deal cards
		winnerCheck.checkWinner(context);
		assertEquals(Winner.NEXT_PLAYER, context.getWinner());
	}
	
	public void testCheckWinnerStartPlayer_BothOut() throws Exception
	{
		int maxCards = 10;
		int maxCharacters = 3;
		
		Player p1 = GameUtils.getPlayer("junit1", maxCharacters, maxCards, 0);
		p1.setScore(15);
		for(Card c:p1.getIngameDeck().getCards(CardType.CHARACTER))
			c.setIntSetting("LIFE", -1);
		Player p2 = GameUtils.getPlayer("junit2", maxCharacters, maxCards, 0);
		p2.setScore(10);
		for(Card c:p2.getIngameDeck().getCards(CardType.CHARACTER))
			c.setIntSetting("LIFE", -1);
		
		GameContext context = new GameContext();
		context.setFirstPlayer(p1);
		context.setSecondPlayer(p2);
		
		// deal cards
		winnerCheck.checkWinner(context);
		assertEquals(Winner.START_PLAYER, context.getWinner());
	}
	
	public void testCheckWinnerNextPlayer_BothOut() throws Exception
	{
		int maxCards = 10;
		int maxCharacters = 3;
		
		Player p1 = GameUtils.getPlayer("junit1", maxCharacters, maxCards, 0);
		p1.setScore(10);
		for(Card c:p1.getIngameDeck().getCards(CardType.CHARACTER))
			c.setIntSetting("LIFE", -1);
		Player p2 = GameUtils.getPlayer("junit2", maxCharacters, maxCards, 0);
		p2.setScore(15);
		for(Card c:p2.getIngameDeck().getCards(CardType.CHARACTER))
			c.setIntSetting("LIFE", -1);
		
		GameContext context = new GameContext();
		context.setFirstPlayer(p1);
		context.setSecondPlayer(p2);
		
		// deal cards
		winnerCheck.checkWinner(context);
		assertEquals(Winner.NEXT_PLAYER, context.getWinner());
	}
	
	public void testCheckWinnerTie() throws Exception
	{
		int maxCards = 10;
		int maxCharacters = 3;
		
		Player p1 = GameUtils.getPlayer("junit1", maxCharacters, maxCards, 0);
		p1.setScore(10);
		for(Card c:p1.getIngameDeck().getCards(CardType.CHARACTER))
			c.setIntSetting("LIFE", -1);
		Player p2 = GameUtils.getPlayer("junit2", maxCharacters, maxCards, 0);
		p2.setScore(10);
		for(Card c:p2.getIngameDeck().getCards(CardType.CHARACTER))
			c.setIntSetting("LIFE", -1);
		
		GameContext context = new GameContext();
		context.setFirstPlayer(p1);
		context.setSecondPlayer(p2);
		
		// deal cards
		winnerCheck.checkWinner(context);
		assertEquals(Winner.TIE, context.getWinner());
	}

}
