package com.javaadash.tc2.core.card.effect;

import java.io.Serializable;

import com.javaadash.tc2.core.context.GameContext;

/**
 * Interface for cards effects
 * 
 * @author b2floo
 * 
 */
public interface Effect extends Serializable {
  /**
   * Take into account the game context and apply the effect
   * 
   * @param context
   */
  void resolve(GameContext context, CardEffectLog cardEffectLog);

  /**
   * Take into account the game context and check if the effect should should
   * 
   * @param context
   */
  void resolveEnd(GameContext context);

  @Override
  String toString();
}
