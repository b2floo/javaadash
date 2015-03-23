package com.javaadash.tc2.core.card.dictionnary;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import main.CardsGenerator;

import com.javaadash.tc2.core.CardType;
import com.javaadash.tc2.core.card.Card;
import com.javaadash.tc2.core.card.condition.Condition;
import com.javaadash.tc2.core.card.effect.Effect;

public class CardDictionnary 
{
	public static AtomicBoolean initialized = new AtomicBoolean(false);
	private static Map<Integer, Card> cards = new HashMap<Integer, Card>();
	
	public static Card getCard(Integer id) {
		if(! initialized.get())
			initialize();
		
		return cards.get(id);
	}
	
	public static synchronized void initialize()
	{
		if(initialized.get())
			return;
		
		// id 0 -> 1000 characters
		// id 1000+ > actions
		CardsGenerator generator = new CardsGenerator();
		Collection<Effect> effects = generator.getEffets(1);
		cards.put(ABI, new Card(ABI,CardType.CHARACTER, "ABI:"+effects, 
				effects , new ArrayList<Condition>(), Card.createSettings("ATT:5 - DEF:3 - LIFE: 8 - CLASS: WIZARD - GUILD: ZIL")));
		effects = generator.getEffets(1);
		cards.put(ABO, new Card(ABO,CardType.CHARACTER,  "ABO:"+effects, 
			effects, new ArrayList<Condition>(), Card.createSettings("ATT:4 - DEF:3 - LIFE: 6 - CLASS: WIZARD - GUILD: ZIL")));
		effects = generator.getEffets(1);
		cards.put(ABU, new Card(ABU,CardType.CHARACTER, "ABU:"+effects, 
			effects , new ArrayList<Condition>(), Card.createSettings("ATT:5 - DEF:1 - LIFE: 7 - CLASS: THIEF - GUILD: ZIL")));
	
		effects = generator.getEffets(1);
		cards.put(YLI,new Card(YLI,CardType.CHARACTER, "YLI:"+effects, 
				effects , new ArrayList<Condition>(), Card.createSettings("ATT:4 - DEF:1 - LIFE: 9 - CLASS:WARRIOR - GUILD: NOZ"))); 
		effects = generator.getEffets(1);
		cards.put(YLO,new Card(YLO,CardType.CHARACTER, "YLO:"+effects, 
				effects , new ArrayList<Condition>(), Card.createSettings("ATT:4 - DEF:3 - LIFE: 5 - CLASS:PRIEST - GUILD:NOZ")));
		effects = generator.getEffets(1);
		cards.put(YLU,new Card(YLU,CardType.CHARACTER, "YLU:"+effects, 
				effects , new ArrayList<Condition>(), Card.createSettings("ATT:5 - DEF:2 - LIFE: 45 - CLASS: PRIEST - GUILD: NOZ"))); 
		
		// and create actions
		for(int i=0; i<50; i++)
		{
			effects = generator.getEffets(1);
			Collection<Condition> conditions =  generator.getConditions();
			cards.put(1000+i,new Card(1000+i, CardType.ACTION, "ACTION"+i+":"+effects, effects, conditions, new HashMap<String, String>()));
		}
		initialized.set(true);
	}

	public static int ABI = 0;
	public static int ABO = 1;
	public static int ABU = 2;
	public static int YLI = 3; 
	public static int YLO = 4;
	public static int YLU = 5;
}
