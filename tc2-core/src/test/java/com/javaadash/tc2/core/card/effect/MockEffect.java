package com.javaadash.tc2.core.card.effect;

import com.javaadash.tc2.core.context.GameContext;

/**
 * A mock effect that store if resolve() and endResolve() methods have been called
 * 
 * @author b2floo
 * 
 */
public class MockEffect implements Effect {
  private static final long serialVersionUID = -5303571966767917546L;

  private boolean resolved = false;
  private boolean endResolved = false;

  @Override
  public void resolve(GameContext context, CardEffectLog desc) {
    resolved = true;
    desc.getSettingChanges().add(new SettingChange(null, null, null));
  }

  @Override
  public void resolveEnd(GameContext context) {
    endResolved = true;
  }

  public void resetResolutions() {
    this.resolved = false;
    this.endResolved = false;
  }

  public boolean isResolved() {
    return resolved;
  }

  public boolean isEndResolved() {
    return endResolved;
  }
}
