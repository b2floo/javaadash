package com.javaadash.tc2.core.card.effect.setting;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.javaadash.tc2.core.card.Card;
import com.javaadash.tc2.core.card.RangeValue;
import com.javaadash.tc2.core.card.effect.Effect;
import com.javaadash.tc2.core.card.effect.Target;
import com.javaadash.tc2.core.card.effect.TargetResolver;
import com.javaadash.tc2.core.card.effect.log.CardEffectLog;
import com.javaadash.tc2.core.card.effect.log.SettingChange;
import com.javaadash.tc2.core.context.GameContext;

public class DismembermentEffect implements Effect {
  private static final long serialVersionUID = 2475414060507759124L;
  private static final Logger log = LoggerFactory.getLogger(DismembermentEffect.class);

  @Override
  public void resolve(GameContext context, CardEffectLog cardEffectLog) {
    for (Card charr : TargetResolver.getCharactersFromTarget(Target.ALL, context)) {

      log.debug("DismembermentEffect is calculating effect on character {}",
          new Object[] {charr.getDescription()});

      String classSetting = charr.getSetting("CLASS");
      int classCount = classSetting.split(",").length;

      RangeValue newLifeValue = charr.getIntSetting("LIFE").remove(classCount);
      charr.setIntSetting("LIFE", newLifeValue);

      log.debug("DismembermentEffect removed {} life points on character {}", new Object[] {
          newLifeValue, charr.getDescription()});

      SettingChange settingChange =
          new SettingChange(charr.getId(), charr.getDescription(), "LIFE");
      settingChange.setNewValue(newLifeValue.getDescription());
      settingChange.setDiff("-" + classCount);
      cardEffectLog.getSettingChanges().add(settingChange);
    }
  }

  @Override
  public void resolveEnd(GameContext context) {
    // irreversible
  }

  @Override
  public String toString() {
    return "DismembermentEffect";
  }
}
