package com.javaadash.tc2.core.card.effect.setting;

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
import com.javaadash.tc2.core.context.GameContext;

/**
 * Effect that is linked to 2 settings (on same character or not) and to a value modifier. When
 * resolved, the modifier is removed from first setting and is added to second setting.
 * 
 * @author b2floo
 * 
 */
public class SettingTransferEffect implements Effect {
  private static final long serialVersionUID = 7808577101051504378L;
  private static final Logger log = LoggerFactory.getLogger(SettingTransferEffect.class);

  private String firstSetting;
  private Target firstSettingTarget;
  private RandomRangeValue modifier;
  private String secondSetting;
  private Target secondSettingTarget;

  private Map<String, Integer> modifierValues = new HashMap<String, Integer>();

  public SettingTransferEffect(String firstSetting, Target firstSettingTarget, String modifier,
      String secondSetting, Target secondSettingTarget) {
    super();
    this.firstSetting = firstSetting;
    this.firstSettingTarget = firstSettingTarget;
    this.modifier = new RandomRangeValue(modifier);
    this.secondSetting = secondSetting;
    this.secondSettingTarget = secondSettingTarget;
  }

  @Override
  public void resolve(GameContext context, CardEffectLog cardEffectLog) {
    modifierValues.clear();
    Integer modifierValue = modifier.getRandom();

    // TODO store initial values as RandomSettingResolver
    for (Card charr : TargetResolver.getCharactersFromTarget(firstSettingTarget, context)) {
      RangeValue newValue = charr.getIntSetting(firstSetting).remove(modifierValue);
      log.debug("{} has calculated {} setting {} value to {}", new Object[] {this, charr,
          firstSetting, newValue});
      charr.setIntSetting(firstSetting, newValue);
      modifierValues.put(charr.getId() + firstSetting, modifierValue);

      SettingChange settingChange =
          new SettingChange(charr.getId(), charr.getDescription(), firstSetting);
      settingChange.setNewValue(newValue.getDescription());
      settingChange.setDiff(modifierValue > 0 ? "-" + modifierValue : "+" + (-modifierValue));
      cardEffectLog.getSettingChanges().add(settingChange);
    }
    for (Card charr : TargetResolver.getCharactersFromTarget(secondSettingTarget, context)) {
      RangeValue newValue = charr.getIntSetting(secondSetting).add(modifierValue);
      log.debug("{} has calculated {} setting {} value to {}", new Object[] {this, charr,
          secondSetting, newValue});
      charr.setIntSetting(secondSetting, newValue);
      modifierValues.put(charr.getId() + secondSetting, modifierValue);

      SettingChange settingChange =
          new SettingChange(charr.getId(), charr.getDescription(), secondSetting);
      settingChange.setNewValue(newValue.getDescription());
      settingChange.setDiff(modifierValue < 0 ? "" + modifierValue + "" : "+" + modifierValue);
      cardEffectLog.getSettingChanges().add(settingChange);
    }
  }

  @Override
  public void resolveEnd(GameContext context) {
    for (Card charr : TargetResolver.getCharactersFromTarget(firstSettingTarget, context)) {
      Integer modifierValue = modifierValues.get(charr.getId() + firstSetting);
      RangeValue newValue = charr.getIntSetting(firstSetting).add(modifierValue);
      log.debug("{} has calculated {} setting {} value to {}", new Object[] {this, charr,
          firstSetting, newValue});
      charr.setIntSetting(firstSetting, newValue);
    }
    for (Card charr : TargetResolver.getCharactersFromTarget(secondSettingTarget, context)) {
      Integer modifierValue = modifierValues.get(charr.getId() + secondSetting);
      RangeValue newValue = charr.getIntSetting(secondSetting).remove(modifierValue);
      log.debug("{} has calculated {} setting {} value to {}", new Object[] {this, charr,
          secondSetting, newValue});
      charr.setIntSetting(secondSetting, newValue);
    }
  }

  @Override
  public String toString() {
    return "SettingTransferEffect [firstSetting=" + firstSetting + ", firstSettingTarget="
        + firstSettingTarget + ", modifier=" + modifier + ", secondSetting=" + secondSetting
        + ", secondSettingTarget=" + secondSettingTarget + "]";
  }
}
