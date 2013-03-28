package com.foobardog.moreau;

import static org.junit.Assert.*;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.foobardog.moreau.Card;
import com.foobardog.moreau.Deck;
import com.foobardog.moreau.Pile;

public class PileTests {
	@Rule
	public ExpectedException thrown = ExpectedException.none();
	
	@Test
	public void canCreateBoundedPile() {
		Pile boundedPile = Pile.createBoundedPile();
		assertTrue(
				"returned pile not reported as bounded",
				boundedPile.isBounded());
		assertEquals(
				"returned pile was not empty",
				0,
				boundedPile.getCardCount());
		assertFalse(
				"returned pile was reported as full",
				boundedPile.isFull());
	}
	
	@Test
	public void canAddCardsToBoundedPile() {
		Pile boundedPile = Pile.createBoundedPile();
		Deck deck = Deck.createDeck();
		
		for (int i = 1; i <= Pile.BOUNDED_PILE_SIZE; i++) {
			Card dealtCard = deck.deal();
			int cardCountBeforeAdd = 
					boundedPile.getSpecificCardCount(dealtCard);
			assertFalse(
					"pile was reported as full early",
					boundedPile.isFull());
			
			boundedPile.add(dealtCard);
			assertEquals(
					"card count not incremented properly",
					i,
					boundedPile.getCardCount());
			assertEquals(
					"specific card count not incremented properly",
					cardCountBeforeAdd + 1,
					boundedPile.getSpecificCardCount(dealtCard));
		}
		
		assertTrue(
				"pile was not full after adding cards",
				boundedPile.isFull());
	}
	
	@Test
	public void cannotAddCardsToBoundedPilePastLimit() 
			throws IllegalStateException {
		Pile boundedPile = Pile.createBoundedPile();
		Deck deck = Deck.createDeck();
		for (int i = 1; i <= Pile.BOUNDED_PILE_SIZE; i++) {
			boundedPile.add(deck.deal());
		}
	
		thrown.expect(IllegalStateException.class);
		thrown.expectMessage("Pile is full and cannot be added to");
		boundedPile.add(deck.deal());
	}
	
	@Test
	public void canCreateUnboundedPile() {
		Pile unboundedPile = Pile.createUnboundedPile();
		assertFalse(
				"returned pile not reported as bounded",
				unboundedPile.isBounded());
		assertEquals(
				"returned pile was not empty",
				0,
				unboundedPile.getCardCount());
		assertFalse(
				"returned pile was reported as full",
				unboundedPile.isFull());
	}
	
	@Test
	public void canAddCardsToUnboundedPile() {
		Pile unboundedPile = Pile.createUnboundedPile();
		Deck deck = Deck.createDeck();
		
		for (int i = 1; i <= Pile.BOUNDED_PILE_SIZE; i++) {
			Card dealtCard = deck.deal();
			int cardCountBeforeAdd = 
					unboundedPile.getSpecificCardCount(dealtCard);
			assertFalse(
					"pile was reported as full early",
					unboundedPile.isFull());
			
			unboundedPile.add(dealtCard);
			assertEquals(
					"card count not incremented properly",
					i,
					unboundedPile.getCardCount());
			assertEquals(
					"specific card count not incremented properly",
					cardCountBeforeAdd + 1,
					unboundedPile.getSpecificCardCount(dealtCard));
		}
		
		assertFalse(
				"pile was full after adding cards up to bound",
				unboundedPile.isFull());
		
		Card additionalCard = deck.deal();
		int cardCountBeforeAdd = unboundedPile.getCardCount();
		int specificCardCountBeforeAdd = 
				unboundedPile.getSpecificCardCount(additionalCard);
		unboundedPile.add(additionalCard);
		assertEquals(
				"card count not incremented properly",
				cardCountBeforeAdd + 1,
				unboundedPile.getCardCount());
		assertEquals(
				"specific card count not incremented properly",
				specificCardCountBeforeAdd + 1,
				unboundedPile.getSpecificCardCount(additionalCard));
		assertFalse(
				"pile was full after adding cards past bound",
				unboundedPile.isFull());
	}
}
