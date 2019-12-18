package fr.game.advent.day16;

import java.util.Arrays;
import java.util.logging.Level;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import fr.game.utils.LoggerUtils;

public class GameTwoTest {
	
	private GameTwo gameTwo = new GameTwo();
	
	@BeforeClass
	public static void initLog() {
		LoggerUtils.setLevel(Level.INFO);
	}

	@Test
	public void testExemple1() {
		String casTest = "03036732577212944063491565474664";
		String resultatAttendu = "84462026";
		executerEtVerifierResultat(casTest, resultatAttendu);
	}

	@Test
	public void testExemple2() {
		String casTest = "02935109699940807407585447034323";
		String resultatAttendu = "78725270";
		executerEtVerifierResultat(casTest, resultatAttendu);
	}

	@Test
	public void testExemple3() {
		String casTest = "03081770884921959731165446850517";
		String resultatAttendu = "53553731";
		executerEtVerifierResultat(casTest, resultatAttendu);
	}

	private void executerEtVerifierResultat(String casTest, String resultatAttendu) {
		Assert.assertEquals(resultatAttendu, gameTwo.play(Arrays.asList(casTest)));
	}
	

	@Test
	public void testGame() {
		LoggerUtils.setLevel(Level.WARNING);
		Assert.assertEquals("36627552", gameTwo.play());
	}
}
