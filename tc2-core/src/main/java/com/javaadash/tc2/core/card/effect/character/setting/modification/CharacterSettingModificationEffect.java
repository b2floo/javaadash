package com.javaadash.tc2.core.card.effect.character.setting.modification;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.javaadash.tc2.core.card.Card;
import com.javaadash.tc2.core.card.effect.CardEffectLog;
import com.javaadash.tc2.core.card.effect.Effect;
import com.javaadash.tc2.core.card.effect.SettingChange;
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

  public CharacterSettingModificationEffect(String setting, int modifier, Target target) {
    this.setting = setting;
    this.modifier = modifier;
    this.target = target;
  }

  public void resolve(GameContext context, CardEffectLog cardEffectLog) {
    for (Card charr : TargetResolver.getCharactersFromTarget(target, context)) {
      Integer newValue = charr.getIntSetting(setting) + modifier;
      charr.setIntSetting(setting, newValue);
      log.debug("New settings : {}", charr);
      SettingChange settingChange =
          new SettingChange(charr.getId(), charr.getDescription(), setting);
      settingChange.setNewValue(Integer.toString(newValue));
      settingChange.setDiff((modifier > 0 ? "+" + modifier : "" + modifier));
      cardEffectLog.getSettingChanges().add(settingChange);
    }
  }

  public void resolveEnd(GameContext context) {
    for (Card charr : TargetResolver.getCharactersFromTarget(target, context)) {
      charr.setIntSetting(setting, charr.getIntSetting(setting) - modifier);
      log.debug("New settings : {}", charr);
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
