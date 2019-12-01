package fr.game.advent.day01;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

public class GameOneTest {
	
	private GameOne gameOne = new GameOne();

	@Test
	public void testExemple1() {
		Assert.assertEquals(new Integer(3), gameOne.play(Arrays.asList(1, -2, 3, 1)));
	}
	
	@Test
	public void testGame() {
		Assert.assertEquals(new Integer(474), gameOne.play());
	}

}
