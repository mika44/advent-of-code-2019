package fr.game.advent.day16;

import java.util.Arrays;
import java.util.logging.Level;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import fr.game.utils.LoggerUtils;

public class GameOneTestBis {
	
	private GameTwo gameOne = new GameTwo();
	
	@BeforeClass
	public static void initLog() {
		LoggerUtils.setLevel(Level.INFO);
	}

	@Test
	public void testExemple1() {
		String casTest = "12345678";
		String resultatAttendu = "01029498";
		gameOne.setPhaseNumberToReach(4);
		executerEtVerifierResultat(casTest, resultatAttendu);
	}

	@Test
	public void testExemple2() {
		String casTest = "80871224585914546619083218645595";
		String resultatAttendu = "24176176";
		gameOne.setPhaseNumberToReach(100);
		executerEtVerifierResultat(casTest, resultatAttendu);
	}

	@Test
	public void testExemple3() {
		String casTest = "19617804207202209144916044189917";
		String resultatAttendu = "73745418";
		gameOne.setPhaseNumberToReach(100);
		executerEtVerifierResultat(casTest, resultatAttendu);
	}

	@Test
	public void testExemple4() {
		String casTest = "69317163492948606335995924319873";
		String resultatAttendu = "52432133";
		gameOne.setPhaseNumberToReach(100);
		executerEtVerifierResultat(casTest, resultatAttendu);
	}

	private void executerEtVerifierResultat(String casTest, String resultatAttendu) {
		Assert.assertEquals(resultatAttendu, gameOne.play(Arrays.asList(casTest)));
	}
	

	@Test
	public void testGame() {
		LoggerUtils.setLevel(Level.INFO);
		gameOne.setPhaseNumberToReach(100);
		Assert.assertEquals("36627552", gameOne.play());
	}
}
