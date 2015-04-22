package com.javaadash.tc2.core;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import com.javaadash.tc2.core.card.Card;
import com.javaadash.tc2.core.card.effect.CardEffectLog;
import com.javaadash.tc2.core.card.effect.SettingChange;
import com.javaadash.tc2.core.interfaces.player.Player;

public class CharacterMatchResolverTest extends TestCase {
  private CharacterMatchResolver characterMatchResolver = new CharacterMatchResolver();

  public void testResolveMatchWithDamages() {
    // Setup context
    Card c1 = new Card();
    int att1 = 4;
    Integer life1 = 12;
    int score1 = 18;
    c1.setIntSetting("ATT", att1);
    c1.setIntSetting("LIFE", life1);
    Player p1 = new Player();
    p1.setScore(score1);

    Card c2 = new Card();
    int def2 = 2;
    int life2 = 21;
    int score2 = 81;
    c2.setIntSetting("DEF", def2);
    c2.setIntSetting("LIFE", life2);
    Player p2 = new Player();
    p2.setScore(score2);

    List<CardEffectLog> effectDescriptions = new ArrayList<CardEffectLog>();
    characterMatchResolver.resolveCharacterMatch(c1, p1, c2, effectDescriptions);

    assertEquals(c2.getIntSetting("LIFE").getMax(), c2.getIntSetting("LIFE").getMin());
    assertEquals(new Integer(life2 - (att1 - def2)), c2.getIntSetting("LIFE").getMin());
    assertEquals(c1.getIntSetting("LIFE").getMax(), c1.getIntSetting("LIFE").getMin());
    assertEquals(life1, c1.getIntSetting("LIFE").getMin());
    assertEquals(score1 + (att1 - def2), p1.getScore());
    assertEquals(score2, p2.getScore());

    // check description of effects has been added
    assertEquals(1, effectDescriptions.size());
    assertEquals(1, effectDescriptions.get(0).getSettingChanges().size());
    SettingChange settingChange = effectDescriptions.get(0).getSettingChanges().get(0);
    assertEquals("LIFE", settingChange.getSetting());
    assertEquals(Integer.toString(life2 - (att1 - def2)), settingChange.getNewValue());
    assertEquals("-" + (att1 - def2), settingChange.getDiff());
    assertEquals(c2.getId(), settingChange.getCharacterId());
  }

  public void testResolveMatchNoDamages() {
    // Setup context
    Card c1 = new Card();
    int att1 = 1;
    int life1 = 12;
    int score1 = 18;
    c1.setIntSetting("ATT", att1);
    c1.setIntSetting("LIFE", life1);
    Player p1 = new Player();
    p1.setScore(score1);

    Card c2 = new Card();
    int def2 = 2;
    Integer life2 = 21;
    c2.setIntSetting("DEF", def2);
    c2.setIntSetting("LIFE", life2);

    List<CardEffectLog> effectDescriptions = new ArrayList<CardEffectLog>();
    characterMatchResolver.resolveCharacterMatch(c1, p1, c2, effectDescriptions);

    assertEquals(c2.getIntSetting("LIFE").getMax(), c2.getIntSetting("LIFE").getMin());
    assertEquals(life2, c2.getIntSetting("LIFE").getMin());
    assertEquals(score1, p1.getScore());

    // check description of effects has been added
    assertEquals(1, effectDescriptions.size());
    assertEquals(1, effectDescriptions.get(0).getSettingChanges().size());
    SettingChange settingChange = effectDescriptions.get(0).getSettingChanges().get(0);
    assertEquals("LIFE", settingChange.getSetting());
    assertEquals(Integer.toString(life2), settingChange.getNewValue());
    assertEquals("0", settingChange.getDiff());
    assertEquals(c2.getId(), settingChange.getCharacterId());
  }
}
