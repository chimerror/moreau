package com.foobardog.moreau;

import java.util.EnumMap;
import java.util.EnumSet;

public class Pile {
	public static final int BOUNDED_PILE_SIZE = 3;
	private static final int UNBOUNDED_PILE_SIZE = -1;
	
	private final EnumMap<Card, Integer> pile;
	private final int maxSize;

	Pile(int maxSize) {
		this.pile = new EnumMap<Card, Integer>(Card.class);
		this.maxSize = maxSize;
	}
	
	Pile(Pile pileToCopy) {
		this.pile = new EnumMap<Card, Integer>(pileToCopy.pile);
		this.maxSize = pileToCopy.maxSize;
	}

	public static Pile createBoundedPile() {
		return new Pile(BOUNDED_PILE_SIZE);
	}

	public static Pile createUnboundedPile() {
		return new Pile(UNBOUNDED_PILE_SIZE);
	}
	
	public boolean isBounded() {
		return maxSize != UNBOUNDED_PILE_SIZE;
	}
	
	public boolean isFull() {
		return this.isBounded() && this.getCardCount() == maxSize; 
	}
	
	public EnumSet<Card> getPileCardTypes() {
		return EnumSet.copyOf(pile.keySet());
	}

	public int getCardCount() {
		int count = 0;
		for (Card cardType : pile.keySet()) {
			count += pile.get(cardType);
		}
		return count;
	}
	
	public int getSpecificCardCount(Card cardType) {
		return pile.containsKey(cardType) 
				? pile.get(cardType)
				: 0; 
	}
	
	public void add(Card card) throws IllegalStateException {
		if (this.isFull())
		{
			throw new IllegalStateException(
					"Pile is full and cannot be added to");
		}
		pile.put(card, this.getSpecificCardCount(card) + 1);
	}
}