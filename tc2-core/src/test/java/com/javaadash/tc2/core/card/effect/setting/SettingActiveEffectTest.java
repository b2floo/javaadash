package com.javaadash.tc2.core.card.effect.setting;

import junit.framework.TestCase;

import com.javaadash.tc2.core.GameUtils;
import com.javaadash.tc2.core.board.CardLocation;
import com.javaadash.tc2.core.card.Card;
import com.javaadash.tc2.core.card.CardType;
import com.javaadash.tc2.core.card.condition.CharacterSettingCondition;
import com.javaadash.tc2.core.card.condition.CharacterSettingCondition.Operator;
import com.javaadash.tc2.core.card.effect.CardEffectLog;
import com.javaadash.tc2.core.card.effect.MockEffect;
import com.javaadash.tc2.core.card.effect.Target;
import com.javaadash.tc2.core.context.GameContext;
import com.javaadash.tc2.core.exceptions.TC2CoreException;
import com.javaadash.tc2.core.interfaces.player.Player;

public class SettingActiveEffectTest extends TestCase {

  private SettingActiveEffect settingActiveEffect;
  private MockEffect mockEffect = new MockEffect();

  @Override
  protected void setUp() throws Exception {
    settingActiveEffect =
        new SettingActiveEffect(mockEffect, new CharacterSettingCondition("LIFE", Operator.EQUALS,
            "3", Target.SELF));
  }

  public void testResolve() throws TC2CoreException {

    Player p1 = GameUtils.getPlayer("Junit1", 2, 0, 0);
    Card char1 = p1.getIngameDeck().getCard(CardType.CHARACTER, CardLocation.HAND);
    p1.getIngameDeck().setCardLocation(char1, CardLocation.BOARD);
    char1.setSetting("LIFE", "3");

    Player p2 = GameUtils.getPlayer("Junit2", 2, 0, 0);
    GameContext context = new GameContext(p1, p2);
    context.setCurrentPlayer(p1);

    settingActiveEffect.resolve(context, new CardEffectLog(0, ""));
    assertTrue(mockEffect.isResolved());
  }

  public void testEndResolve() throws TC2CoreException {

    Player p1 = GameUtils.getPlayer("Junit1", 2, 0, 0);
    Card char1 = p1.getIngameDeck().getCard(CardType.CHARACTER, CardLocation.HAND);
    p1.getIngameDeck().setCardLocation(char1, CardLocation.BOARD);
    char1.setSetting("LIFE", "3");

    Player p2 = GameUtils.getPlayer("Junit2", 2, 0, 0);
    GameContext context = new GameContext(p1, p2);
    context.setCurrentPlayer(p1);

    settingActiveEffect.resolveEnd(context);
    assertFalse(mockEffect.isEndResolved());

    // only launched when effect was active
    settingActiveEffect.resolve(context, new CardEffectLog(0, ""));
    settingActiveEffect.resolveEnd(context);
    assertTrue(mockEffect.isEndResolved());
  }



}
