package com.javaadash.tc2.core.card;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.javaadash.tc2.core.card.condition.Condition;
import com.javaadash.tc2.core.card.effect.Effect;

public class Card implements Serializable {
  private static final long serialVersionUID = 3548312040504128825L;

  protected String id;
  protected String description;
  protected Integer type;
  protected Collection<Effect> effects = new ArrayList<Effect>();
  protected Collection<Condition> conditions = new ArrayList<Condition>();
  protected Map<String, String> settings = new HashMap<String, String>();
  protected Chain chain;
  protected Boolean available = true;

  public Card() {

  }

  public Card(Integer type) {
    this.type = type;
  }

  public Card(Integer type, String description) {
    this.type = type;
    this.description = description;
  }

  public Card(String id, Integer type, String description, Collection<Effect> effects,
      Collection<Condition> conditions, Map<String, String> settings) {
    this.id = id;
    this.type = type;
    this.description = description;

    this.effects = effects;
    this.conditions = conditions;
    this.settings = settings;
  }

  public void addCondition(Condition condition) {
    this.conditions.add(condition);
  }

  public void setChain(Chain chain) {
    this.chain = chain;
  }

  public Collection<Effect> getEffects() {
    return Collections.unmodifiableCollection(effects);
  }

  public Collection<Condition> getConditions() {
    return Collections.unmodifiableCollection(conditions);
  }

  public int getIntSetting(String setting) {
    return Integer.parseInt(settings.get(setting));
  }

  public void setIntSetting(String setting, Integer value) {
    settings.put(setting, value.toString());
  }

  public String getSetting(String setting) {
    return settings.get(setting);
  }

  public void setSetting(String setting, String value) {
    settings.put(setting, value);
  }

  public Map<String, String> getSettings() {
    return settings;
  }

  public Integer getType() {
    return type;
  }

  public void setType(Integer type) {
    this.type = type;
  }

  public String getId() {
    return id;
  }

  public Boolean getAvailable() {
    return available;
  }

  public void setAvailable(Boolean available) {
    this.available = available;
  }

  // TODO in a factory and throw exceptions
  public static Map<String, String> createSettings(String desc) {
    Map<String, String> settings = new HashMap<String, String>();
    for (String set : desc.split("-")) {
      String[] s = set.trim().split(":");
      settings.put(s[0].trim(), s[1].trim());
    }
    return settings;
  }

  @Override
  public String toString() {
    return "Card [id=" + id + ", description=" + description + ", type=" + type + ", effects="
        + effects + ", conditions=" + conditions + ", settings=" + settings + ", chain=" + chain
        + ", available=" + available + "]";
  }
}
