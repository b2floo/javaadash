package com.javaadash.tc2.core.card.effect.character.setting.modification;

import junit.framework.TestCase;

import com.javaadash.tc2.core.GameUtils;
import com.javaadash.tc2.core.board.CardLocation;
import com.javaadash.tc2.core.card.Card;
import com.javaadash.tc2.core.card.CardType;
import com.javaadash.tc2.core.card.effect.CardEffectLog;
import com.javaadash.tc2.core.card.effect.Effect;
import com.javaadash.tc2.core.card.effect.SettingChange;
import com.javaadash.tc2.core.card.effect.Target;
import com.javaadash.tc2.core.context.GameContext;
import com.javaadash.tc2.core.interfaces.player.Player;

public class CharacterSettingModificationEffectTest extends TestCase {
  private int initialSetting = 4;
  private int modifier = -1;
  private static String setting = "DEF";

  public void testResolve() throws Exception {
    Effect effect = new CharacterSettingModificationEffect(setting, modifier, Target.SELF);

    // set the characters setting and put it on board
    Player startPlayer = GameUtils.getPlayer("junit1", 1, 0, 0);
    Card targetCharacter =
        startPlayer.getIngameDeck().getCard(CardType.CHARACTER, CardLocation.HAND);
    targetCharacter.setIntSetting(setting, initialSetting);
    startPlayer.getIngameDeck().setCardLocation(targetCharacter, CardLocation.BOARD);

    Player nextPlayer = GameUtils.getPlayer("junit2", 1, 0, 0);

    GameContext context = new GameContext(startPlayer, nextPlayer);
    context.setCurrentPlayer(startPlayer);
    CardEffectLog effectDescription = new CardEffectLog(123, "junit Test");
    effect.resolve(context, effectDescription);

    assertEquals(initialSetting + modifier, targetCharacter.getIntSetting(setting));
    // check description of settingChange is correctly filled
    assertEquals(1, effectDescription.getSettingChanges().size());
    SettingChange settingChange = effectDescription.getSettingChanges().get(0);
    assertEquals(setting, settingChange.getSetting());
    assertEquals(Integer.toString(initialSetting + modifier), settingChange.getNewValue());
    assertEquals(Integer.toString(modifier), settingChange.getDiff());
    assertEquals(targetCharacter.getId(), settingChange.getCharacterId());
  }

  public void testResolveEnd() throws Exception {
    Effect effect = new CharacterSettingModificationEffect(setting, modifier, Target.SELF);
    // set the characters setting and put it on board
    Player startPlayer = GameUtils.getPlayer("junit1", 1, 0, 0);
    Card targetCharacter =
        startPlayer.getIngameDeck().getCard(CardType.CHARACTER, CardLocation.HAND);
    targetCharacter.setIntSetting(setting, initialSetting + modifier);
    startPlayer.getIngameDeck().setCardLocation(targetCharacter, CardLocation.BOARD);


    Player nextPlayer = GameUtils.getPlayer("junit2", 1, 0, 0);

    GameContext context = new GameContext(startPlayer, nextPlayer);
    context.setCurrentPlayer(startPlayer);
    effect.resolveEnd(context);

    assertEquals(initialSetting, targetCharacter.getIntSetting(setting));
  }
}
