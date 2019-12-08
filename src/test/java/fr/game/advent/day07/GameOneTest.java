package fr.game.advent.day07;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

public class GameOneTest {
	
	private GameOne gameOne = new GameOne();

	@Test
	public void testExemple1() {
		Assert.assertEquals(new Integer(43210), gameOne.play(Arrays.asList(3,15,3,16,1002,16,10,16,1,16,15,15,4,15,99,0,0)));
		Assert.assertEquals(new Integer(54321), gameOne.play(Arrays.asList(3,23,3,24,1002,24,10,24,1002,23,-1,23,101,5,23,23,1,24,23,23,4,23,99,0,0)));
		Assert.assertEquals(new Integer(65210), gameOne.play(Arrays.asList(3,31,3,32,1002,32,10,32,1001,31,-2,31,1007,31,0,33,1002,33,7,33,1,33,31,31,1,32,31,31,4,31,99,0,0,0)));
	}
	
	@Test
	public void testGame() {
		Assert.assertEquals(new Integer(30940), gameOne.play());
	}

}
