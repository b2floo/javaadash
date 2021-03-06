package com.javaadash.tc2.core.card.condition;

import java.util.Collections;

import junit.framework.TestCase;

import com.javaadash.tc2.core.GameUtils;
import com.javaadash.tc2.core.board.CardLocation;
import com.javaadash.tc2.core.card.Card;
import com.javaadash.tc2.core.card.CardType;
import com.javaadash.tc2.core.card.condition.CharacterSettingCondition.Operator;
import com.javaadash.tc2.core.card.effect.Target;
import com.javaadash.tc2.core.context.GameContext;
import com.javaadash.tc2.core.exceptions.TC2CoreException;
import com.javaadash.tc2.core.interfaces.player.Player;

public class CharacterSettingConditionTest extends TestCase {
  private GameContext context;
  private Card c;
  private static String settingName = "LIFE";

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
        new CharacterSettingCondition(settingName, Operator.GREATER, "2", Target.SELF);

    c.setIntSetting(settingName, 3);
    assertTrue(condition.isFulfilled(context));

    c.setIntSetting(settingName, 2);
    assertFalse(condition.isFulfilled(context));
  }

  public void testGreaterEquals() {
    CharacterSettingCondition condition =
        new CharacterSettingCondition(settingName, Operator.GREATER_EQUALS, "2", Target.SELF);

    c.setIntSetting(settingName, 3);
    assertTrue(condition.isFulfilled(context));

    c.setIntSetting(settingName, 2);
    assertTrue(condition.isFulfilled(context));

    c.setIntSetting(settingName, 1);
    assertFalse(condition.isFulfilled(context));
  }

  public void testEquals() {
    CharacterSettingCondition condition =
        new CharacterSettingCondition(settingName, Operator.EQUALS, "2", Target.SELF);

    c.setIntSetting(settingName, 3);
    assertFalse(condition.isFulfilled(context));

    c.setIntSetting(settingName, 2);
    assertTrue(condition.isFulfilled(context));

    c.setIntSetting(settingName, 1);
    assertFalse(condition.isFulfilled(context));

    c.setIntSetting(settingName, 22);
    assertFalse(condition.isFulfilled(context));
  }

  @SuppressWarnings("unchecked")
  public void testEqualsWithMultiValuedStaticSetting() throws TC2CoreException {
    c =
        new Card("123", CardType.CHARACTER, "JUNIT", null, null, Collections.singletonMap("CLASS",
            "MARAUDER, PRIEST"), Collections.EMPTY_MAP);
    Player p = GameUtils.getPlayer("junit", 1, 0, 0, c);

    context = new GameContext(p, new Player());
    context.setCurrentPlayer(p);
    p.getIngameDeck().setCardLocation(c, CardLocation.BOARD);

    CharacterSettingCondition condition =
        new CharacterSettingCondition("CLASS", Operator.EQUALS, "MARAUDER", Target.SELF);

    assertTrue(condition.isFulfilled(context));
  }

  public void testLessEquals() {
    CharacterSettingCondition condition =
        new CharacterSettingCondition(settingName, Operator.LESS_EQUALS, "2", Target.SELF);

    c.setIntSetting(settingName, 3);
    assertFalse(condition.isFulfilled(context));

    c.setIntSetting(settingName, 2);
    assertTrue(condition.isFulfilled(context));

    c.setIntSetting(settingName, 1);
    assertTrue(condition.isFulfilled(context));
  }

  public void testLess() {
    CharacterSettingCondition condition =
        new CharacterSettingCondition(settingName, Operator.LESS, "2", Target.SELF);

    c.setIntSetting(settingName, 3);
    assertFalse(condition.isFulfilled(context));

    c.setIntSetting(settingName, 2);
    assertFalse(condition.isFulfilled(context));

    c.setIntSetting(settingName, 1);
    assertTrue(condition.isFulfilled(context));
  }

  // TODO add multiple characters target tests
}
