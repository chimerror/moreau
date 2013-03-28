package com.foobardog.moreau;

import static org.junit.Assert.*;

import java.util.*;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.foobardog.moreau.Card;
import com.foobardog.moreau.Game;
import com.foobardog.moreau.Pile;

// TODO: games outside of bounds
public class GameTests {
	@Rule
	public ExpectedException thrown = ExpectedException.none();
	
	@Test
	public void canCreateThreePlayerGame() {
		Game game = Game.createGame(3);
		assertEquals(
				"cards left not reported correctly",
				64,
				game.getCardsLeft());
		assertTrue(
				"card has not been removed",
				game.cardHasBeenRemoved());
		
		EnumSet<Card> cardsDealt = EnumSet.noneOf(Card.class);
		for (int i = 0; i < 3; i++) {
			Pile pile = game.getPlayerPile(i);
			assertNotNull(
					String.format("could not get player pile %d", i),
					pile);
			assertEquals(
					String.format("player pile %d had unexpected count", i),
					1,
					pile.getCardCount());
			
			EnumSet<Card> playerCardTypes = pile.getPileCardTypes();
			assertEquals(
					String.format("player pile %d had unexpected card types count", i),
					1,
					playerCardTypes.size());
			
			Card playerCardType = playerCardTypes.iterator().next();
			assertEquals(
					String.format("player pile %d had unexpected specific number of cards", i),
					1,
					pile.getSpecificCardCount(playerCardType));
			assertFalse(
					String.format("player pile %d was dealt special card: %s", i, playerCardType),
					Card.getSpecialCards().contains(playerCardType));
			assertTrue(
					String.format("player pile %d was not dealt player card: %s", i, playerCardType),
					Card.getPlayerCards().contains(playerCardType));
			
			assertFalse(
					String.format("same card was dealt to two players: %s", playerCardType),
					cardsDealt.contains(playerCardType));
			cardsDealt.add(playerCardType);
		}
		
		assertEquals(
				"Unexpected number of cards dealt out",
				3,
				cardsDealt.size());
		
		List<Pile> centerPiles = game.getCenterPiles();
		for (Pile centerPile : centerPiles) {
			assertEquals(
					"center pile had cards",
					0,
					centerPile.getCardCount());
		}
		
		Pile deckPile = game.getDeckPile();
		Card missingCard = game.getRemovedCard();
		for (Card cardType : Card.values()) {
			int expectedCards = 0;
			if (cardType != missingCard) {
				expectedCards = cardType.getCardCount() -
						(cardsDealt.contains(cardType) ? 1 : 0);
				
			}
			assertEquals(
					String.format("unexpected number of cards left in deck for card type %s", cardType),
					expectedCards,
					deckPile.getSpecificCardCount(cardType));
		}
	}
	
	@Test
	public void canCreateFourPlayerGame() {
		Game game = Game.createGame(4);
		assertEquals(
				"cards left not reported correctly",
				72,
				game.getCardsLeft());
		assertFalse(
				"card has been removed",
				game.cardHasBeenRemoved());
		
		EnumSet<Card> cardsDealt = EnumSet.noneOf(Card.class);
		for (int i = 0; i < 4; i++) {
			Pile pile = game.getPlayerPile(i);
			assertNotNull(
					String.format("could not get player pile %d", i),
					pile);
			assertEquals(
					String.format("player pile %d had unexpected count", i),
					1,
					pile.getCardCount());
			
			EnumSet<Card> playerCardTypes = pile.getPileCardTypes();
			assertEquals(
					String.format("player pile %d had unexpected card types count", i),
					1,
					playerCardTypes.size());
			
			Card playerCardType = playerCardTypes.iterator().next();
			assertEquals(
					String.format("player pile %d had unexpected specific number of cards", i),
					1,
					pile.getSpecificCardCount(playerCardType));
			assertFalse(
					String.format("player pile %d was dealt special card: %s", i, playerCardType),
					Card.getSpecialCards().contains(playerCardType));
			assertTrue(
					String.format("player pile %d was not dealt player card: %s", i, playerCardType),
					Card.getPlayerCards().contains(playerCardType));
			
			assertFalse(
					String.format("same card was dealt to two players: %s", playerCardType),
					cardsDealt.contains(playerCardType));
			cardsDealt.add(playerCardType);
		}
		
		assertEquals(
				"Unexpected number of cards dealt out",
				4,
				cardsDealt.size());
		
		List<Pile> centerPiles = game.getCenterPiles();
		for (Pile centerPile : centerPiles) {
			assertEquals(
					"center pile had cards",
					0,
					centerPile.getCardCount());
		}
		
		Pile deckPile = game.getDeckPile();
		for (Card cardType : Card.values()) {
			int expectedCards = cardType.getCardCount() -
					(cardsDealt.contains(cardType) ? 1 : 0);
			assertEquals(
					String.format("unexpected number of cards left in deck for card type %s", cardType),
					expectedCards,
					deckPile.getSpecificCardCount(cardType));
		}
	}
	
