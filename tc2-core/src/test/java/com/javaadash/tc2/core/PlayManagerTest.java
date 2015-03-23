package com.javaadash.tc2.core;

import junit.framework.TestCase;

import com.javaadash.tc2.core.board.CardLocation;
import com.javaadash.tc2.core.card.Card;
import com.javaadash.tc2.core.card.condition.MockCondition;
import com.javaadash.tc2.core.context.GameContext;
import com.javaadash.tc2.core.interfaces.player.MockTC2PlayerInterface;
import com.javaadash.tc2.core.interfaces.player.Player;

public class PlayManagerTest extends TestCase
{
	private static final long serialVersionUID = -4379295777625056705L;
	private PlayManager playManager = new PlayManager();
	private MockTC2PlayerInterface mockPlayerInterface = null;
	
	@Override
	protected void setUp() throws Exception 
	{
		mockPlayerInterface = new MockTC2PlayerInterface();
	}
	
	public void testNoAvailableCharacter() throws Exception
	{
		Player p = GameUtils.getPlayer("junit", 0, 0, 0);
		p.setPlayerInterface(mockPlayerInterface);
		
		GameContext context = new GameContext();
		context.setState(GameState.PLAYER_CHOOSE_CHARACTER);
		context.setCurrentPlayer(p);
		assertEquals(-1, mockPlayerInterface.getAvailableCharactersSize());
		playManager.play(context);
		assertEquals(-1, mockPlayerInterface.getAvailableCharactersSize());
	}
	
	public void testPlay1Character() throws Exception
	{
		Player p = GameUtils.getPlayer("junit", 1, 0, 0);
		p.setPlayerInterface(mockPlayerInterface);
		
		GameContext context = new GameContext();
		context.setState(GameState.PLAYER_CHOOSE_CHARACTER);
		context.setCurrentPlayer(p);
		assertEquals(-1, mockPlayerInterface.getAvailableCharactersSize());
		playManager.play(context);
		assertEquals(1, mockPlayerInterface.getAvailableCharactersSize());
	}
	
	public void testNoAvailableAction() throws Exception
	{
		Player p = GameUtils.getPlayer("junit", 0, 0, 0);
		p.setPlayerInterface(mockPlayerInterface);
		
		GameContext context = new GameContext();
		context.setState(GameState.PLAYER_CHOOSE_ACTIONS);
		context.setCurrentPlayer(p);
		assertEquals(-1, mockPlayerInterface.getAvailableActionsSize());
		playManager.play(context);
		assertEquals(-1, mockPlayerInterface.getAvailableActionsSize());
	}
	
	public void testPlay1Action() throws Exception
	{
		Player p = GameUtils.getPlayer("junit", 0, 1, 0);
		p.setPlayerInterface(mockPlayerInterface);
		
		// put the action in hand
		for(Card action:p.getIngameDeck().getCards(CardType.ACTION))
			p.getIngameDeck().setCardLocation(action, CardLocation.HAND);
		
		assertEquals(0, p.getIngameDeck().getCards(CardType.ACTION,CardLocation.BOARD).size());
		GameContext context = new GameContext();
		context.setState(GameState.PLAYER_CHOOSE_ACTIONS);
		context.setCurrentPlayer(p);
		assertEquals(-1, mockPlayerInterface.getAvailableActionsSize());
		playManager.play(context);
		assertEquals(1, mockPlayerInterface.getAvailableActionsSize());
	}
	
	/*
	public void testPlay2Actions() throws Exception
	{
		Player p = GameUtils.getPlayer("junit", 0, 2);
		p.setPlayListener(this);
		
		// put actions in hand and set them chainable
		for(Card action:p.getIngameDeck().getCards(CardType.ACTION))
		{
			action.setChain(new Chain());
			p.getIngameDeck().setCardLocation(action, CardLocation.HAND);
		}
		
		assertEquals(0, p.getIngameDeck().getCards(CardType.ACTION,CardLocation.BOARD).size());
		GameContext context = new GameContext();
		context.setState(GameState.PLAYER_CHOOSE_ACTIONS);
		p.play(context);
		assertEquals(2, p.getIngameDeck().getCards(CardType.ACTION,CardLocation.BOARD).size());
	}*/
	
	
	public void testAllRestrictedActions() throws Exception
	{
		Player p = GameUtils.getPlayer("junit", 1, 4, 0);
		p.setPlayerInterface(mockPlayerInterface);
		MockCondition condition = new MockCondition(false);
		
		// put character and action on board
		p.getIngameDeck().setCardLocation(
				p.getIngameDeck().getCard(CardType.CHARACTER,CardLocation.HAND), CardLocation.BOARD);
		for(Card action:p.getIngameDeck().getCards(CardType.ACTION))
		{
			p.getIngameDeck().setCardLocation(action, CardLocation.HAND);
			action.addCondition(condition);
		}
		
		GameContext context = new GameContext();
		context.setState(GameState.PLAYER_CHOOSE_ACTIONS);
		context.setCurrentPlayer(p);
		assertEquals(-1, mockPlayerInterface.getAvailableActionsSize());
		playManager.play(context);
		assertEquals(-1, mockPlayerInterface.getAvailableActionsSize());
	}


}
