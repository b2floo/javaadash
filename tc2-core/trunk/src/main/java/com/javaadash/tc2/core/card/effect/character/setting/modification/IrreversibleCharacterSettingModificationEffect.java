package com.javaadash.tc2.core.card.effect.character.setting.modification;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.javaadash.tc2.core.card.effect.Target;
import com.javaadash.tc2.core.context.GameContext;

public class IrreversibleCharacterSettingModificationEffect extends CharacterSettingModificationEffect
{
	private static final long serialVersionUID = -1528262995626726773L;
    private static final Logger log = LoggerFactory.getLogger(CharacterSettingModificationEffect.class);

	public IrreversibleCharacterSettingModificationEffect(String setting, int modifier, Target target) {
        super(setting, modifier, target);
    }
	
    public void resolveEnd(GameContext context) {
    	log.debug("Irreversible effect: nothing to do");
    }
    
    @Override
    public String toString()  {
        return new StringBuilder()
        .append(super.toString())
        .append(" IRREVERSIBLE")
        .toString();
    }
}