	@Test
	public void canCreateFivePlayerGame() {
		Game game = Game.createGame(5);
		assertEquals(
				"cardsLeft not reported correctly",
				71,
				game.getCardsLeft());
		assertFalse(
				"card has been removed",
				game.cardHasBeenRemoved());
		
		EnumSet<Card> cardsDealt = EnumSet.noneOf(Card.class);
		for (int i = 0; i < 5; i++) {
			Pile pile = game.getPlayerPile(i);
			assertNotNull(
					String.format("could not get player pile %d", i),
					pile);
			assertEquals(
					String.format("player pile %d had unexpected count", i),
					1,
					pile.getCardCount());
			
			EnumSet<Card> playerCardTypes = pile.getPileCardTypes();
			assertEquals(
					String.format("player pile %d had unexpected card types count", i),
					1,
					playerCardTypes.size());
			
			Card playerCardType = playerCardTypes.iterator().next();
			assertEquals(
					String.format("player pile %d had unexpected specific number of cards", i),
					1,
					pile.getSpecificCardCount(playerCardType));
			assertFalse(
					String.format("player pile %d was dealt special card: %s", i, playerCardType),
					Card.getSpecialCards().contains(playerCardType));
			assertTrue(
					String.format("player pile %d was not dealt player card: %s", i, playerCardType),
					Card.getPlayerCards().contains(playerCardType));
			
			assertFalse(
					String.format("same card was dealt to two players: %s", playerCardType),
					cardsDealt.contains(playerCardType));
			cardsDealt.add(playerCardType);
		}
		
		assertEquals(
				"Unexpected number of cards dealt out",
				5,
				cardsDealt.size());
		
		List<Pile> centerPiles = game.getCenterPiles();
		for (Pile centerPile : centerPiles) {
			assertEquals(
					"center pile had cards",
					0,
					centerPile.getCardCount());
		}
		
		Pile deckPile = game.getDeckPile();
		for (Card cardType : Card.values()) {
			int expectedCards = cardType.getCardCount() -
					(cardsDealt.contains(cardType) ? 1 : 0);
			assertEquals(
					String.format("unexpected number of cards left in deck for card type %s", cardType),
					expectedCards,
					deckPile.getSpecificCardCount(cardType));
		}
	}

	@Test
	public void cannotGetRemovedCardForGameWithoutRemovedCard() 
			throws IllegalStateException {
		Game game = Game.createGame(4);
		assertFalse(
				"card has been removed",
				game.cardHasBeenRemoved());
		
		thrown.expect(IllegalStateException.class);
		thrown.expectMessage("No card has been removed");
		game.getRemovedCard();
	}
	
	@Test
	public void cannotCreateGameWithLessThanMinimumPlayers() 
			throws IllegalArgumentException {
		thrown.expect(IllegalArgumentException.class);
		thrown.expectMessage(String.format(
				"Invalid number of players, must be between %d and %d",
				Game.MINIMUM_PLAYERS, Game.MAXIMUM_PLAYERS));
		Game.createGame(Game.MINIMUM_PLAYERS - 1);
	}
	
	@Test
	public void cannotCreateGameWithGreaterThanMinimumPlayers() 
			throws IllegalArgumentException {
		thrown.expect(IllegalArgumentException.class);
		thrown.expectMessage(String.format(
				"Invalid number of players, must be between %d and %d",
				Game.MINIMUM_PLAYERS, Game.MAXIMUM_PLAYERS));
		Game.createGame(Game.MAXIMUM_PLAYERS + 1);
	}
}
