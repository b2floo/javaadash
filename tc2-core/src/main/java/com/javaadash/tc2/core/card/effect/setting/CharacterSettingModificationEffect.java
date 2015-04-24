package com.javaadash.tc2.core.card.effect.setting;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.javaadash.tc2.core.card.Card;
import com.javaadash.tc2.core.card.RandomRangeValue;
import com.javaadash.tc2.core.card.RangeValue;
import com.javaadash.tc2.core.card.effect.Effect;
import com.javaadash.tc2.core.card.effect.Target;
import com.javaadash.tc2.core.card.effect.TargetResolver;
import com.javaadash.tc2.core.card.effect.log.CardEffectLog;
import com.javaadash.tc2.core.card.effect.log.SettingChange;
import com.javaadash.tc2.core.context.GameContext;

public class CharacterSettingModificationEffect implements Effect {
  private static final long serialVersionUID = 4796357637727471295L;
  private static final Logger log = LoggerFactory
      .getLogger(CharacterSettingModificationEffect.class);

  public enum SettingPart {
    MIN, MAX, ALL
  };

  protected String setting = null;
  protected RandomRangeValue modifier;
  protected Target target;
  protected SettingPart settingPart = SettingPart.ALL;

  protected Map<Integer, ModificationMemory> modifierValues =
      new HashMap<Integer, ModificationMemory>();

  public CharacterSettingModificationEffect(String setting, String modifier, Target target) {
    this.setting = setting;
    this.modifier = new RandomRangeValue(modifier);
    this.target = target;
  }

  public CharacterSettingModificationEffect(String setting, String modifier, Target target,
      SettingPart settingPart) {
    this.setting = setting;
    this.modifier = new RandomRangeValue(modifier);
    this.target = target;
    this.settingPart = settingPart;
  }

  public void resolve(GameContext context, CardEffectLog cardEffectLog) {
    modifierValues.clear();
    Integer modifierValue = modifier.getRandom();

    for (Card charr : TargetResolver.getCharactersFromTarget(target, context)) {

      RangeValue currentValue = charr.getIntSetting(setting);
      ModificationMemory memory = new ModificationMemory(modifierValue);
      RangeValue newValue = null;

      switch (settingPart) {
        case MIN:
          memory.setMinMaxInterval(currentValue.getMax() - currentValue.getMin());
          newValue = currentValue.add(modifierValue, 0);
          break;
        case MAX:
          memory.setMinMaxInterval(currentValue.getMax() - currentValue.getMin());
          newValue = currentValue.add(0, modifierValue);
          break;
        case ALL:
          newValue = currentValue.add(modifierValue);
          break;
      }
      log.debug("{} has calculated {} setting {} value to {}", new Object[] {this, charr, setting,
          newValue});
      charr.setIntSetting(setting, newValue);
      modifierValues.put(charr.getId(), memory);

      SettingChange settingChange =
          new SettingChange(charr.getId(), charr.getDescription(), setting);
      settingChange.setNewValue(newValue.getDescription());
      String modifierString = modifierValue > 0 ? "+" + modifierValue : "" + modifierValue;
      switch (settingPart) {
        case MIN:
          settingChange.setDiff(modifierString + "/0");
          break;
        case MAX:
          settingChange.setDiff("0/" + modifierString);
          break;
        case ALL:
          settingChange.setDiff(modifierString);
          break;
      }
      cardEffectLog.getSettingChanges().add(settingChange);
    }
  }

  public void resolveEnd(GameContext context) {
    for (Card charr : TargetResolver.getCharactersFromTarget(target, context)) {

      RangeValue currentValue = charr.getIntSetting(setting);
      ModificationMemory memory = modifierValues.get(charr.getId());
      RangeValue newValue = null;

      switch (settingPart) {
        case MIN:
          int initialMin = currentValue.remove(memory.getModifier(), 0).getMin();
          newValue = new RangeValue(initialMin, initialMin + memory.getMinMaxInterval());
          break;
        case MAX:
          int initialMax = currentValue.remove(0, memory.getModifier()).getMax();
          newValue = new RangeValue(initialMax - memory.getMinMaxInterval(), initialMax);
          break;
        case ALL:
          newValue = currentValue.remove(memory.getModifier());
          break;
      }
      log.debug("{} has calculated {} setting {} value to {}", new Object[] {this, charr, setting,
          newValue});
      charr.setIntSetting(setting, newValue);
    }
  }

  @Override
  public String toString() {
    return "CharacterSettingModificationEffect [setting=" + setting + ", modifier=" + modifier
        + ", target=" + target + ", settingPart=" + settingPart + "]";
  }

  private class ModificationMemory {
    private Integer modifier;
    private Integer minMaxInterval;

    public ModificationMemory(Integer modifier) {
      this.modifier = modifier;
    }

    public Integer getModifier() {
      return modifier;
    }

    public Integer getMinMaxInterval() {
      return minMaxInterval;
    }

    public void setMinMaxInterval(Integer minMaxInterval) {
      this.minMaxInterval = minMaxInterval;
    }
  }
}
