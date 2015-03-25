package main;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

import com.javaadash.tc2.core.card.Card;
import com.javaadash.tc2.core.exceptions.TC2CoreException;
import com.javaadash.tc2.core.interfaces.message.EndGameMessage;
import com.javaadash.tc2.core.interfaces.message.StartGameMessage;
import com.javaadash.tc2.core.interfaces.message.UpdateGameMessage;
import com.javaadash.tc2.core.interfaces.player.TC2PlayerInterface;

public class AutomaticPlayerInterface implements TC2PlayerInterface {
  private static final long serialVersionUID = -6441688386633163335L;

  @Override
  public void startGame(StartGameMessage msg) {
    System.out.println("AutomaticPlayerInterface - receive startGame()");
  }

  @Override
  public Card selectCharacter(Collection<Card> availableCharacters) {
    int index = (int) (Math.random() * availableCharacters.size());
    Iterator<Card> it = availableCharacters.iterator();
    for (int i = 0; i < index - 1; i++)
      it.next();
    if (it.hasNext())
      return it.next();
    else
      return null;
  }

  @Override
  public Card selectAction(Collection<Card> availableActions) {
    int index = (int) (Math.random() * availableActions.size());
    Iterator<Card> it = availableActions.iterator();
    for (int i = 0; i < index - 1; i++)
      it.next();
    if (it.hasNext())
      return it.next();
    else
      return null;
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
