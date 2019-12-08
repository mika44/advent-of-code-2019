package fr.game.advent.day08;

import org.junit.Assert;
import org.junit.Test;

public class GameOneTest {
	
	private GameOne gameOne = new GameOne();

	@Test
	public void testGame() {
		Assert.assertEquals(new Integer(2440), gameOne.play());
	}

}
