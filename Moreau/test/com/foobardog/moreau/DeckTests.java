package com.foobardog.moreau;

import static org.junit.Assert.*;

import java.util.EnumSet;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.foobardog.moreau.Card;
import com.foobardog.moreau.Deck;
import com.foobardog.moreau.Pile;

public class DeckTests {
	@Rule
	public ExpectedException thrown = ExpectedException.none();
	
	@Test
	public void newDeckHasRightNumberOfCards() {
		Deck deck = Deck.createDeck();
		assertEquals(
				"unexpected number of cards in deck",
				76,
				deck.getSize());
	}
	
	@Test
	public void deckCanBeConvertedToPile() {
		Deck deck = Deck.createDeck();
		Pile pile = deck.toPile();
		assertTrue(
				"pile returned from deck was not bounded",
				pile.isBounded());
		assertTrue(
				"pile returned from deck was not full",
				pile.isFull());
		assertEquals(
				"pile returned from deck did not contain all cards",
				EnumSet.allOf(Card.class),
				pile.getPileCardTypes());
		assertEquals(
				"pile returned from deck did not have right card count",
				76,
				pile.getCardCount());
		for (Card cardType : Card.values()) {
			assertEquals(
					String.format("pile returned from deck did not have right number of cards for %s", cardType),
					cardType.getCardCount(),
					pile.getSpecificCardCount(cardType));
		}
	}
	
	@Test
	public void partiallyDealtDeckCanBeConvertedToPile() {
		Deck deck = Deck.createDeck();
		Pile dealtCards = Pile.createUnboundedPile();
		for (int i = 0; i < 10; i++) {
			dealtCards.add(deck.deal());
		}
		
		Pile restOfDeck = deck.toPile();
		assertTrue(
				"pile returned from deck was not bounded",
				restOfDeck.isBounded());
		assertTrue(
				"pile returned from deck was not full",
				restOfDeck.isFull());
		assertEquals(
				"pile returned from deck did not have right card count",
				66,
				restOfDeck.getCardCount());
		for (Card cardType : Card.values()) {
			assertEquals(
					String.format("pile returned from deck did not have right number of cards for %s", cardType),
					cardType.getCardCount() - dealtCards.getSpecificCardCount(cardType),
					restOfDeck.getSpecificCardCount(cardType));
		}
	}
	
	@Test
	public void emptyDeckCanBeConvertedToPile() {
		Deck deck = Deck.createDeck();
		while (deck.getSize() > 0) {
			deck.deal();
		}
		
		Pile restOfDeck = deck.toPile();
		assertTrue(
				"pile returned from deck was not bounded",
				restOfDeck.isBounded());
		assertTrue(
				"pile returned from deck was not full",
				restOfDeck.isFull());
		assertEquals(
				"pile returned from deck did not have right card count",
				0,
				restOfDeck.getCardCount());
		for (Card cardType : Card.values()) {
			assertEquals(
					String.format("pile returned from empty deck did not have right number of cards for %s", cardType),
					0,
					restOfDeck.getSpecificCardCount(cardType));
		}
	}
	
	@Test
	public void cannotAddCardsToPileCreatedFromDeck() 
			throws IllegalStateException {
		Deck deck = Deck.createDeck();
		Pile pile = deck.toPile();
		
		thrown.expect(IllegalStateException.class);
		thrown.expectMessage("Pile is full and cannot be added to");
		pile.add(Card.WILD);
	}

	@Test
	public void shuffledDeckCanBeSeeded() {
		Deck deck = Deck.createDeck(42);
		assertEquals(
				"unexpected number of cards in seeded deck",
				76,
				deck.getSize());

		Card[] expectedCards = {
				Card.DOG,
				Card.PLUS_TWO,
				Card.SHEEP,
				Card.RABBIT,
				Card.PLUS_TWO,
				Card.MONKEY,
				Card.ROOSTER,
				Card.ROOSTER,
				Card.MONKEY,
				Card.DOG,
				};
		for (Card expectedCard : expectedCards) {
			assertEquals(
					"unexpected card dealt from seeded deck",
					expectedCard,
					deck.deal());
		}
	}
	
	@Test
	public void cardTypeCanBeRemovedFromDeck() {
		Deck deck = Deck.createDeck();
		int sizeBeforeRemoval = deck.getSize();
		Pile pileBeforeRemoval = deck.toPile();
		int monkeyCardsBeforeRemoval = 
				pileBeforeRemoval.getSpecificCardCount(Card.MONKEY);
		assertTrue(
				"no monkey cards to remove",
				monkeyCardsBeforeRemoval > 0);
		
		deck.removeAll(Card.MONKEY);
		assertEquals(
				"full count not updated",
				sizeBeforeRemoval - monkeyCardsBeforeRemoval,
				deck.getSize());
		Pile pileAfterRemoval = deck.toPile();
		assertEquals(
				"monkey count not updated",
				0,
				pileAfterRemoval.getSpecificCardCount(Card.MONKEY));
	}
}
