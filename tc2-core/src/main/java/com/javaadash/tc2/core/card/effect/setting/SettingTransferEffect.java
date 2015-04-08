package com.javaadash.tc2.core.card.effect.setting;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.javaadash.tc2.core.card.Card;
import com.javaadash.tc2.core.card.effect.CardEffectLog;
import com.javaadash.tc2.core.card.effect.Effect;
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
  private Integer valueModifier;
  private String secondSetting;
  private Target secondSettingTarget;

  public SettingTransferEffect(String firstSetting, Target firstSettingTarget,
      Integer valueModifier, String secondSetting, Target secondSettingTarget) {
    super();
    this.firstSetting = firstSetting;
    this.firstSettingTarget = firstSettingTarget;
    this.valueModifier = valueModifier;
    this.secondSetting = secondSetting;
    this.secondSettingTarget = secondSettingTarget;
  }

  @Override
  public void resolve(GameContext context, CardEffectLog cardEffectLog) {
    for (Card charr : TargetResolver.getCharactersFromTarget(firstSettingTarget, context)) {
      Integer newValue = charr.getIntSetting(firstSetting) - valueModifier;
      charr.setIntSetting(firstSetting, newValue);
      log.debug("Effect " + this.toString() + " has calculated " + charr.getDescription()
          + " setting value to " + newValue);
    }
    for (Card charr : TargetResolver.getCharactersFromTarget(secondSettingTarget, context)) {
      Integer newValue = charr.getIntSetting(secondSetting) + valueModifier;
      charr.setIntSetting(secondSetting, newValue);
      log.debug("Effect " + this.toString() + " has calculated " + charr.getDescription()
          + " setting value to " + newValue);
    }
  }

  @Override
  public void resolveEnd(GameContext context) {
    for (Card charr : TargetResolver.getCharactersFromTarget(firstSettingTarget, context)) {
      Integer newValue = charr.getIntSetting(firstSetting) + valueModifier;
      charr.setIntSetting(firstSetting, newValue);
      log.debug("Effect " + this.toString() + " has calculated " + charr.getDescription()
          + " setting value to " + newValue);
    }
    for (Card charr : TargetResolver.getCharactersFromTarget(secondSettingTarget, context)) {
      Integer newValue = charr.getIntSetting(secondSetting) - valueModifier;
      charr.setIntSetting(secondSetting, newValue);
      log.debug("Effect " + this.toString() + " has calculated " + charr.getDescription()
          + " setting value to " + newValue);
    }
  }

  @Override
  public String toString() {
    return "SettingTransferEffect [firstSetting=" + firstSetting + ", firstSettingTarget="
        + firstSettingTarget + ", valueModifier=" + valueModifier + ", secondSetting="
        + secondSetting + ", secondSettingTarget=" + secondSettingTarget + "]";
  }
}
