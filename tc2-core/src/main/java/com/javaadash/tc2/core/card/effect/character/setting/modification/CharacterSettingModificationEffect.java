package com.javaadash.tc2.core.card.effect.character.setting.modification;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.javaadash.tc2.core.PlayManager;
import com.javaadash.tc2.core.card.Card;
import com.javaadash.tc2.core.card.effect.Effect;
import com.javaadash.tc2.core.card.effect.Target;
import com.javaadash.tc2.core.card.effect.TargetResolver;
import com.javaadash.tc2.core.context.GameContext;

public class CharacterSettingModificationEffect implements Effect {
  private static final long serialVersionUID = 4796357637727471295L;
  private static final Logger log = LoggerFactory
      .getLogger(CharacterSettingModificationEffect.class);

  protected String setting = null;
  protected int modifier = 0;
  protected Target target;
  protected TargetResolver targetResolver = new TargetResolver();

  public CharacterSettingModificationEffect(String setting, int modifier, Target target) {
    this.setting = setting;
    this.modifier = modifier;
    this.target = target;
  }

  public void resolve(GameContext context) {
    for (Card charr : targetResolver.getCharactersFromTarget(target, context)) {
      charr.setIntSetting(setting, charr.getIntSetting(setting) + modifier);
      log.debug("New settings : {}", charr);
      PlayManager.updateSettings(context, charr.getDescription() + " " + setting + " "
          + (modifier > 0 ? "+" : "") + modifier);
    }
  }

  public void resolveEnd(GameContext context) {
    for (Card charr : targetResolver.getCharactersFromTarget(target, context)) {
      charr.setIntSetting(setting, charr.getIntSetting(setting) - modifier);
      log.debug("New settings : {}", charr);
      // PlayManager.updateSettings(context, charr.getDescription() + " " + setting + " "
      // + (modifier < 0 ? "+" + (-modifier) : "-" + modifier));
    }
  }

  @Override
  public String toString() {
    StringBuilder buf = new StringBuilder().append(setting);
    if (modifier > 0)
      buf.append("+");
    buf.append(modifier).append(" TARGET ").append(target);
    return buf.toString();
  }

}
