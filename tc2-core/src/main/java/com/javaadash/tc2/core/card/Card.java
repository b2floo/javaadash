package com.javaadash.tc2.core.card;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import com.javaadash.tc2.core.card.condition.Condition;
import com.javaadash.tc2.core.card.effect.Effect;

public class Card implements Serializable {
  private static final long serialVersionUID = 3548312040504128825L;
  protected static AtomicInteger ID_GENERATOR = new AtomicInteger();

  protected Integer id;
  protected String cardCode;
  protected String description;
  protected Integer type;
  protected List<Effect> effects = new ArrayList<Effect>();
  protected List<Condition> conditions = new ArrayList<Condition>();
  protected Map<String, String> settings = new HashMap<String, String>();
  protected Chain chain;
  protected Boolean available = true;

  public Card() {

  }

  public Card(Integer type) {
    this.id = ID_GENERATOR.getAndIncrement();
    this.type = type;
  }

  public Card(Integer type, String description) {
    this.id = ID_GENERATOR.getAndIncrement();
    this.type = type;
    this.description = description;
  }

  public Card(String cardCode, Integer type, String description, List<Effect> effects,
      List<Condition> conditions, Map<String, String> settings) {
    this.id = ID_GENERATOR.getAndIncrement();
    this.cardCode = cardCode;
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

  public List<Effect> getEffects() {
    return Collections.unmodifiableList(effects);
  }

  public List<Condition> getConditions() {
    return Collections.unmodifiableList(conditions);
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

  public Integer getId() {
    return id;
  }

  public String getCardCode() {
    return cardCode;
  }

  public Boolean getAvailable() {
    return available;
  }

  public void setAvailable(Boolean available) {
    this.available = available;
  }

  public String getDescription() {
    return description;
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
    return "Card [id=" + id + ", cardCode=" + cardCode + ", description=" + description + ", type="
        + type + ", effects=" + effects + ", conditions=" + conditions + ", settings=" + settings
        + ", chain=" + chain + ", available=" + available + "]";
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Card other = (Card) obj;
    if (id == null) {
      if (other.id != null)
        return false;
    } else if (!id.equals(other.id))
      return false;
    return true;
  }
}
