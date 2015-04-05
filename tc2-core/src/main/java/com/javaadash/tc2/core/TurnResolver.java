package com.javaadash.tc2.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.javaadash.tc2.core.board.CardLocation;
import com.javaadash.tc2.core.card.Card;
import com.javaadash.tc2.core.card.CardType;
import com.javaadash.tc2.core.card.effect.CardEffectLog;
import com.javaadash.tc2.core.context.GameContext;

public class TurnResolver {
  private PlayManager playManager;
  private CardEffectResolver effectResolver = new CardEffectResolver();
  private CharacterMatchResolver characterMatchResolver = new CharacterMatchResolver();

  public TurnResolver(PlayManager playManager) {
    this.playManager = playManager;
  }

  public void resolveTurn(GameContext context) {
    log.info("Resolving the turn ...");

    Card char1 =
        context.getFirstPlayer().getIngameDeck().getCard(CardType.CHARACTER, CardLocation.BOARD);
    Card char2 =
        context.getSecondPlayer().getIngameDeck().getCard(CardType.CHARACTER, CardLocation.BOARD);

    // List change of settings for each card
    List<CardEffectLog> cardEffectLogs = new ArrayList<CardEffectLog>();

    // first resolve characters effects
    context.setCurrentPlayer(context.getFirstPlayer());
    effectResolver.resolveCardEffect(Collections.singletonList(char1), context, cardEffectLogs);

    context.setCurrentPlayer(context.getSecondPlayer());
    effectResolver.resolveCardEffect(Collections.singletonList(char2), context, cardEffectLogs);

    // then resolve actions effects
    Collection<Card> actions1 =
        context.getFirstPlayer().getIngameDeck().getCards(CardType.ACTION, CardLocation.BOARD);
    Collection<Card> actions2 =
        context.getSecondPlayer().getIngameDeck().getCards(CardType.ACTION, CardLocation.BOARD);

    context.setCurrentPlayer(context.getFirstPlayer());
    effectResolver.resolveCardEffect(actions1, context, cardEffectLogs);
    context.setCurrentPlayer(context.getSecondPlayer());
    effectResolver.resolveCardEffect(actions2, context, cardEffectLogs);

    // and the battle starts...
    context.setCurrentPlayer(context.getFirstPlayer());
    characterMatchResolver.resolveCharacterMatch(char1, context.getFirstPlayer(), char2,
        cardEffectLogs);
    context.setCurrentPlayer(context.getSecondPlayer());
    characterMatchResolver.resolveCharacterMatch(char2, context.getSecondPlayer(), char1,
        cardEffectLogs);

    // end actions effects
    context.setCurrentPlayer(context.getFirstPlayer());
    effectResolver.resolveEndCardEffect(actions1, context);
    context.setCurrentPlayer(context.getSecondPlayer());
    effectResolver.resolveEndCardEffect(actions2, context);

    // end characters effects
    context.setCurrentPlayer(context.getFirstPlayer());
    effectResolver.resolveEndCardEffect(Collections.singletonList(char1), context);
    context.setCurrentPlayer(context.getSecondPlayer());
    effectResolver.resolveEndCardEffect(Collections.singletonList(char2), context);

    // now send a message with all logged actions
    playManager.updateSettings(context, cardEffectLogs);
  }

  private Logger log = LoggerFactory.getLogger(TurnResolver.class);
}
