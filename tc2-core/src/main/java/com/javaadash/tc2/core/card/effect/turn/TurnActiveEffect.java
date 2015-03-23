package com.javaadash.tc2.core.card.effect.turn;

import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.javaadash.tc2.core.card.effect.Effect;
import com.javaadash.tc2.core.context.GameContext;

public class TurnActiveEffect implements Effect
{
	private static final long serialVersionUID = 8132787058089852159L;
    private static final Logger log = LoggerFactory.getLogger(TurnActiveEffect.class);
    
	private Effect effect = null;
    private Set<Integer> turns = new HashSet<Integer>();
    
	public TurnActiveEffect(Effect effect, Set<Integer> turns) {
		this.effect = effect;
		this.turns = turns;
	}
	
	public void resolve(GameContext context) {
		if(!turns.contains(context.getTurn())) {
            log.debug("Effect {} is not effective", this);
            return;
        }
		
		log.debug("Effect {} is effective", this);
		effect.resolve(context);
	}
	
	public void resolveEnd(GameContext context) {
		if(!turns.contains(context.getTurn())) {
            log.debug("Effect {} dont need to be deactivated", this);
            return;
        }
		
		log.debug("Effect {} need to be deactivated", this);
		effect.resolveEnd(context);
	}
	
	@Override
	public String toString() {
		return new StringBuilder()
		.append(effect)
		.append(" TURNS ")
		.append(turns).toString();
	}

}
