package com.javaadash.tc2.core.card.effect.setting;

import junit.framework.TestCase;

import com.javaadash.tc2.core.GameUtils;
import com.javaadash.tc2.core.board.CardLocation;
import com.javaadash.tc2.core.card.Card;
import com.javaadash.tc2.core.card.CardType;
import com.javaadash.tc2.core.card.RangeValue;
import com.javaadash.tc2.core.card.effect.Effect;
import com.javaadash.tc2.core.card.effect.Target;
import com.javaadash.tc2.core.card.effect.log.CardEffectLog;
import com.javaadash.tc2.core.card.effect.log.SettingChange;
import com.javaadash.tc2.core.card.effect.setting.CharacterSettingModificationEffect.SettingPart;
import com.javaadash.tc2.core.context.GameContext;
import com.javaadash.tc2.core.interfaces.player.Player;

public class CharacterSettingModificationEffectTest extends TestCase {

  public void testResolve() throws Exception {
    Effect effect = new CharacterSettingModificationEffect("DEF", "-1", Target.SELF);

    // set the characters setting and put it on board
    Player startPlayer = GameUtils.getPlayer("junit1", 1, 0, 0);
    Card targetCharacter =
        startPlayer.getIngameDeck().getCard(CardType.CHARACTER, CardLocation.HAND);
    targetCharacter.setIntSetting("DEF", 4);
    startPlayer.getIngameDeck().setCardLocation(targetCharacter, CardLocation.BOARD);

    Player nextPlayer = GameUtils.getPlayer("junit2", 1, 0, 0);

    GameContext context = new GameContext(startPlayer, nextPlayer);
    context.setCurrentPlayer(startPlayer);
    CardEffectLog effectDescription = new CardEffectLog(123, "junit Test");
    effect.resolve(context, effectDescription);

    assertEquals(Integer.valueOf(3), targetCharacter.getIntSetting("DEF").getMin());
    assertEquals(Integer.valueOf(3), targetCharacter.getIntSetting("DEF").getMax());
    // check description of settingChange is correctly filled
    assertEquals(1, effectDescription.getSettingChanges().size());
    SettingChange settingChange = effectDescription.getSettingChanges().get(0);
    assertEquals("DEF", settingChange.getSetting());
    assertEquals("3", settingChange.getNewValue());
    assertEquals("-1", settingChange.getDiff());
    assertEquals(targetCharacter.getId(), settingChange.getCharacterId());
  }

  public void testResolvePartial() throws Exception {
    Effect effect =
        new CharacterSettingModificationEffect("DEF", "-1", Target.SELF, SettingPart.MIN);

    // set the characters setting and put it on board
    Player startPlayer = GameUtils.getPlayer("junit1", 1, 0, 0);
    Card targetCharacter =
        startPlayer.getIngameDeck().getCard(CardType.CHARACTER, CardLocation.HAND);
    targetCharacter.setIntSetting("DEF", 4);
    startPlayer.getIngameDeck().setCardLocation(targetCharacter, CardLocation.BOARD);

    Player nextPlayer = GameUtils.getPlayer("junit2", 1, 0, 0);

    GameContext context = new GameContext(startPlayer, nextPlayer);
    context.setCurrentPlayer(startPlayer);
    CardEffectLog effectDescription = new CardEffectLog(123, "junit Test");
    effect.resolve(context, effectDescription);

    assertEquals(Integer.valueOf(3), targetCharacter.getIntSetting("DEF").getMin());
    assertEquals(Integer.valueOf(4), targetCharacter.getIntSetting("DEF").getMax());

    // check description of settingChange is correctly filled
    assertEquals(1, effectDescription.getSettingChanges().size());
    SettingChange settingChange = effectDescription.getSettingChanges().get(0);
    assertEquals("DEF", settingChange.getSetting());
    assertEquals("3/4", settingChange.getNewValue());
    assertEquals("-1/0", settingChange.getDiff());
    assertEquals(targetCharacter.getId(), settingChange.getCharacterId());
  }

