package com.javaadash.tc2.core.card.effect.character.setting.modification;

import junit.framework.TestCase;

import com.javaadash.tc2.core.GameUtils;
import com.javaadash.tc2.core.board.CardLocation;
import com.javaadash.tc2.core.card.Card;
import com.javaadash.tc2.core.card.CardType;
import com.javaadash.tc2.core.card.effect.Effect;
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
    effect.resolve(context);

    assertEquals(initialSetting + modifier, targetCharacter.getIntSetting(setting));
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
