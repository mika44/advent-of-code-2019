package fr.game.advent.day15;

import java.util.logging.Level;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import fr.game.utils.LoggerUtils;

public class GameTwoTest {
	
	private GameTwo gameTwo = new GameTwo();
	
	@Before
	public void initLog() {
		LoggerUtils.setLevel(Level.WARNING);
	}

	@Test
	public void testGame() {
		Assert.assertEquals(new Long(350), gameTwo.play());
	}

}
