package com.foobardog.moreau;

import java.util.*;

public class Game {
	public static final int MINIMUM_PLAYERS = 3;
	public static final int MAXIMUM_PLAYERS = 5;
	
	private final Deck deck;
	private final List<Pile> centerPiles;
	private final List<Pile> playerPiles;
	private Random rng;
	private EnumSet<Card> removedCard;
	
	private Game(int playerCount) {
		deck = Deck.createDeck();
		centerPiles = new ArrayList<Pile>();
		playerPiles = new ArrayList<Pile>();
		for (int i = 0; i < playerCount; i++) {
			centerPiles.add(Pile.createBoundedPile());
			playerPiles.add(Pile.createUnboundedPile());
		}
		rng = new Random();
		removedCard = EnumSet.noneOf(Card.class);
		
		EnumSet<Card> selectedPlayerCards = Card.getPlayerCards();
		while (selectedPlayerCards.size() > playerCount) {
			 Object[] availableCards = selectedPlayerCards.toArray();
			 Card cardToRemove = 
					 (Card)availableCards[rng.nextInt(availableCards.length)];
			 selectedPlayerCards.remove(cardToRemove);
		}
		assert selectedPlayerCards.size() == playerCount;

		Object[] selectedPlayerCardsArray = selectedPlayerCards.toArray();
		for (int i = 0; i < playerCount; i++) {
			Card playerCard = (Card)selectedPlayerCardsArray[i];
			deck.removeFirst(playerCard);
			playerPiles.get(i).add(playerCard);
		}
		
		if (playerCount == 3) {
			EnumSet<Card> removableCards = Card.getPlayerCards();
			removableCards.removeAll(selectedPlayerCards);
			Object[] removableCardsArray = removableCards.toArray();
			Card cardToRemove =
					(Card)removableCardsArray[rng.nextInt(removableCardsArray.length)];
			deck.removeAll(cardToRemove);
			removedCard.add(cardToRemove);
		}
	}
	
	public static Game createGame(int playerCount) 
			throws IllegalArgumentException {
		if (playerCount < Game.MINIMUM_PLAYERS ||
			playerCount > Game.MAXIMUM_PLAYERS) {
			throw new IllegalArgumentException(String.format(
				"Invalid number of players, must be between %d and %d",
				Game.MINIMUM_PLAYERS, Game.MAXIMUM_PLAYERS));
		}
		return new Game(playerCount);
	}

	public boolean cardHasBeenRemoved() {
		return !removedCard.isEmpty();
	}
	
	public Card getRemovedCard() throws IllegalStateException {
		if (!this.cardHasBeenRemoved()) {
			throw new IllegalStateException("No card has been removed");
		}
		return removedCard.iterator().next();
	}
	
	public int getCardsLeft() {
		return deck.getSize();
	}
	
	Pile getDeckPile() {
		return deck.toPile();
	}
	
	public Pile getPlayerPile(int player) {
		return new Pile(playerPiles.get(player));
	}
	
	public List<Pile> getCenterPiles() {
		return new ArrayList<Pile>(centerPiles);
	}
}
