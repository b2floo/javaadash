package com.javaadash.tc2.core.interfaces.player;

import java.util.Collection;

import com.javaadash.tc2.core.card.Card;

public class MockTC2PlayerInterface implements TC2PlayerInterface {
	
	private static final long serialVersionUID = 60057190742736687L;
	private int availableCharactersSize = -1;
	private int availableActionsSize = -1;
	
	@Override
	public Card selectCharacter(Collection<Card> availableCharacters) 
	{
		availableCharactersSize = availableCharacters.size();
		if(availableCharactersSize > 0)
			return availableCharacters.iterator().next();
		else
			return null;
	}

	@Override
	public Card selectAction(Collection<Card> availableActions) 
	{
		availableActionsSize = availableActions.size();
		if(availableActionsSize > 0)
			return availableActions.iterator().next();
		else
			return null;
	}

	@Override
	public void startGame() {
		
	}

	public int getAvailableCharactersSize() {
		return availableCharactersSize;
	}

	public int getAvailableActionsSize() {
		return availableActionsSize;
	}
}
