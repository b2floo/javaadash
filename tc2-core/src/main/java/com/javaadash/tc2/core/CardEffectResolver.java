package com.javaadash.tc2.core;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.javaadash.tc2.core.card.Card;
import com.javaadash.tc2.core.card.effect.Effect;
import com.javaadash.tc2.core.context.GameContext;

/**
 * Loop over a collection of cards to apply or unapply effects
 * 
 * @author b2floo
 * 
 */
public class CardEffectResolver {

  private Logger log = LoggerFactory.getLogger(CardEffectResolver.class);

  /**
   * Loop over a collection of cards to setup effects if applicable
   * 
   * @param cards
   * @param context
   */
  public void resolveCardEffect(Collection<Card> cards, GameContext context) {
    for (Card card : cards) {
      log.debug("Resolving card {} effects", card);
      PlayManager.updateSettings(context, "Applying " + card.getDescription() + " effects");
      for (Effect effect : card.getEffects()) {
        log.info("Effect : {}", effect);
        effect.resolve(context);
        try {
          Thread.sleep(1000);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    }
  }

  /**
   * Loop over a collection of cards to remove effects if applicable
   * 
   * @param cards
   * @param context
   */
  public void resolveEndCardEffect(Collection<Card> cards, GameContext context) {
    for (Card card : cards) {
      log.debug("Resolving card {} end effects", card);
      for (Effect effect : card.getEffects()) {
        log.info("End Effect : {}", effect);
        effect.resolveEnd(context);
      }
    }
  }

}
