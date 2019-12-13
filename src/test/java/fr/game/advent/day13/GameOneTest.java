package fr.game.advent.day13;

import java.util.logging.Level;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import fr.game.utils.LoggerUtils;

public class GameOneTest {
	
	private GameOne gameOne = new GameOne();
	
	@Before
	public void initLog() {
		LoggerUtils.setLevel(Level.INFO);
	}

	@Test
	public void testGame() {
		Assert.assertEquals(new Long(216), gameOne.play());
	}

}
