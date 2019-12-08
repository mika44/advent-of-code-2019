package fr.game.advent.day05;

import java.io.ByteArrayInputStream;
import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

public class GameTwoTest {
	
	private GameTwo gameTwo = new GameTwo();

	@Test
	public void testExemple1() {
		System.setIn(new ByteArrayInputStream("8".getBytes()));
		Assert.assertEquals(new Integer(1), gameTwo.play(Arrays.asList(3,9,8,9,10,9,4,9,99,-1,8)));
//		System.setIn(new ByteArrayInputStream("6".getBytes()));
//		Assert.assertEquals(new Integer(0), gameTwo.play(Arrays.asList(3,9,8,9,10,9,4,9,99,-1,8)));
//		System.setIn(new ByteArrayInputStream("89".getBytes()));
//		Assert.assertEquals(new Integer(0), gameTwo.play(Arrays.asList(3,9,8,9,10,9,4,9,99,-1,8)));
	}

	@Test
	public void testExemple2() {
		System.setIn(new ByteArrayInputStream("7".getBytes()));
		Assert.assertEquals(new Integer(1), gameTwo.play(Arrays.asList(3,9,7,9,10,9,4,9,99,-1,8)));
		System.setIn(new ByteArrayInputStream("-86".getBytes()));
		Assert.assertEquals(new Integer(1), gameTwo.play(Arrays.asList(3,9,7,9,10,9,4,9,99,-1,8)));
		System.setIn(new ByteArrayInputStream("886".getBytes()));
		Assert.assertEquals(new Integer(0), gameTwo.play(Arrays.asList(3,9,7,9,10,9,4,9,99,-1,8)));
	}

	@Test
	public void testExemple3() {
		System.setIn(new ByteArrayInputStream("8".getBytes()));
		Assert.assertEquals(new Integer(1), gameTwo.play(Arrays.asList(3,3,1108,-1,8,3,4,3,99)));
		System.setIn(new ByteArrayInputStream("7".getBytes()));
		Assert.assertEquals(new Integer(0), gameTwo.play(Arrays.asList(3,3,1108,-1,8,3,4,3,99)));
	}

	@Test
	public void testExemple4() {
		System.setIn(new ByteArrayInputStream("7".getBytes()));
		Assert.assertEquals(new Integer(1), gameTwo.play(Arrays.asList(3,3,1107,-1,8,3,4,3,99)));
		System.setIn(new ByteArrayInputStream("854".getBytes()));
		Assert.assertEquals(new Integer(0), gameTwo.play(Arrays.asList(3,3,1107,-1,8,3,4,3,99)));
	}

	@Test
	public void testExemple5() {
		System.setIn(new ByteArrayInputStream("2".getBytes()));
		Assert.assertEquals(new Integer(1), gameTwo.play(Arrays.asList(3,12,6,12,15,1,13,14,13,4,13,99,-1,0,1,9)));
	}

	@Test
	public void testExemple6() {
		System.setIn(new ByteArrayInputStream("2".getBytes()));
		Assert.assertEquals(new Integer(1), gameTwo.play(Arrays.asList(3,3,1105,-1,9,1101,0,0,12,4,12,99,1)));
	}

	@Test
	public void testExemple7() {
		System.setIn(new ByteArrayInputStream("8".getBytes()));
		Assert.assertEquals(new Integer(1000), gameTwo.play(Arrays.asList(3,21,1008,21,8,20,1005,20,22,107,8,21,20,1006,20,31,1106,0,36,98,0,0,1002,21,125,20,4,20,1105,1,46,104,999,1105,1,46,1101,1000,1,20,4,20,1105,1,46,98,99)));
		System.setIn(new ByteArrayInputStream("3".getBytes()));
		Assert.assertEquals(new Integer(999), gameTwo.play(Arrays.asList(3,21,1008,21,8,20,1005,20,22,107,8,21,20,1006,20,31,1106,0,36,98,0,0,1002,21,125,20,4,20,1105,1,46,104,999,1105,1,46,1101,1000,1,20,4,20,1105,1,46,98,99)));
		System.setIn(new ByteArrayInputStream("7548".getBytes()));
		Assert.assertEquals(new Integer(1001), gameTwo.play(Arrays.asList(3,21,1008,21,8,20,1005,20,22,107,8,21,20,1006,20,31,1106,0,36,98,0,0,1002,21,125,20,4,20,1105,1,46,104,999,1105,1,46,1101,1000,1,20,4,20,1105,1,46,98,99)));
	}

	@Test
	public void testGame() {
		System.setIn(new ByteArrayInputStream("5".getBytes()));
		Assert.assertEquals(new Integer(2808771), gameTwo.play());
	}
	
}
