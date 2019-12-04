package fr.game.advent.day05;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

public class GameOneTest {
	
	private GameOne gameOne = new GameOne();

	@Test
	public void testExemple1() {
		Assert.assertEquals(new Long(2), gameOne.play(Arrays.asList(12L)));
		Assert.assertEquals(new Long(2), gameOne.play(Arrays.asList(14L)));
		Assert.assertEquals(new Long(654), gameOne.play(Arrays.asList(1969L)));
		Assert.assertEquals(new Long(33583), gameOne.play(Arrays.asList(100756L)));
	}
	
	@Test
	public void testGame() {
		Assert.assertEquals(new Long(3159380), gameOne.play());
	}

}
