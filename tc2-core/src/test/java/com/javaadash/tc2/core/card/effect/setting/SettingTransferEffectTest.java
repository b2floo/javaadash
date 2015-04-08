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
  private int lifeValue = 10;
  private int attckValue = 5;

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
    assertEquals(attckValue + Integer.parseInt(modifier), char1.getIntSetting("ATT"));
    assertEquals(lifeValue - Integer.parseInt(modifier), char1.getIntSetting("LIFE"));

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
    assertEquals(attckValue + Integer.parseInt(modifier), char1.getIntSetting("ATT"));
    assertEquals(lifeValue - Integer.parseInt(modifier), char1.getIntSetting("LIFE"));

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
    assertEquals(attckValue, char1.getIntSetting("ATT"));
    assertEquals(lifeValue, char1.getIntSetting("LIFE"));
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

    assertTrue(char1.getIntSetting("LIFE") >= lifeValue - 4);
    assertTrue(char1.getIntSetting("LIFE") <= lifeValue - 1);
    assertTrue(char1.getIntSetting("ATT") >= attckValue + 1);
    assertTrue(char1.getIntSetting("ATT") <= attckValue + 4);

    assertEquals(2, cardEffectLog.getSettingChanges().size());

    effect.resolveEnd(context);
    assertEquals(attckValue, char1.getIntSetting("ATT"));
    assertEquals(lifeValue, char1.getIntSetting("LIFE"));
  }
}
