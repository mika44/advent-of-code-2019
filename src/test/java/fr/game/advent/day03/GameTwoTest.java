package fr.game.advent.day03;

import java.util.Arrays;
import java.util.stream.Collectors;

import org.junit.Assert;
import org.junit.Test;

public class GameTwoTest {
	
	private GameTwo gameTwo = new GameTwo();

	@Test
	public void testExemple1() {
		Assert.assertEquals(new Integer(610), gameTwo.play(
				  										Arrays.asList("R75,D30,R83,U83,L12,D49,R71,U7,L72", "U62,R66,U55,R34,D71,R55,D58,R83")
				  										.stream()
				  										.map(GameTwo.MAPPER)
				  										.collect(Collectors.toList())
				  									   )
				);
	}
	
	@Test
	public void testExemple2() {
		Assert.assertEquals(new Integer(410), gameTwo.play(
				  										Arrays.asList("R98,U47,R26,D63,R33,U87,L62,D20,R33,U53,R51", "U98,R91,D20,R16,D67,R40,U7,R15,U6,R7")
				  										.stream()
				  										.map(GameTwo.MAPPER)
				  										.collect(Collectors.toList())
				  									   )
				);
	}
	
	@Test
	public void testGame() {
		Assert.assertEquals(new Integer(35194), gameTwo.play());
	}
}
