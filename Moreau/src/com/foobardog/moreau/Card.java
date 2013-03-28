package com.foobardog.moreau;

import java.util.EnumSet;

public enum Card {
	WILD(3),
	PLUS_TWO(10),
	RAT(9),
	RABBIT(9),
	SNAKE(9),
	SHEEP(9),
	MONKEY(9),
	ROOSTER(9),
	DOG(9);

	private static final EnumSet<Card> SPECIAL_CARDS =
			EnumSet.of(Card.WILD, Card.PLUS_TWO);
	private static final EnumSet<Card> PLAYER_CARDS = 
			EnumSet.complementOf(SPECIAL_CARDS);
	
	private final int cardCount;

	private Card(int cardCount) {
		this.cardCount = cardCount;
	}
	
	public int getCardCount() {
		return this.cardCount;
	}
	
	public static EnumSet<Card> getSpecialCards() {
		return EnumSet.copyOf(SPECIAL_CARDS);
	}
	
	public static EnumSet<Card> getPlayerCards() {
		return EnumSet.copyOf(PLAYER_CARDS);
	}
}
