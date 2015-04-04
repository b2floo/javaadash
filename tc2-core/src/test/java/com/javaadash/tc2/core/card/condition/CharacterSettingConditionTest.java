package com.javaadash.tc2.core.card.condition;

import junit.framework.TestCase;

import com.javaadash.tc2.core.GameUtils;
import com.javaadash.tc2.core.board.CardLocation;
import com.javaadash.tc2.core.card.Card;
import com.javaadash.tc2.core.card.CardType;
import com.javaadash.tc2.core.card.condition.CharacterSettingCondition.Sign;
import com.javaadash.tc2.core.card.effect.Target;
import com.javaadash.tc2.core.context.GameContext;
import com.javaadash.tc2.core.interfaces.player.Player;

public class CharacterSettingConditionTest extends TestCase {
  private GameContext context;
  private Card c;

  @Override
  protected void setUp() throws Exception {
    // create the context
    Player p = GameUtils.getPlayer("junit", 1, 0, 0);
    context = new GameContext(p, new Player());
    // add the player to context
    context.setCurrentPlayer(p);

    // put the character on board
    c = p.getIngameDeck().getCard(CardType.CHARACTER, CardLocation.HAND);
    p.getIngameDeck().setCardLocation(c, CardLocation.BOARD);
  }

  public void testGreater() {
    CharacterSettingCondition condition =
        new CharacterSettingCondition(settingName, Sign.GREATER, "2", Target.SELF);

    c.setIntSetting(settingName, 3);
    assertTrue(condition.isFulfilled(context));

    c.setIntSetting(settingName, 2);
    assertFalse(condition.isFulfilled(context));
  }

  public void testGreaterEquals() {
    CharacterSettingCondition condition =
        new CharacterSettingCondition(settingName, Sign.GREATER_EQUALS, "2", Target.SELF);

    c.setIntSetting(settingName, 3);
    assertTrue(condition.isFulfilled(context));

    c.setIntSetting(settingName, 2);
    assertTrue(condition.isFulfilled(context));

    c.setIntSetting(settingName, 1);
    assertFalse(condition.isFulfilled(context));
  }

  public void testEquals() {
    CharacterSettingCondition condition =
        new CharacterSettingCondition(settingName, Sign.EQUALS, "2", Target.SELF);

    c.setIntSetting(settingName, 3);
    assertFalse(condition.isFulfilled(context));

    c.setIntSetting(settingName, 2);
    assertTrue(condition.isFulfilled(context));

    c.setIntSetting(settingName, 1);
    assertFalse(condition.isFulfilled(context));
  }

  public void testLessEquals() {
    CharacterSettingCondition condition =
        new CharacterSettingCondition(settingName, Sign.LESS_EQUALS, "2", Target.SELF);

    c.setIntSetting(settingName, 3);
    assertFalse(condition.isFulfilled(context));

    c.setIntSetting(settingName, 2);
    assertTrue(condition.isFulfilled(context));

    c.setIntSetting(settingName, 1);
    assertTrue(condition.isFulfilled(context));
  }

  public void testLess() {
    CharacterSettingCondition condition =
        new CharacterSettingCondition(settingName, Sign.LESS, "2", Target.SELF);

    c.setIntSetting(settingName, 3);
    assertFalse(condition.isFulfilled(context));

    c.setIntSetting(settingName, 2);
    assertFalse(condition.isFulfilled(context));

    c.setIntSetting(settingName, 1);
    assertTrue(condition.isFulfilled(context));
  }

  // TODO add multiple characters target tests

  private static String settingName = "LIFE";

}
