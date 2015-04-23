package com.javaadash.tc2.core.card.effect.character.setting.modification;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.javaadash.tc2.core.card.effect.CardEffectLog;
import com.javaadash.tc2.core.card.effect.Effect;
import com.javaadash.tc2.core.context.GameContext;

public class IrreversibleEffect implements Effect {
  private static final long serialVersionUID = -1528262995626726773L;
  private static final Logger log = LoggerFactory.getLogger(IrreversibleEffect.class);

  protected Effect effect;

  public IrreversibleEffect(Effect effect) {
    this.effect = effect;
  }

  @Override
  public void resolve(GameContext context, CardEffectLog cardEffectLog) {
    effect.resolve(context, cardEffectLog);
  }

  @Override
  public void resolveEnd(GameContext context) {
    log.debug("Irreversible effect: nothing to do");
  }

  @Override
  public String toString() {
    return "IrreversibleEffect [effect=" + effect + "]";
  }
}
