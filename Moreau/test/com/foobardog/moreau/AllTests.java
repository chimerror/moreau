package com.foobardog.moreau;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ 
	CardTests.class,
	DeckTests.class,
	GameTests.class,
	PileTests.class, 
	})
public class AllTests {
}
