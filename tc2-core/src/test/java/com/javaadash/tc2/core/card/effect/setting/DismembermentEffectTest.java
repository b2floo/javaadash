package com.javaadash.tc2.core.card.effect.setting;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

import com.javaadash.tc2.core.GameUtils;
import com.javaadash.tc2.core.board.CardLocation;
import com.javaadash.tc2.core.card.Card;
import com.javaadash.tc2.core.card.CardType;
import com.javaadash.tc2.core.card.RangeValue;
import com.javaadash.tc2.core.card.effect.Effect;
import com.javaadash.tc2.core.card.effect.log.CardEffectLog;
import com.javaadash.tc2.core.card.effect.log.SettingChange;
import com.javaadash.tc2.core.context.GameContext;
import com.javaadash.tc2.core.exceptions.TC2CoreException;
import com.javaadash.tc2.core.interfaces.player.Player;

public class DismembermentEffectTest extends TestCase {

  public void testResolve() throws TC2CoreException {
    Effect effect = new DismembermentEffect();

    // set the characters setting and put it on board
    Map<String, RangeValue> intSettings = new HashMap<String, RangeValue>();
    intSettings.put("LIFE", new RangeValue("5"));
    Card card1 =
        new Card("123", CardType.CHARACTER, "JUNIT", null, null, Collections.singletonMap("CLASS",
            "MARAUDER, PRIEST"), intSettings);

    intSettings = new HashMap<String, RangeValue>();
    intSettings.put("LIFE", new RangeValue("5"));
    Card card2 =
        new Card("123", CardType.CHARACTER, "JUNIT", null, null, Collections.singletonMap("CLASS",
            "MARAUDER, PRIEST, WIZARD"), intSettings);

    Player startPlayer = GameUtils.getPlayer("junit1", 1, 0, 0, card1);
    startPlayer.getIngameDeck().setCardLocation(card1, CardLocation.BOARD);

    Player nextPlayer = GameUtils.getPlayer("junit2", 1, 0, 0, card2);

    GameContext context = new GameContext(startPlayer, nextPlayer);
    context.setCurrentPlayer(startPlayer);
    CardEffectLog effectDescription = new CardEffectLog(123, "junit Test");
    effect.resolve(context, effectDescription);

    assertEquals(Integer.valueOf(3), card1.getIntSetting("LIFE").getMin());
    assertEquals(Integer.valueOf(2), card2.getIntSetting("LIFE").getMin());

    // check description of settingChange is correctly filled
    assertEquals(2, effectDescription.getSettingChanges().size());
    SettingChange settingChange = effectDescription.getSettingChanges().get(0);
    assertEquals("LIFE", settingChange.getSetting());
    assertEquals("3", settingChange.getNewValue());
    assertEquals("-2", settingChange.getDiff());
    assertEquals(card1.getId(), settingChange.getCharacterId());

    settingChange = effectDescription.getSettingChanges().get(1);
    assertEquals("LIFE", settingChange.getSetting());
    assertEquals("2", settingChange.getNewValue());
    assertEquals("-3", settingChange.getDiff());
    assertEquals(card2.getId(), settingChange.getCharacterId());
  }
}
