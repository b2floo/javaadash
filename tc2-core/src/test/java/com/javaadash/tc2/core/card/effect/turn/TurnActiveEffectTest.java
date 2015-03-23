package com.javaadash.tc2.core.card.effect.turn;

import java.util.HashSet;
import java.util.Set;

import junit.framework.TestCase;

import com.javaadash.tc2.core.card.effect.MockEffect;
import com.javaadash.tc2.core.context.GameContext;

public class TurnActiveEffectTest extends TestCase
{
	@Override
	protected void setUp() throws Exception  {
		activeTurns = new HashSet<Integer>();
		activeTurns.add(1);
		activeTurns.add(5);
		
		turnActiveEffect = new TurnActiveEffect(mockEffect, activeTurns);
	}

	public void testResolve() {
		GameContext context = new GameContext();
		for(int i=0; i<10; i++) {
			context.setTurn(i);
			mockEffect.resetResolutions();
			turnActiveEffect.resolve(context);
			if(activeTurns.contains(i))
				assertTrue(mockEffect.isResolved());
			else
				assertFalse(mockEffect.isResolved());
		}
	}
	
	public void testEndResolve()
	{
		GameContext context = new GameContext();
		for(int i=0; i<10; i++) {
			context.setTurn(i);
			mockEffect.resetResolutions();
			turnActiveEffect.resolveEnd(context);
			if(activeTurns.contains(i))
				assertTrue(mockEffect.isEndResolved());
			else
				assertFalse(mockEffect.isEndResolved());
		}
	}
	
	private TurnActiveEffect turnActiveEffect;
	private MockEffect mockEffect = new MockEffect();
	private Set<Integer> activeTurns;
}
