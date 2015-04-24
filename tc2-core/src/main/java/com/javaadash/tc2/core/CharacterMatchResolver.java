package com.javaadash.tc2.core;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.javaadash.tc2.core.card.Card;
import com.javaadash.tc2.core.card.RangeValue;
import com.javaadash.tc2.core.card.effect.damage.DamageModifier;
import com.javaadash.tc2.core.card.effect.log.CardEffectLog;
import com.javaadash.tc2.core.card.effect.log.SettingChange;
import com.javaadash.tc2.core.interfaces.player.Player;

public class CharacterMatchResolver {
  /**
   * Resolve a match between 2 characters. First character is the attacker, second one is the
   * defender. First player's score is updated depending on damages.
   * 
   * @param char1 Attacking character
   * @param p1 Attacking player
   * @param char2 Defending character
   */
  public void resolveCharacterMatch(Card char1, Player p1, Card char2,
      List<CardEffectLog> cardEffectLogs) {
    // calculate life loss
    int damage = char1.getIntSetting("ATT").getMin() - char2.getIntSetting("DEF").getMin();

    CardEffectLog cardEffectLog =
        new CardEffectLog(char1.getId(), "Fight: " + char1.getDescription() + " attacks "
            + char2.getDescription());
    cardEffectLogs.add(cardEffectLog);

    SettingChange settingChange = new SettingChange(char2.getId(), char2.getDescription(), "LIFE");
    cardEffectLog.getSettingChanges().add(settingChange);

    int tmpLifeCount = char2.getIntSetting("LIFE").getMin();
    if (damage <= 0) {
      log.info("Damage to {} : null", char2);
      settingChange.setDiff("0");
      settingChange.setNewValue(Integer.toString(tmpLifeCount));
    } else {
      log.info("Damage to {} : {}", char2, damage);
      tmpLifeCount = tmpLifeCount - damage;
      log.info("Life left : {}", tmpLifeCount);
      settingChange.setDiff("-" + (damage));
      settingChange.setNewValue(Integer.toString(tmpLifeCount));
    }

    // Call DamageModifiers
    for (DamageModifier damageModifier : char2.getDamageModifiers()) {

      cardEffectLog =
          new CardEffectLog(-1, "Activating " + damageModifier.getCardName() + " effects");
      cardEffectLogs.add(cardEffectLog);

      int modifierValue = damageModifier.getValue(damage);
      // call modifier only if damage suffered or modifier add damages
      if (modifierValue > 0 || damage > 0) {
        damage += modifierValue;
        updateDamageLogs(modifierValue, char2, tmpLifeCount, cardEffectLog);
        tmpLifeCount = tmpLifeCount - damage;
      } else {
        updateDamageLogs(0, char2, tmpLifeCount, cardEffectLog);
      }
    }

    if (damage > 0) {
      RangeValue life2 = char2.getIntSetting("LIFE").remove(damage);
      char2.setIntSetting("LIFE", life2);
      p1.setScore(p1.getScore() + damage);
    }
  }

  public void updateDamageLogs(int damage, Card char2, int tmpLifeCount, CardEffectLog cardEffectLog) {

    SettingChange settingChange = new SettingChange(char2.getId(), char2.getDescription(), "LIFE");
    cardEffectLog.getSettingChanges().add(settingChange);

    log.info("Damage to {} : {}", char2, damage);
    tmpLifeCount = tmpLifeCount - damage;
    log.info("Life left : {}", tmpLifeCount);
    settingChange.setDiff(damage > 0 ? "-" + damage : "+" + (-damage));
    settingChange.setNewValue(Integer.toString(tmpLifeCount));
  }

  private Logger log = LoggerFactory.getLogger(CharacterMatchResolver.class);
}
