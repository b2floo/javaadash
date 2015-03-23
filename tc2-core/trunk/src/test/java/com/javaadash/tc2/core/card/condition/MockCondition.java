package com.javaadash.tc2.core.card.condition;

import com.javaadash.tc2.core.card.condition.Condition;
import com.javaadash.tc2.core.context.GameContext;

public class MockCondition implements Condition
{
	private static final long serialVersionUID = -3256000650751032027L;
	
	private boolean isFulfilled;
	
	public MockCondition(boolean isFulfilled)
	{
		this.isFulfilled = isFulfilled;
	}

	@Override
	public boolean isFulfilled(GameContext context) 
	{
		return isFulfilled;
	}
}
