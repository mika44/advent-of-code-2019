package fr.game.advent.day02;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

public class GameTwoTest {
	
	private GameTwo gameTwo = new GameTwo();

	@Test
	public void testExemple1() {
		Assert.assertEquals("fgij", gameTwo.play(Arrays.asList("abcde", "fghij", "klmno", "pqrst", "fguij", "axcye", "wvxyz")));
	}

	@Test
	public void testGame() {
		Assert.assertEquals("lnfqdscwjyteorambzuchrgpx", gameTwo.play());
	}
}
