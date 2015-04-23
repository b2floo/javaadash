package com.javaadash.tc2.core.card.effect.setting;

import junit.framework.TestCase;

import com.javaadash.tc2.core.GameUtils;
import com.javaadash.tc2.core.board.CardLocation;
import com.javaadash.tc2.core.card.Card;
import com.javaadash.tc2.core.card.CardType;
import com.javaadash.tc2.core.card.effect.CardEffectLog;
import com.javaadash.tc2.core.card.effect.SettingChange;
import com.javaadash.tc2.core.card.effect.Target;
import com.javaadash.tc2.core.context.GameContext;
import com.javaadash.tc2.core.exceptions.TC2CoreException;
import com.javaadash.tc2.core.interfaces.player.Player;

public class SettingTransferEffectTest extends TestCase {

  private SettingTransferEffect effect;
  private String modifier = "2";
  private Integer lifeValue = 10;
  private Integer attckValue = 5;

  @Override
  protected void setUp() throws Exception {
    effect = new SettingTransferEffect("LIFE", Target.SELF, modifier, "ATT", Target.SELF);
  }

  public void testResolve() throws TC2CoreException {
    Player p1 = GameUtils.getPlayer("Junit1", 2, 0, 0);
    Card char1 = p1.getIngameDeck().getCard(CardType.CHARACTER, CardLocation.HAND);
    p1.getIngameDeck().setCardLocation(char1, CardLocation.BOARD);
    char1.setIntSetting("ATT", attckValue);
    char1.setIntSetting("LIFE", lifeValue);

    Player p2 = GameUtils.getPlayer("Junit2", 2, 0, 0);
    GameContext context = new GameContext(p1, p2);
    context.setCurrentPlayer(p1);

    CardEffectLog cardEffectLog = new CardEffectLog(123, "junit card log");
    effect.resolve(context, cardEffectLog);
    assertEquals(char1.getIntSetting("ATT").getMin(), char1.getIntSetting("ATT").getMax());
    assertEquals(new Integer(attckValue + Integer.parseInt(modifier)), char1.getIntSetting("ATT")
        .getMax());

    assertEquals(char1.getIntSetting("LIFE").getMin(), char1.getIntSetting("LIFE").getMax());
    assertEquals(new Integer(lifeValue - Integer.parseInt(modifier)), char1.getIntSetting("LIFE")
        .getMax());

    assertEquals(2, cardEffectLog.getSettingChanges().size());
    SettingChange settingChange = cardEffectLog.getSettingChanges().get(0);
    assertEquals("LIFE", settingChange.getSetting());
    assertEquals(Integer.toString(lifeValue - Integer.parseInt(modifier)),
        settingChange.getNewValue());
    assertEquals("-2", settingChange.getDiff());
    settingChange = cardEffectLog.getSettingChanges().get(1);
    assertEquals("ATT", settingChange.getSetting());
    assertEquals(Integer.toString(attckValue + Integer.parseInt(modifier)),
        settingChange.getNewValue());
    assertEquals("+2", settingChange.getDiff());
  }


