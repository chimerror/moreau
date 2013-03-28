package com.foobardog.moreau;

import java.util.EnumSet;
import java.util.Random;
import java.util.Stack;
import java.util.Vector;

public class Deck {
	private final Stack<Card> deck;
	private Random rng;

	private Deck() {
		deck = new Stack<Card>();
		for (Card cardType : Card.values()) {
			for (int i = 0; i < cardType.getCardCount(); i++) {
				deck.push(cardType);
			}
		}
		rng = new Random();
	}

	public int getSize() {
		return deck.size();
	}

	public static Deck createDeck() {
		Deck deck = new Deck();
		deck.shuffle();
		return deck;
	}

	static Deck createDeck(long seed) {
		Deck deck = new Deck();
		deck.shuffle(seed);
		return deck;
	}
	
	Pile toPile() {
		Pile pile = new Pile(this.getSize());
		for (Card card : deck) {
			pile.add(card);
		}
		return pile;
	}

	private void shuffle(long seed) {
		rng.setSeed(seed);
		this.shuffle();
	}

	private void shuffle() {
		Vector<Card> deckCopy = new Vector<Card>(deck);
		deck.clear();
		while (deckCopy.size() > 0) {
			int cardToRemove = rng.nextInt(deckCopy.size());
			deck.push(deckCopy.remove(cardToRemove));
		}
	}

	public Card deal() {
		return deck.pop();
	}
	
	void removeFirst(Card cardToRemove) {
		deck.remove(cardToRemove);
	}
	
	void removeAll(Card cardToRemove) {
		deck.removeAll(EnumSet.of(cardToRemove));
	}
}
