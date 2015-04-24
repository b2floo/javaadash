package com.javaadash.tc2.core;

import java.util.List;
import java.util.ListIterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.javaadash.tc2.core.card.Card;
import com.javaadash.tc2.core.card.effect.Effect;
import com.javaadash.tc2.core.card.effect.log.CardEffectLog;
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
  public void resolveCardEffect(List<Card> cards, GameContext context,
      List<CardEffectLog> cardEffectLogs) {
    for (Card card : cards) {
      log.debug("Resolving card {} effects", card);
      CardEffectLog cardEffectLog =
          new CardEffectLog(card.getId(), "Activating " + card.getDescription() + " effects");
      cardEffectLogs.add(cardEffectLog);
      for (Effect effect : card.getEffects()) {
        log.debug("Resolving effect : {}", effect);
        effect.resolve(context, cardEffectLog);
      }
    }
  }

  /**
   * Loop over a collection of cards to remove effects if applicable
   * 
   * @param cards
   * @param context
   */
  public void resolveEndCardEffect(List<Card> cards, GameContext context) {

    for (Card card : cards) {
      log.debug("Resolving card {} end effects", card);
      ListIterator<Effect> effectIt = card.getEffects().listIterator(card.getEffects().size());
      while (effectIt.hasPrevious()) {
        Effect effect = effectIt.previous();
        log.info("End Effect : {}", effect);
        effect.resolveEnd(context);
      }
    }
  }
}
