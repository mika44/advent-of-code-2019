package fr.game.advent.day06;

import java.util.Arrays;
import java.util.stream.Collectors;

import org.junit.Assert;
import org.junit.Test;

public class GameTwoTest {
	
	private GameTwo gameTwo = new GameTwo();

	@Test
	public void testExemple1() {
		String[] useCase = 
				("COM)B\n" + 
				"B)C\n" + 
				"C)D\n" + 
				"D)E\n" + 
				"E)F\n" + 
				"B)G\n" + 
				"G)H\n" + 
				"D)I\n" + 
				"E)J\n" + 
				"J)K\n" + 
				"K)L\n"	+ 
				"K)YOU\n" + 
				"I)SAN")
				.split("\\n");
		Assert.assertEquals(new Integer(4), gameTwo.play(
				  										Arrays.stream(useCase)
				  										.map(Relationship::fromString)
				  										.collect(Collectors.toList())
				  									   )
				);
	}
	
	@Test
	public void testGame() {
		Assert.assertEquals(new Integer(517), gameTwo.play());
	}
}
