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
 * Effect that is linked to a setting which has a min and a max value. When resolving effect a value
 * is randomly determined between min and max, and is applied to the setting.
 * 
 * @author b2floo
 *
 */
public class RandomSettingEffect implements Effect {
  private static final long serialVersionUID = 376345004963828057L;
  private static final Logger log = LoggerFactory.getLogger(RandomSettingEffect.class);

  private String setting;
  private Target target;
  private Map<Card, RandomRangeValue> storedValues = new HashMap<Card, RandomRangeValue>();

  public RandomSettingEffect(String setting, Target target) {
    this.setting = setting;
    this.target = target;
  }

  @Override
  public void resolve(GameContext context, CardEffectLog cardEffectLog) {
    storedValues.clear();
    for (Card charr : TargetResolver.getCharactersFromTarget(target, context)) {

      RandomRangeValue val = new RandomRangeValue(charr.getSetting(setting));
      storedValues.put(charr, val);
      int randomValue = val.getRandom();

      log.debug("Effect " + this + " has calculated setting value " + charr.getSetting(setting)
          + " to " + randomValue);
      charr.setIntSetting(setting, randomValue);

      SettingChange settingChange =
          new SettingChange(charr.getId(), charr.getDescription(), setting,
              Integer.toString(randomValue), "=" + randomValue);
      cardEffectLog.getSettingChanges().add(settingChange);
    }
  }

  @Override
  public void resolveEnd(GameContext context) {
    log.debug("Effect {} need to be deactivated", this);
    for (Card charr : storedValues.keySet()) {
      charr.setSetting(setting, storedValues.get(charr).getDescription());
    }
  }

  @Override
  public String toString() {
    return "RandomSettingEffect [setting=" + setting + ", target=" + target + "]";
  }
}
