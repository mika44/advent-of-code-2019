package fr.game.advent.day04;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

public class GameOneTest {
	
	private GameOne gameOne = new GameOne();

	@Test
	public void testExemple1() {
		Assert.assertEquals(new Long(1), gameOne.play(Arrays.asList(111111, 111111)));
		Assert.assertEquals(new Long(0), gameOne.play(Arrays.asList(223450, 223450)));
		Assert.assertEquals(new Long(0), gameOne.play(Arrays.asList(123789, 123789)));
	}
	
	@Test
	public void testGame() {
		Assert.assertEquals(new Long(1729), gameOne.play());
	}

}
