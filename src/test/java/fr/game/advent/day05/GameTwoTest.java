package fr.game.advent.day05;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

public class GameTwoTest {
	
	private GameTwo gameTwo = new GameTwo();

	@Test
	public void testExemple1() {
		System.out.println("Test 1 - taper 8");
		Assert.assertEquals(new Integer(1), gameTwo.play(Arrays.asList(3,9,8,9,10,9,4,9,99,-1,8)));
	}

	@Test
	public void testExemple2() {
		System.out.println("Test 2 - taper 7");
		Assert.assertEquals(new Integer(1), gameTwo.play(Arrays.asList(3,9,7,9,10,9,4,9,99,-1,8)));
	}

	@Test
	public void testExemple3() {
		System.out.println("Test 3 - taper 8");
		Assert.assertEquals(new Integer(1), gameTwo.play(Arrays.asList(3,3,1108,-1,8,3,4,3,99)));
	}

	@Test
	public void testExemple4() {
		System.out.println("Test 4 - taper 7");
		Assert.assertEquals(new Integer(1), gameTwo.play(Arrays.asList(3,3,1107,-1,8,3,4,3,99)));
	}

	@Test
	public void testExemple5() {
		System.out.println("Test 5 - taper 2");
		Assert.assertEquals(new Integer(1), gameTwo.play(Arrays.asList(3,12,6,12,15,1,13,14,13,4,13,99,-1,0,1,9)));
	}

	@Test
	public void testExemple6() {
		System.out.println("Test 6 - taper 2");
		Assert.assertEquals(new Integer(1), gameTwo.play(Arrays.asList(3,3,1105,-1,9,1101,0,0,12,4,12,99,1)));
	}

	@Test
	public void testExemple7() {
		System.out.println("Test 7 - taper 8");
		Assert.assertEquals(new Integer(1000), gameTwo.play(Arrays.asList(3,21,1008,21,8,20,1005,20,22,107,8,21,20,1006,20,31,
																		1106,0,36,98,0,0,1002,21,125,20,4,20,1105,1,46,104,
																		999,1105,1,46,1101,1000,1,20,4,20,1105,1,46,98,99)));
	}

	@Test
	public void testGame() {
		Assert.assertEquals(new Integer(3071943), gameTwo.play());
	}
	
}
