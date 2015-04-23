package com.javaadash.tc2.core.card.effect.setting;

import junit.framework.TestCase;

import com.javaadash.tc2.core.GameUtils;
import com.javaadash.tc2.core.board.CardLocation;
import com.javaadash.tc2.core.card.Card;
import com.javaadash.tc2.core.card.CardType;
import com.javaadash.tc2.core.card.effect.CardEffectLog;
import com.javaadash.tc2.core.card.effect.Target;
import com.javaadash.tc2.core.context.GameContext;
import com.javaadash.tc2.core.exceptions.TC2CoreException;
import com.javaadash.tc2.core.interfaces.player.Player;

public class RandomSettingResolverTest extends TestCase {

  private RandomSettingResolver randomEffect;

  @Override
  protected void setUp() throws Exception {
    randomEffect = new RandomSettingResolver("ATT", Target.SELF);
  }

  public void testResolve() throws TC2CoreException {

    Player p1 = GameUtils.getPlayer("Junit1", 2, 0, 0);
    Card char1 = p1.getIngameDeck().getCard(CardType.CHARACTER, CardLocation.HAND);
    p1.getIngameDeck().setCardLocation(char1, CardLocation.BOARD);
    char1.setIntSetting("ATT", new RangeValue("5/8"));

    Player p2 = GameUtils.getPlayer("Junit2", 2, 0, 0);
    GameContext context = new GameContext(p1, p2);
    context.setCurrentPlayer(p1);

    randomEffect.resolve(context, new CardEffectLog(0, ""));
    assertEquals(char1.getIntSetting("ATT").getMin(), char1.getIntSetting("ATT").getMax());
    assertTrue(char1.getIntSetting("ATT").getMin() >= 5);
    assertTrue(char1.getIntSetting("ATT").getMin() <= 8);

    randomEffect.resolveEnd(context);
    assertEquals("5/8", char1.getIntSetting("ATT").getDescription());
  }

}
