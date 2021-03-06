package com.javaadash.tc2.core.card.effect.setting;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.javaadash.tc2.core.card.condition.CharacterSettingCondition;
import com.javaadash.tc2.core.card.effect.Effect;
import com.javaadash.tc2.core.card.effect.log.CardEffectLog;
import com.javaadash.tc2.core.context.GameContext;

public class SettingActiveEffect implements Effect {
  private static final long serialVersionUID = 8132787058089852159L;
  private static final Logger log = LoggerFactory.getLogger(SettingActiveEffect.class);

  private Effect effect = null;
  private CharacterSettingCondition condition;
  private boolean conditionFulfilled;

  public SettingActiveEffect(Effect effect, CharacterSettingCondition condition) {
    this.effect = effect;
    this.condition = condition;
  }

  public void resolve(GameContext context, CardEffectLog cardEffectLog) {
    // check if setting comparison is fulfilled
    if (condition.isFulfilled(context)) {
      log.debug("Effect {} is effective", this);
      conditionFulfilled = true;
      effect.resolve(context, cardEffectLog);
    } else {
      log.debug("Effect {} is NOT effective", this);
      conditionFulfilled = false;
    }
  }

  public void resolveEnd(GameContext context) {
    if (conditionFulfilled) {
      log.debug("Effect {} need to be deactivated", this);
      effect.resolveEnd(context);
    } else {
      log.debug("Effect {} is NOT effective", this);
    }
    conditionFulfilled = false;
  }

  @Override
  public String toString() {
    return "SettingActiveEffect [effect=" + effect + ", condition=" + condition + "]";
  }
}
