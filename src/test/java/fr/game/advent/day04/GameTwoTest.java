package fr.game.advent.day04;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

public class GameTwoTest {
	
	private GameTwo gameTwo = new GameTwo();

	@Test
	public void testExemple1() {
		Assert.assertEquals(new Long(1), gameTwo.play(Arrays.asList(112233, 112233)));
		Assert.assertEquals(new Long(0), gameTwo.play(Arrays.asList(123444, 123444)));
		Assert.assertEquals(new Long(1), gameTwo.play(Arrays.asList(111122, 111122)));
	}

	@Test
	public void testGame() {
		Assert.assertEquals(new Long(1172), gameTwo.play());
	}
	
}
