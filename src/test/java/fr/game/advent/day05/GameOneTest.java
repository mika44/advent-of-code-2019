package fr.game.advent.day05;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

public class GameOneTest {
	
	private GameOne gameOne = new GameOne();

	@Test
	public void testExemple1() {
		Assert.assertEquals(null, gameOne.play(Arrays.asList(1002,4,3,4,33)));
	}
	
	public void testExemple2() {
		Assert.assertEquals(null, gameOne.play(Arrays.asList(3,5,4,5,99,-1)));
	}

	@Test
	public void testGame() {
		Assert.assertEquals(new Integer(16225258), gameOne.play());
	}

}
