package com.foobardog.moreau;

import static org.junit.Assert.*;
import org.junit.Test;

import com.foobardog.moreau.Card;

public class CardTests {
	@Test
	public void rightNumberOfCardValues() {
		assertEquals(
				"unexpected number of card values",
				9,
				Card.values().length);
	}
}
