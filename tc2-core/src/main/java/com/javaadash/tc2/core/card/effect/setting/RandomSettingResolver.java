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

/**
 * Resolver that is linked to a setting which has a min and a max value. When resolving effect a
 * value is randomly determined between min and max, and is applied to the setting.
 * 
 * @author b2floo
 * 
 */
public class RandomSettingResolver implements Effect {
  private static final long serialVersionUID = 376345004963828057L;
  private static final Logger log = LoggerFactory.getLogger(RandomSettingResolver.class);

  private String setting;
  private Target target;
  private Map<Card, RangeValue> storedValues = new HashMap<Card, RangeValue>();

  public RandomSettingResolver(String setting, Target target) {
    this.setting = setting;
    this.target = target;
  }

  @Override
  public void resolve(GameContext context, CardEffectLog cardEffectLog) {
    storedValues.clear();
    for (Card charr : TargetResolver.getCharactersFromTarget(target, context)) {

      RangeValue currentSetting = charr.getIntSetting(setting);


      if (currentSetting.getMin() == currentSetting.getMax()) {
        log.debug("Effect {} : no random setting value is {}",
            new Object[] {this, charr.getIntSetting(setting)});
      } else {
        storedValues.put(charr, currentSetting);
        RandomRangeValue val = new RandomRangeValue(currentSetting);
        int randomValue = val.getRandom();

        log.debug("Effect {} has calculated setting value {} to {}",
            new Object[] {this, charr.getIntSetting(setting), randomValue});
        charr.setIntSetting(setting, randomValue);

        SettingChange settingChange =
            new SettingChange(charr.getId(), charr.getDescription(), setting,
                Integer.toString(randomValue), "=" + randomValue);
        cardEffectLog.getSettingChanges().add(settingChange);
      }
    }
  }

  @Override
  public void resolveEnd(GameContext context) {
    log.debug("Effect {} need to be deactivated", this);
    for (Card charr : storedValues.keySet()) {
      charr.setIntSetting(setting, storedValues.get(charr));
    }
  }

  @Override
  public String toString() {
    return "RandomSettingResolver [setting=" + setting + ", target=" + target + "]";
  }
}
