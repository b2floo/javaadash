package com.javaadash.tc2.core.card;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.javaadash.tc2.core.card.condition.Condition;

public class Chain implements Serializable {
  private static final long serialVersionUID = -2370584983032777811L;

  protected List<Condition> conditions = new ArrayList<Condition>();

  public Chain() {

  }

  public Chain(List<Condition> conditions) {
    this.conditions = conditions;
  }

  public Collection<Condition> getConditions() {
    return Collections.unmodifiableCollection(conditions);
  }
}
