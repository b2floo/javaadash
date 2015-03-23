package com.javaadash.tc2.core.card.condition;

import java.io.Serializable;

import com.javaadash.tc2.core.context.GameContext;

public interface Condition extends Serializable
{
	/**
	 * Check if the condition is fulfilled by the current gameContext.
	 * If condition matches then the action can be played
	 * @param context Game context
	 * @return True if condition is fulfilled
	 */
	public boolean isFulfilled(GameContext context);
}
