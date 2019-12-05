package fr.game.advent.day05;

import java.io.ByteArrayInputStream;
import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

public class GameOneTest {
	
	private GameOne gameOne = new GameOne();

	@Test
	public void testExemple1() {
		Assert.assertEquals(null, gameOne.play(Arrays.asList(1002,4,3,4,33)));
	}
	
	@Test
	public void testExemple2() {
		System.setIn(new ByteArrayInputStream("453".getBytes()));
		Assert.assertEquals(new Integer(453), gameOne.play(Arrays.asList(3,5,4,5,99,-1)));
	}

	@Test
	public void testGame() {
		System.setIn(new ByteArrayInputStream("1".getBytes()));
		Assert.assertEquals(new Integer(16225258), gameOne.play());
	}

}
