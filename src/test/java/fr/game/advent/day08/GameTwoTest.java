package fr.game.advent.day08;

import org.junit.Assert;
import org.junit.Test;

public class GameTwoTest {
	
	private GameTwo gameTwo = new GameTwo();

	@Test
	public void testGame() {
		Assert.assertEquals(new Integer(0), gameTwo.play());
	}

}
