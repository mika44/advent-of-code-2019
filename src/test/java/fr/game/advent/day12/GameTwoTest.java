package fr.game.advent.day12;

import java.util.Arrays;
import java.util.logging.Level;
import java.util.stream.Collectors;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import fr.game.utils.LoggerUtils;

public class GameTwoTest {
	
	private GameTwo gameThree = new GameTwo();

	@Before
	public void initLog() {
		LoggerUtils.setLevel(Level.WARNING);
	}

	@Test
	public void testExemple1() {
		Assert.assertEquals(new Long(2772L), gameThree.play(
				  										Arrays.asList("<x=-1, y=0, z=2>", "<x=2, y=-10, z=-7>", "<x=4, y=-8, z=8>", "<x=3, y=5, z=-1>")
				  										.stream()
				  										.map(GameTwo.MAPPER)
				  										.collect(Collectors.toList())
				  									   )
				);
	}
	

	@Test
	public void testExemple2() {
		Assert.assertEquals(new Long(4686774924L), gameThree.play(
				  										Arrays.asList("<x=-8, y=-10, z=0>", "<x=5, y=5, z=10>", "<x=2, y=-7, z=3>", "<x=9, y=-8, z=-3>")
				  										.stream()
				  										.map(GameTwo.MAPPER)
				  										.collect(Collectors.toList())
				  									   )
				);
	}
	

	@Test
	public void testGame() {
		Assert.assertEquals(new Long(506359021038056L), gameThree.play());
	}
}
