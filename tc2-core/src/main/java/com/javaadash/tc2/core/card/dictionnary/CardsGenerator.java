package com.javaadash.tc2.core.card.dictionnary;

import java.util.Collection;
import java.util.HashSet;

import com.javaadash.tc2.core.card.condition.CharacterSettingCondition;
import com.javaadash.tc2.core.card.condition.CharacterSettingCondition.Sign;
import com.javaadash.tc2.core.card.condition.Condition;
import com.javaadash.tc2.core.card.effect.Effect;
import com.javaadash.tc2.core.card.effect.Target;
import com.javaadash.tc2.core.card.effect.character.setting.modification.CharacterSettingModificationEffect;
import com.javaadash.tc2.core.card.effect.character.setting.modification.IrreversibleCharacterSettingModificationEffect;
import com.javaadash.tc2.core.card.effect.turn.TurnActiveEffect;

public class CardsGenerator {

  public Collection<Effect> getEffets(int number) {
    HashSet<Effect> effects = new HashSet<Effect>();
    for (int i = 0; i < number; i++) {
      int random = (int) (Math.random() * 11);
      HashSet<Integer> turns = new HashSet<Integer>();
      turns.add(random);
      turns.add(1);

      switch (random) {
        case 0:
          effects.add(new CharacterSettingModificationEffect("DEF", 1, Target.SELF));
          break;
        case 1:
          effects.add(new CharacterSettingModificationEffect("ATT", 1, Target.SELF));
          break;
        case 2:
          effects.add(new CharacterSettingModificationEffect("DEF", -1, Target.OPPONENT));
          break;
        case 3:
          effects.add(new CharacterSettingModificationEffect("ATT", -1, Target.OPPONENT));
          break;
        case 4:
          effects.add(new CharacterSettingModificationEffect("DEF", 1, Target.SELF));
          break;
        case 5:
          effects.add(new CharacterSettingModificationEffect("DEF", 2, Target.SELF));
          break;
        case 6:
          effects.add(new TurnActiveEffect(new CharacterSettingModificationEffect("DEF", 1,
              Target.SELF), turns));
          break;
        case 7:
          effects.add(new TurnActiveEffect(new CharacterSettingModificationEffect("ATT", 1,
              Target.SELF), turns));
          break;
        case 8:
          effects.add(new TurnActiveEffect(new CharacterSettingModificationEffect("DEF", -2,
              Target.OPPONENT), turns));
          break;
        case 9:
          effects.add(new IrreversibleCharacterSettingModificationEffect("LIFE", -2,
              Target.OPPONENT));
          break;
        case 10:
          effects.add(new IrreversibleCharacterSettingModificationEffect("LIFE", -2,
              Target.OPPONENT));
          break;
        case 11:
          effects.add(new IrreversibleCharacterSettingModificationEffect("LIFE", -2,
              Target.OPPONENT));
          break;
      }
    }
    return effects;
  }

  public Collection<Condition> getConditions() {
    HashSet<Condition> conditions = new HashSet<Condition>();

    if (Math.random() > 0.5) {
      int random = (int) (Math.random() * 11);

      switch (random) {
        case 0:
          conditions.add(new CharacterSettingCondition("DEF", Sign.GREATER, "2", Target.SELF));
          break;
        case 1:
          conditions.add(new CharacterSettingCondition("LIFE", Sign.GREATER, "4", Target.SELF));
          break;
        case 2:
          conditions.add(new CharacterSettingCondition("LIFE", Sign.LESS_EQUALS, "3", Target.SELF));
          break;
        case 3:
          conditions.add(new CharacterSettingCondition("GUILD", Sign.EQUALS, "NOZ", Target.SELF));
          break;
        case 4:
          conditions
              .add(new CharacterSettingCondition("CLASS", Sign.EQUALS, "WARRIOR", Target.SELF));
          break;
        case 5:
          conditions
              .add(new CharacterSettingCondition("CLASS", Sign.EQUALS, "PRIEST", Target.SELF));
          break;
      }
    }

    return conditions;
  }
}
