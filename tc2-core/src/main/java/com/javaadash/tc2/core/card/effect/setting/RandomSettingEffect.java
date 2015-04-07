package com.javaadash.tc2.core.card.effect.setting;

import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.javaadash.tc2.core.card.Card;
import com.javaadash.tc2.core.card.effect.CardEffectLog;
import com.javaadash.tc2.core.card.effect.Effect;
import com.javaadash.tc2.core.card.effect.SettingChange;
import com.javaadash.tc2.core.card.effect.Target;
import com.javaadash.tc2.core.card.effect.TargetResolver;
import com.javaadash.tc2.core.context.GameContext;

public class RandomSettingEffect implements Effect {
  private static final long serialVersionUID = 8132787058089852159L;
  private static final Logger log = LoggerFactory.getLogger(SettingActiveEffect.class);

  private String setting;
  private Target target;
  private String storedValue;

  public RandomSettingEffect(String setting, Target target) {
    this.setting = setting;
    this.target = target;
  }

  public void resolve(GameContext context, CardEffectLog cardEffectLog) {
    for (Card charr : TargetResolver.getCharactersFromTarget(target, context)) {
      storedValue = charr.getSetting(setting);
      Integer min = Integer.parseInt(storedValue.substring(0, storedValue.indexOf("/")));
      Integer max = Integer.parseInt(storedValue.substring(storedValue.indexOf("/") + 1));

      Random rand = new Random();
      int randomValue = rand.nextInt((max - min) + 1) + min;

      log.debug("Effect " + this + " has calculated setting value " + storedValue + " to "
          + randomValue);
      charr.setIntSetting(setting, randomValue);

      SettingChange settingChange =
          new SettingChange(charr.getId(), charr.getDescription(), setting,
              Integer.toString(randomValue), "=" + randomValue);
      cardEffectLog.getSettingChanges().add(settingChange);
    }
  }

  public void resolveEnd(GameContext context) {
    // TODO sometimes might not be activated!!!
    log.debug("Effect {} need to be deactivated", this);
    for (Card charr : TargetResolver.getCharactersFromTarget(target, context)) {
      charr.setSetting(setting, storedValue);
    }
  }

  @Override
  public String toString() {
    return "RandomSettingEffect [setting=" + setting + ", target=" + target + "]";
  }
}