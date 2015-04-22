package com.javaadash.tc2.core.card.effect.character.setting.modification;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.javaadash.tc2.core.card.Card;
import com.javaadash.tc2.core.card.effect.CardEffectLog;
import com.javaadash.tc2.core.card.effect.Effect;
import com.javaadash.tc2.core.card.effect.SettingChange;
import com.javaadash.tc2.core.card.effect.Target;
import com.javaadash.tc2.core.card.effect.TargetResolver;
import com.javaadash.tc2.core.card.effect.setting.RandomRangeValue;
import com.javaadash.tc2.core.card.effect.setting.RangeValue;
import com.javaadash.tc2.core.context.GameContext;

public class CharacterSettingModificationEffect implements Effect {
  private static final long serialVersionUID = 4796357637727471295L;
  private static final Logger log = LoggerFactory
      .getLogger(CharacterSettingModificationEffect.class);

  protected String setting = null;
  protected RandomRangeValue modifier;
  protected Target target;

  private Map<Integer, Integer> modifierValues = new HashMap<Integer, Integer>();

  public CharacterSettingModificationEffect(String setting, String modifier, Target target) {
    this.setting = setting;
    this.modifier = new RandomRangeValue(modifier);
    this.target = target;
  }

  public void resolve(GameContext context, CardEffectLog cardEffectLog) {
    modifierValues.clear();
    Integer modifierValue = modifier.getRandom();

    for (Card charr : TargetResolver.getCharactersFromTarget(target, context)) {

      RangeValue newValue = charr.getIntSetting(setting).add(modifierValue);
      log.debug("{} has calculated {} setting {} value to {}", new Object[] {this, charr, setting,
          newValue});
      charr.setIntSetting(setting, newValue);
      modifierValues.put(charr.getId(), modifierValue);

      SettingChange settingChange =
          new SettingChange(charr.getId(), charr.getDescription(), setting);
      settingChange.setNewValue(newValue.getDescription());
      settingChange.setDiff(modifierValue > 0 ? "+" + modifierValue : "" + modifierValue);
      cardEffectLog.getSettingChanges().add(settingChange);
    }
  }

  public void resolveEnd(GameContext context) {
    for (Card charr : TargetResolver.getCharactersFromTarget(target, context)) {
      Integer modifierValue = modifierValues.get(charr.getId());
      RangeValue newValue = charr.getIntSetting(setting).remove(modifierValue);
      log.debug("{} has calculated {} setting {} value to {}", new Object[] {this, charr, setting,
          newValue});
      charr.setIntSetting(setting, newValue);
    }
  }

  @Override
  public String toString() {
    return "CharacterSettingModificationEffect [setting=" + setting + ", modifier=" + modifier
        + ", target=" + target + "]";
  }
}
