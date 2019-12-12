package fr.game.advent.day12;

import java.util.Arrays;
import java.util.stream.Collectors;

import org.junit.Assert;
import org.junit.Test;

public class GameOneTest {
	
	private GameOne gameOne = new GameOne();

	@Test
	public void testExemple1() {
		gameOne.setMaxStep(10);
		Assert.assertEquals(new Integer(179), gameOne.play(
				  										Arrays.asList("<x=-1, y=0, z=2>", "<x=2, y=-10, z=-7>", "<x=4, y=-8, z=8>", "<x=3, y=5, z=-1>")
				  										.stream()
				  										.map(SpatialCoordinates::getSpatialCoordinates)
				  										.collect(Collectors.toList())
				  									   )
				);
	}
	

	@Test
	public void testExemple2() {
		gameOne.setMaxStep(100);
		Assert.assertEquals(new Integer(1940), gameOne.play(
				  										Arrays.asList("<x=-8, y=-10, z=0>",	"<x=5, y=5, z=10>", "<x=2, y=-7, z=3>", "<x=9, y=-8, z=-3>")
				  										.stream()
				  										.map(SpatialCoordinates::getSpatialCoordinates)
				  										.collect(Collectors.toList())
				  									   )
				);
	}
	

	@Test
	public void testGame() {
		Assert.assertEquals(new Integer(8538), gameOne.play());
	}
}
