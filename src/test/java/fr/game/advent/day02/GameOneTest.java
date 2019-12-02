package fr.game.advent.day02;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class GameOneTest {
	
	private GameOne gameOne = new GameOne();

	@Test
	public void testExemple1() {
		List<Integer> program = Arrays.asList(1,0,0,0,99);
		program = gameOne.executeIntcodeProgram(program);
		Assert.assertEquals(Arrays.asList(2,0,0,0,99), program);
	}
	
	@Test
	public void testExemple2() {
		List<Integer> program = Arrays.asList(2,3,0,3,99);
		program = gameOne.executeIntcodeProgram(program);
		Assert.assertEquals(Arrays.asList(2,3,0,6,99), program);
	}
	
	@Test
	public void testExemple3() {
		List<Integer> program = Arrays.asList(2,4,4,5,99,0);
		program = gameOne.executeIntcodeProgram(program);
		Assert.assertEquals(Arrays.asList(2,4,4,5,99,9801), program);
	}
	
	@Test
	public void testExemple4() {
		List<Integer> program = Arrays.asList(1,1,1,4,99,5,6,0,99);
		program = gameOne.executeIntcodeProgram(program);
		Assert.assertEquals(Arrays.asList(30,1,1,4,2,5,6,0,99), program);
	}
	
	@Test
	public void testGame() {
		Assert.assertEquals(new Integer(2842648), gameOne.play());
	}
}