  public void testResolveNegativeModifier() throws TC2CoreException {
    String modifier = "-2";
    effect = new SettingTransferEffect("LIFE", Target.SELF, modifier, "ATT", Target.SELF);

    Player p1 = GameUtils.getPlayer("Junit1", 2, 0, 0);
    Card char1 = p1.getIngameDeck().getCard(CardType.CHARACTER, CardLocation.HAND);
    p1.getIngameDeck().setCardLocation(char1, CardLocation.BOARD);
    char1.setIntSetting("ATT", attckValue);
    char1.setIntSetting("LIFE", lifeValue);

    Player p2 = GameUtils.getPlayer("Junit2", 2, 0, 0);
    GameContext context = new GameContext(p1, p2);
    context.setCurrentPlayer(p1);

    CardEffectLog cardEffectLog = new CardEffectLog(123, "junit card log");
    effect.resolve(context, cardEffectLog);
    assertEquals(char1.getIntSetting("ATT").getMin(), char1.getIntSetting("ATT").getMax());
    assertEquals(new Integer(attckValue + Integer.parseInt(modifier)), char1.getIntSetting("ATT")
        .getMax());

    assertEquals(char1.getIntSetting("LIFE").getMin(), char1.getIntSetting("LIFE").getMax());
    assertEquals(new Integer(lifeValue - Integer.parseInt(modifier)), char1.getIntSetting("LIFE")
        .getMax());

    assertEquals(2, cardEffectLog.getSettingChanges().size());
    SettingChange settingChange = cardEffectLog.getSettingChanges().get(0);
    assertEquals("LIFE", settingChange.getSetting());
    assertEquals(Integer.toString(lifeValue - Integer.parseInt(modifier)),
        settingChange.getNewValue());
    assertEquals("+2", settingChange.getDiff());
    settingChange = cardEffectLog.getSettingChanges().get(1);
    assertEquals("ATT", settingChange.getSetting());
    assertEquals(Integer.toString(attckValue + Integer.parseInt(modifier)),
        settingChange.getNewValue());
    assertEquals("-2", settingChange.getDiff());
  }

  public void testResolveEnd() throws TC2CoreException {
    Player p1 = GameUtils.getPlayer("Junit1", 2, 0, 0);
    Card char1 = p1.getIngameDeck().getCard(CardType.CHARACTER, CardLocation.HAND);
    p1.getIngameDeck().setCardLocation(char1, CardLocation.BOARD);
    char1.setIntSetting("ATT", attckValue);
    char1.setIntSetting("LIFE", lifeValue);

    Player p2 = GameUtils.getPlayer("Junit2", 2, 0, 0);
    GameContext context = new GameContext(p1, p2);
    context.setCurrentPlayer(p1);

    effect.resolve(context, new CardEffectLog(0, ""));
    effect.resolveEnd(context);
    assertEquals(attckValue, char1.getIntSetting("ATT").getMin());
    assertEquals(lifeValue, char1.getIntSetting("LIFE").getMin());
  }

  public void testResolveWithRandom() throws TC2CoreException {
    String modifier = "1/4";
    effect = new SettingTransferEffect("LIFE", Target.SELF, modifier, "ATT", Target.SELF);

    Player p1 = GameUtils.getPlayer("Junit1", 2, 0, 0);
    Card char1 = p1.getIngameDeck().getCard(CardType.CHARACTER, CardLocation.HAND);
    p1.getIngameDeck().setCardLocation(char1, CardLocation.BOARD);
    char1.setIntSetting("ATT", attckValue);
    char1.setIntSetting("LIFE", lifeValue);

    Player p2 = GameUtils.getPlayer("Junit2", 2, 0, 0);
    GameContext context = new GameContext(p1, p2);
    context.setCurrentPlayer(p1);

    CardEffectLog cardEffectLog = new CardEffectLog(123, "junit card log");
    effect.resolve(context, cardEffectLog);

    assertEquals(char1.getIntSetting("LIFE").getMin(), char1.getIntSetting("LIFE").getMax());
    assertTrue(char1.getIntSetting("LIFE").getMin() >= lifeValue - 4);
    assertTrue(char1.getIntSetting("LIFE").getMin() <= lifeValue - 1);
    assertEquals(char1.getIntSetting("ATT").getMin(), char1.getIntSetting("ATT").getMax());
    assertTrue(char1.getIntSetting("ATT").getMin() >= attckValue + 1);
    assertTrue(char1.getIntSetting("ATT").getMin() <= attckValue + 4);

    assertEquals(2, cardEffectLog.getSettingChanges().size());

    effect.resolveEnd(context);
    assertEquals(char1.getIntSetting("LIFE").getMin(), char1.getIntSetting("LIFE").getMax());
    assertEquals(attckValue, char1.getIntSetting("ATT").getMin());
    assertEquals(char1.getIntSetting("ATT").getMin(), char1.getIntSetting("ATT").getMax());
    assertEquals(lifeValue, char1.getIntSetting("LIFE").getMin());
  }
}
