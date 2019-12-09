package fr.game.advent.day09;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.stream.Collectors;

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
	public void testExemple1() {
		List<Long> inputCodeProgram = Arrays.asList(109,1,204,-1,1001,100,1,100,1008,100,16,101,1006,101,0,99).stream().map(Long::new).collect(Collectors.toList());
		Assert.assertEquals(new Long(99), gameOne.play(inputCodeProgram));
	}
	
	@Test
	public void testExemple2() {
		List<Long> inputCodeProgram = Arrays.asList(1102,34915192,34915192,7,4,7,99,0).stream().map(Long::new).collect(Collectors.toList());
		Assert.assertEquals(new Long(1219070632396864L), gameOne.play(inputCodeProgram));
	}

	
	@Test
	public void testExemple3() {
		List<Long> inputCodeProgram = Arrays.asList(104L,1125899906842624L,99L).stream().map(Long::new).collect(Collectors.toList());
		Assert.assertEquals(new Long(1125899906842624L), gameOne.play(inputCodeProgram));
	}
	
	@Test
	public void testGame() {
		Assert.assertEquals(new Long(2171728567L), gameOne.play());
	}

}
