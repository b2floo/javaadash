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

public class SettingTransferEffectTest extends TestCase {

  private SettingTransferEffect effect;
  private int modifier = 2;

  @Override
  protected void setUp() throws Exception {
    effect = new SettingTransferEffect("LIFE", Target.SELF, modifier, "ATT", Target.SELF);
  }

  public void testResolve() throws TC2CoreException {

    int lifeValue = 10;
    int attckValue = 5;

    Player p1 = GameUtils.getPlayer("Junit1", 2, 0, 0);
    Card char1 = p1.getIngameDeck().getCard(CardType.CHARACTER, CardLocation.HAND);
    p1.getIngameDeck().setCardLocation(char1, CardLocation.BOARD);
    char1.setIntSetting("ATT", attckValue);
    char1.setIntSetting("LIFE", lifeValue);

    Player p2 = GameUtils.getPlayer("Junit2", 2, 0, 0);
    GameContext context = new GameContext(p1, p2);
    context.setCurrentPlayer(p1);

    effect.resolve(context, new CardEffectLog(0, ""));
    assertEquals(attckValue + modifier, char1.getIntSetting("ATT"));
    assertEquals(lifeValue - modifier, char1.getIntSetting("LIFE"));

    effect.resolveEnd(context);
    assertEquals(attckValue, char1.getIntSetting("ATT"));
    assertEquals(lifeValue, char1.getIntSetting("LIFE"));
  }

}
