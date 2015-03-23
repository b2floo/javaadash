package com.javaadash.tc2.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import junit.framework.TestCase;

import com.javaadash.tc2.core.card.Card;
import com.javaadash.tc2.core.card.condition.Condition;
import com.javaadash.tc2.core.card.effect.Effect;
import com.javaadash.tc2.core.card.effect.MockEffect;
import com.javaadash.tc2.core.context.GameContext;

public class ActionEffectResolverTest extends TestCase
{
	private CardEffectResolver effectResolver = new CardEffectResolver();
	
	public void testResolve() {
		List<Effect> allEffects = new ArrayList<Effect>();
		List<Card> cards = new ArrayList<Card>();
		// use loops to create more complex object hierarchy
		// and check all effects are taken into account
		for(int j=0; j< 10; j++) {
			List<Effect> effects = new ArrayList<Effect>();
			for(int i=0; i< 5; i++) {
				MockEffect mockEffect = new MockEffect();
				effects.add(mockEffect);
				allEffects.add(mockEffect);
			}
			cards.add(new Card(-1, CardType.ACTION, "junit-action", 
					effects, new ArrayList<Condition>(), new HashMap<String, String>()));
		}
		
		effectResolver.resolveCardEffect(cards, new GameContext());
		
		for(Effect effect: allEffects)
			assertTrue(((MockEffect)effect).isResolved());
	}
	
	public void testEndResolve()
	{
		List<Effect> allEffects = new ArrayList<Effect>();
		List<Card> cards = new ArrayList<Card>();
		// use loops to create more complex object hierarchy
		// and check all effects are taken into account
		for(int j=0; j< 10; j++) {
			List<Effect> effects = new ArrayList<Effect>();
			for(int i=0; i< 5; i++) {
				MockEffect mockEffect = new MockEffect();
				effects.add(mockEffect);
				allEffects.add(mockEffect);
			}
			cards.add(new Card(-1, CardType.ACTION, "junit-action", 
					effects, new ArrayList<Condition>(), new HashMap<String, String>()));
		}
		
		effectResolver.resolveEndCardEffect(cards, new GameContext());
		
		for(Effect effect: allEffects)
			assertTrue(((MockEffect)effect).isEndResolved());
	}
}
