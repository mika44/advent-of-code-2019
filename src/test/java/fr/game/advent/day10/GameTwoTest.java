package fr.game.advent.day10;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.stream.Collectors;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import fr.game.utils.LoggerUtils;

public class GameTwoTest {
	
	private GameTwo gameTwo = new GameTwo();
	
	@Before
	public void initLog() {
		LoggerUtils.setLevel(Level.INFO);
	}

	private List<List<Boolean>> toSpatialMap(final String casTest) {
		return Arrays.stream(casTest.split("\\n"))
										.map( s -> s.chars().mapToObj(c -> c == '#').collect(Collectors.toList()) )
										.collect(Collectors.toList());
	}
	
	
	@Test
	public void testExemple5() {
		final String casTest = 
							".#..##.###...#######\n" + 
							"##.############..##.\n" + 
							".#.######.########.#\n" + 
							".###.#######.####.#.\n" + 
							"#####.##.#.##.###.##\n" + 
							"..#####..#.#########\n" + 
							"####################\n" + 
							"#.####....###.#.#.##\n" + 
							"##.#################\n" + 
							"#####.##.###..####..\n" + 
							"..######..##.#######\n" + 
							"####.##.####...##..#\n" + 
							".#####..#.######.###\n" + 
							"##...#.##########...\n" + 
							"#.##########.#######\n" + 
							".####.#.###.###.#.##\n" + 
							"....##.##.###..#####\n" + 
							".#.#.###########.###\n" + 
							"#.#.#.#####.####.###\n" + 
							"###.##.####.##.#..##";
		Assert.assertEquals(new Integer(802), gameTwo.play(toSpatialMap(casTest)));
	}

	
	@Test
	public void testGame() {
		Assert.assertEquals(new Integer(502), gameTwo.play());
	}

}