  public void testResolveEnd() throws Exception {

    Effect effect = new CharacterSettingModificationEffect("DEF", "-1", Target.SELF);

    // set the characters setting and put it on board
    Player startPlayer = GameUtils.getPlayer("junit1", 1, 0, 0);
    Card targetCharacter =
        startPlayer.getIngameDeck().getCard(CardType.CHARACTER, CardLocation.HAND);
    targetCharacter.setIntSetting("DEF", 4);
    startPlayer.getIngameDeck().setCardLocation(targetCharacter, CardLocation.BOARD);


    Player nextPlayer = GameUtils.getPlayer("junit2", 1, 0, 0);

    GameContext context = new GameContext(startPlayer, nextPlayer);
    context.setCurrentPlayer(startPlayer);
    effect.resolve(context, new CardEffectLog(123, ""));
    effect.resolveEnd(context);

    assertEquals(Integer.valueOf(4), targetCharacter.getIntSetting("DEF").getMin());
    assertEquals(Integer.valueOf(4), targetCharacter.getIntSetting("DEF").getMax());
  }


  public void testResolvePartialMinGreaterThanMax() throws Exception {

    Effect effect =
        new CharacterSettingModificationEffect("DEF", "3", Target.SELF, SettingPart.MIN);

    // set the characters setting and put it on board
    Player startPlayer = GameUtils.getPlayer("junit1", 1, 0, 0);
    Card targetCharacter =
        startPlayer.getIngameDeck().getCard(CardType.CHARACTER, CardLocation.HAND);
    targetCharacter.setIntSetting("DEF", new RangeValue("4/5"));
    startPlayer.getIngameDeck().setCardLocation(targetCharacter, CardLocation.BOARD);

    Player nextPlayer = GameUtils.getPlayer("junit2", 1, 0, 0);

    GameContext context = new GameContext(startPlayer, nextPlayer);
    context.setCurrentPlayer(startPlayer);
    CardEffectLog effectDescription = new CardEffectLog(123, "junit Test");
    effect.resolve(context, effectDescription);

    assertEquals(Integer.valueOf(7), targetCharacter.getIntSetting("DEF").getMin());
    assertEquals(Integer.valueOf(7), targetCharacter.getIntSetting("DEF").getMax());

    // check description of settingChange is correctly filled
    assertEquals(1, effectDescription.getSettingChanges().size());
    SettingChange settingChange = effectDescription.getSettingChanges().get(0);
    assertEquals("DEF", settingChange.getSetting());
    assertEquals("7", settingChange.getNewValue());
    assertEquals("+3/0", settingChange.getDiff());
    assertEquals(targetCharacter.getId(), settingChange.getCharacterId());

    effect.resolveEnd(context);

    assertEquals(Integer.valueOf(4), targetCharacter.getIntSetting("DEF").getMin());
    assertEquals(Integer.valueOf(5), targetCharacter.getIntSetting("DEF").getMax());
  }


  public void testResolvePartialMinGreaterThanMax_WithRange() throws Exception {

    Effect effect =
        new CharacterSettingModificationEffect("DEF", "3/4", Target.SELF, SettingPart.MIN);

    // set the characters setting and put it on board
    Player startPlayer = GameUtils.getPlayer("junit1", 1, 0, 0);
    Card targetCharacter =
        startPlayer.getIngameDeck().getCard(CardType.CHARACTER, CardLocation.HAND);
    targetCharacter.setIntSetting("DEF", new RangeValue("4/5"));
    startPlayer.getIngameDeck().setCardLocation(targetCharacter, CardLocation.BOARD);

    Player nextPlayer = GameUtils.getPlayer("junit2", 1, 0, 0);

    GameContext context = new GameContext(startPlayer, nextPlayer);
    context.setCurrentPlayer(startPlayer);
    CardEffectLog effectDescription = new CardEffectLog(123, "junit Test");
    effect.resolve(context, effectDescription);

    assertTrue(Integer.valueOf(7).equals(targetCharacter.getIntSetting("DEF").getMin())
        || Integer.valueOf(8).equals(targetCharacter.getIntSetting("DEF").getMin()));
    assertTrue(Integer.valueOf(7).equals(targetCharacter.getIntSetting("DEF").getMax())
        || Integer.valueOf(8).equals(targetCharacter.getIntSetting("DEF").getMax()));

    // check description of settingChange is correctly filled
    assertEquals(1, effectDescription.getSettingChanges().size());
    SettingChange settingChange = effectDescription.getSettingChanges().get(0);
    assertTrue("7".equals(settingChange.getNewValue()) || "8".equals(settingChange.getNewValue()));
    assertTrue("+3/0".equals(settingChange.getDiff()) || "+4/0".equals(settingChange.getDiff()));

    effect.resolveEnd(context);

    assertEquals(Integer.valueOf(4), targetCharacter.getIntSetting("DEF").getMin());
    assertEquals(Integer.valueOf(5), targetCharacter.getIntSetting("DEF").getMax());
  }
}
