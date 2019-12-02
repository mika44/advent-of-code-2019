package fr.game.advent.day02;

import org.junit.Assert;
import org.junit.Test;

public class GameTwoTest {
	
	private GameTwo gameTwo = new GameTwo();

	@Test
	public void testGame() {
		Assert.assertEquals(new Integer(9074), gameTwo.play());
	}
}
