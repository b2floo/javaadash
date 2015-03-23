package com.javaadash.tc2.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.javaadash.tc2.core.card.Card;
import com.javaadash.tc2.core.interfaces.player.Player;

public class CharacterMatchResolver 
{
	/**
	 * Resolve a match between 2 characters. First character is the attacker, second one is the defender.
	 * First player's score is updated depending on damages.
	 * @param char1 Attacking character
	 * @param p1 Attacking player
	 * @param char2 Defending character
	 */
	public void resolveCharacterMatch(Card char1, Player p1, Card char2)
	{
		// calculate life loss
		int damage1 = char1.getIntSetting("ATT") - char2.getIntSetting("DEF");
		if(damage1 <= 0)
		{
			log.info("Damaged to {} : null", char2);
			return;
		}
        p1.setScore(p1.getScore() + damage1);
        
		log.info("Damaged to {} : {}", char2, damage1);
		int life2 = char2.getIntSetting("LIFE") - damage1;
		log.info("Life left : {}", life2);
		char2.setIntSetting("LIFE", life2);
	}
	
	private Logger log = LoggerFactory.getLogger(CharacterMatchResolver.class);
}
