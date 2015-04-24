package com.javaadash.tc2.core;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import com.javaadash.tc2.core.card.Card;
import com.javaadash.tc2.core.card.effect.log.CardEffectLog;
import com.javaadash.tc2.core.card.effect.log.SettingChange;
import com.javaadash.tc2.core.interfaces.player.Player;

public class CharacterMatchResolverTest extends TestCase {
  private CharacterMatchResolver characterMatchResolver = new CharacterMatchResolver();

  public void testResolveMatchWithDamages() {
    // Setup context
    Card c1 = new Card();
    c1.setIntSetting("ATT", 4);
    c1.setIntSetting("LIFE", 12);
    Player p1 = new Player();
    p1.setScore(18);

    Card c2 = new Card();
    c2.setIntSetting("DEF", 2);
    c2.setIntSetting("LIFE", 21);
    Player p2 = new Player();
    p2.setScore(81);

    List<CardEffectLog> effectDescriptions = new ArrayList<CardEffectLog>();
    characterMatchResolver.resolveCharacterMatch(c1, p1, c2, effectDescriptions);

    assertEquals(Integer.valueOf(19), c2.getIntSetting("LIFE").getMin()); // 21- (4-2)
    assertEquals(Integer.valueOf(12), c1.getIntSetting("LIFE").getMin());
    assertEquals(20, p1.getScore()); // 18 + (4-2)
    assertEquals(81, p2.getScore());

    // check description of effects has been added
    assertEquals(1, effectDescriptions.size());
    assertEquals(1, effectDescriptions.get(0).getSettingChanges().size());
    SettingChange settingChange = effectDescriptions.get(0).getSettingChanges().get(0);
    assertEquals("LIFE", settingChange.getSetting());
    assertEquals("19", settingChange.getNewValue());
    assertEquals("-2", settingChange.getDiff());
    assertEquals(c2.getId(), settingChange.getCharacterId());
  }

  public void testResolveMatchNoDamages() {
    // Setup context
    Card c1 = new Card();
    c1.setIntSetting("ATT", 1);
    c1.setIntSetting("LIFE", 12);
    Player p1 = new Player();
    p1.setScore(18);

    Card c2 = new Card();
    c2.setIntSetting("DEF", 2);
    c2.setIntSetting("LIFE", 21);

    List<CardEffectLog> effectDescriptions = new ArrayList<CardEffectLog>();
    characterMatchResolver.resolveCharacterMatch(c1, p1, c2, effectDescriptions);

    assertEquals(c2.getIntSetting("LIFE").getMax(), c2.getIntSetting("LIFE").getMin());
    assertEquals(Integer.valueOf(21), c2.getIntSetting("LIFE").getMin());
    assertEquals(18, p1.getScore());

    // check description of effects has been added
    assertEquals(1, effectDescriptions.size());
    assertEquals(1, effectDescriptions.get(0).getSettingChanges().size());
    SettingChange settingChange = effectDescriptions.get(0).getSettingChanges().get(0);
    assertEquals("LIFE", settingChange.getSetting());
    assertEquals("21", settingChange.getNewValue());
    assertEquals("0", settingChange.getDiff());
    assertEquals(c2.getId(), settingChange.getCharacterId());
  }

  public void testResolveMatchWithDamages_DamageModifier() {
    // Setup context
    Card c1 = new Card();
    c1.setIntSetting("ATT", 4);
    c1.setIntSetting("LIFE", 12);
    Player p1 = new Player();
    p1.setScore(18);

    Card c2 = new Card();
    c2.setIntSetting("DEF", 2);
    c2.setIntSetting("LIFE", 21);
    c2.registerDamageModifier(new MockDamageModifier(-1));
    Player p2 = new Player();
    p2.setScore(81);

    List<CardEffectLog> effectDescriptions = new ArrayList<CardEffectLog>();
    characterMatchResolver.resolveCharacterMatch(c1, p1, c2, effectDescriptions);

    assertEquals(Integer.valueOf(20), c2.getIntSetting("LIFE").getMin()); // 21- (4-2) +1
    assertEquals(19, p1.getScore()); // 18 + (4-2) -1

    // check description of effects has been added
    assertEquals(2, effectDescriptions.size());
    assertEquals(1, effectDescriptions.get(0).getSettingChanges().size());
    SettingChange settingChange = effectDescriptions.get(0).getSettingChanges().get(0);
    assertEquals("LIFE", settingChange.getSetting());
    assertEquals("19", settingChange.getNewValue());
    assertEquals("-2", settingChange.getDiff());
    assertEquals(c2.getId(), settingChange.getCharacterId());

    assertEquals(1, effectDescriptions.get(1).getSettingChanges().size());
    settingChange = effectDescriptions.get(1).getSettingChanges().get(0);
    assertEquals("LIFE", settingChange.getSetting());
    assertEquals("20", settingChange.getNewValue());
    assertEquals("+1", settingChange.getDiff());
    assertEquals(c2.getId(), settingChange.getCharacterId());
  }

  public void testResolveMatchWithNoDamages_DamageModifier() {
    // Setup context
    Card c1 = new Card();
    c1.setIntSetting("ATT", 1);
    c1.setIntSetting("LIFE", 12);
    Player p1 = new Player();
    p1.setScore(18);

    Card c2 = new Card();
    c2.setIntSetting("DEF", 2);
    c2.setIntSetting("LIFE", 21);
    c2.registerDamageModifier(new MockDamageModifier(-1));

    List<CardEffectLog> effectDescriptions = new ArrayList<CardEffectLog>();
    characterMatchResolver.resolveCharacterMatch(c1, p1, c2, effectDescriptions);

    assertEquals(c2.getIntSetting("LIFE").getMax(), c2.getIntSetting("LIFE").getMin());
    assertEquals(Integer.valueOf(21), c2.getIntSetting("LIFE").getMin());
    assertEquals(18, p1.getScore());

    // check description of effects has been added
    assertEquals(2, effectDescriptions.size());
    assertEquals(1, effectDescriptions.get(0).getSettingChanges().size());
    SettingChange settingChange = effectDescriptions.get(0).getSettingChanges().get(0);
    assertEquals("LIFE", settingChange.getSetting());
    assertEquals("21", settingChange.getNewValue());
    assertEquals("0", settingChange.getDiff());
    assertEquals(c2.getId(), settingChange.getCharacterId());

    assertEquals(1, effectDescriptions.get(1).getSettingChanges().size());
    settingChange = effectDescriptions.get(1).getSettingChanges().get(0);
    assertEquals("LIFE", settingChange.getSetting());
    assertEquals("21", settingChange.getNewValue());
    assertEquals("+0", settingChange.getDiff());
    assertEquals(c2.getId(), settingChange.getCharacterId());
  }
}
