package com.javaadash.tc2.core.card.effect.damage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.javaadash.tc2.core.card.Card;
import com.javaadash.tc2.core.card.RandomRangeValue;
import com.javaadash.tc2.core.card.effect.Effect;
import com.javaadash.tc2.core.card.effect.Target;
import com.javaadash.tc2.core.card.effect.TargetResolver;
import com.javaadash.tc2.core.card.effect.log.CardEffectLog;
import com.javaadash.tc2.core.context.GameContext;

public class DamageModificationEffect implements Effect, DamageModifier {
  private static final long serialVersionUID = -1063009634894731171L;
  private static final Logger log = LoggerFactory.getLogger(DamageModificationEffect.class);

  protected RandomRangeValue modifier;
  protected Target target;
  protected String cardName;

  public DamageModificationEffect() {
    super();
  }

  public DamageModificationEffect(String modifier, Target target, String cardName) {
    this.modifier = new RandomRangeValue(modifier);
    this.target = target;
    this.cardName = cardName;
  }

  @Override
  public void resolve(GameContext context, CardEffectLog cardEffectLog) {
    for (Card charr : TargetResolver.getCharactersFromTarget(target, context)) {
      charr.registerDamageModifier(this);
      log.debug("{} has registered as DamageModifier for {}", new Object[] {this, charr});
    }
  }

  @Override
  public void resolveEnd(GameContext context) {
    for (Card charr : TargetResolver.getCharactersFromTarget(target, context)) {
      charr.unregisterDamageModifier(this);
      log.debug("{} has unregistered as DamageModifier for {}", new Object[] {this, charr});
    }
  }

  @Override
  public int getValue(int initialDamage) {
    return (initialDamage > 0 ? modifier.getRandom() : 0);
  }

  @Override
  public String getCardName() {
    return cardName;
  }

  @Override
  public String toString() {
    return "DamageModificationEffect [modifier=" + modifier + ", target=" + target + "]";
  }
}
