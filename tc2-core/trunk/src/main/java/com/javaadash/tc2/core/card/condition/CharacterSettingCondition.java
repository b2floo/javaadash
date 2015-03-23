package com.javaadash.tc2.core.card.condition;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.javaadash.tc2.core.card.Card;
import com.javaadash.tc2.core.card.effect.Target;
import com.javaadash.tc2.core.card.effect.TargetResolver;
import com.javaadash.tc2.core.context.GameContext;

public class CharacterSettingCondition implements Condition
{
	private static final long serialVersionUID = 6022811082831293233L;
	private static final Logger log = LoggerFactory.getLogger(CharacterSettingCondition.class);
	
	public enum Sign {GREATER, GREATER_EQUALS, EQUALS, LESS, LESS_EQUALS}
	protected String setting;
	protected String value;
	protected Sign sign;
	protected Target target;
	protected TargetResolver targetResolver = new TargetResolver();
	
	public CharacterSettingCondition(String setting, Sign sign, String value, Target target)
	{
		this.setting = setting;
		this.sign = sign;
		this.value = value;
		this.target = target;
	}

	@Override
	public boolean isFulfilled(GameContext context) 
	{
		List<Card> characters = targetResolver.getCharactersFromTarget(target, context);
		
		for(Card character : characters) {
			log.debug("Checking character {} setting {} condition {} {} {}", 
					new Object[]{character, setting, character.getSetting(setting), sign, value});
			switch (sign)  {
				case GREATER:
					if (character.getIntSetting(setting) <= Integer.parseInt(value))
						return false;
					break;
				case GREATER_EQUALS:
					if (character.getIntSetting(setting) < Integer.parseInt(value))
						return false;
					break;
				case EQUALS:
					if (character.getSetting(setting).compareToIgnoreCase(value) != 0)
						return false;
					break;
				case LESS:
					if (character.getIntSetting(setting) >= Integer.parseInt(value))
						return false;
					break;
				case LESS_EQUALS:
					if (character.getIntSetting(setting) > Integer.parseInt(value))
						return false;
					break;
			}
		}
		return true;
	}
	
	@Override
	public String toString()  {
		return new StringBuilder()
		.append(setting)
		.append(" ")
		.append(sign)
		.append(" ")
		.append(value)
		.toString();
	}
	
}
