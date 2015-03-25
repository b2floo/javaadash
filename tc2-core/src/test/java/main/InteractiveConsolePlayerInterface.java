package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.Iterator;

import com.javaadash.tc2.core.card.Card;
import com.javaadash.tc2.core.exceptions.TC2CoreException;
import com.javaadash.tc2.core.interfaces.message.EndGameMessage;
import com.javaadash.tc2.core.interfaces.message.StartGameMessage;
import com.javaadash.tc2.core.interfaces.message.UpdateGameMessage;
import com.javaadash.tc2.core.interfaces.player.TC2PlayerInterface;

public class InteractiveConsolePlayerInterface implements TC2PlayerInterface {
  private static final long serialVersionUID = 1177974093999000139L;

  @Override
  public void startGame(StartGameMessage msg) {
    System.out.println("AutomaticPlayerInterface - received " + msg);
  }

  @Override
  public Card selectCharacter(Collection<Card> availableCharacters) {
    return selectCard(availableCharacters);
  }

  @Override
  public Card selectAction(Collection<Card> availableActions) {
    return selectCard(availableActions);
  }

  private Card selectCard(Collection<Card> availableCards) {
    int i = 1;
    for (Card card : availableCards) {
      System.out.println((i++) + "/ " + card);
    }
    boolean choiceApproved = false;
    int index = -1;
    while (!choiceApproved) {
      System.out.println("Enter your choice: ");
      BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
      try {
        String choice = reader.readLine();
        index = Integer.parseInt(choice);
      } catch (Exception e) {
        System.out.println("Input is not a number");
        continue;
      }
      if (index > availableCards.size())
        System.out.println("Input is not in available range");
      else
        choiceApproved = true;
    }

    int j = 0;
    Card selectedCard = null;
    for (Iterator<Card> it = availableCards.iterator(); it.hasNext() && j++ < index;)
      selectedCard = it.next();

    return selectedCard;
  }

  @Override
  public void updateGameStatus(UpdateGameMessage message) throws IOException, TC2CoreException {
    // TODO Auto-generated method stub

  }

  @Override
  public void endGame(EndGameMessage msg) {
    // TODO Auto-generated method stub

  }



}
