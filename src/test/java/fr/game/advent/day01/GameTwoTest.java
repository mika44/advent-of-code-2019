package fr.game.advent.day01;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

public class GameTwoTest {
	
	private GameTwo gameTwo = new GameTwo();

	@Test
	public void testExemple1() {
		Assert.assertEquals(new Long(2), gameTwo.play(Arrays.asList(12L)));
		Assert.assertEquals(new Long(966), gameTwo.play(Arrays.asList(1969L)));
		Assert.assertEquals(new Long(50346), gameTwo.play(Arrays.asList(100756L)));
	}

	@Test
	public void testGame() {
		Assert.assertEquals(new Long(4736213), gameTwo.play());
	}
	
}
