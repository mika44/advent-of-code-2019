package fr.game.advent.day12;

import java.util.List;
import java.util.stream.Collectors;

import fr.game.utils.AbstractGame;
import fr.game.utils.FileUtils;

public class GameOne extends AbstractGame<SpatialCoordinates, Integer> {
	
	private static final int DEFAULT_MAX_STEP = 1000;
	private static final String INPUT_FILENAME = "day12/input-day12-1";
	private int maxStep;
	
	
	
	public GameOne() {
		super(FileUtils::getListFromFile, INPUT_FILENAME, SpatialCoordinates::getSpatialCoordinates);
		maxStep = DEFAULT_MAX_STEP;
	}

	public void setMaxStep(int maxStep) {
		this.maxStep = maxStep;
	}

	@Override
	public Integer play(List<SpatialCoordinates> moons) {
		int step = 0;
		// Vélocités initialisées à 0
		List<SpatialCoordinates> velocities = moons.stream().map(s -> new SpatialCoordinates()).collect(Collectors.toList());
		print(step, moons, velocities);

		while (step < maxStep) {
			step++;
			velocities = updateVelocities(velocities, moons);
			moons = applyVelocities(moons, velocities);
			print(step, moons, velocities);
		}
		printEnergy(step, moons, velocities);
		return totalEnergy(moons, velocities);
	}

	private Integer totalEnergy(List<SpatialCoordinates> moons, List<SpatialCoordinates> velocities) {
		int totalEnergy = 0;
		for (int i = 0; i < moons.size(); i++) {
			int pot = moons.get(i).energy();
			int kin = velocities.get(i).energy();
			totalEnergy = totalEnergy + pot * kin;
		}
		return totalEnergy;
	}

	private void printEnergy(int step, List<SpatialCoordinates> moons, List<SpatialCoordinates> velocities) {
		if (step < 11 || (step > 10 && step <= 100 && step % 10 == 0) || (step > 100 && step <= 1000 && step % 100 == 0)) {
			System.out.println("Energy after " + step + " steps : ");
			for (int i = 0; i < moons.size(); i++) {
				int pot = moons.get(i).energy();
				int kin = velocities.get(i).energy();
				System.out.println(String.format("pot=%s;  kin=%s; total: %d * %d = %d", moons.get(i).toStringEnergy(), velocities.get(i).toStringEnergy(), pot, kin, pot * kin));
			}
			System.out.println(String.format("Sum of total energy: %d", totalEnergy(moons, velocities)));
		}
	}

	private void print(int step, List<SpatialCoordinates> moons, List<SpatialCoordinates> velocities) {
		if (step < 11 || (step > 10 && step <= 100 && step % 10 == 0) || (step > 100 && step <= 1000 && step % 100 == 0)) {
			System.out.println("After " + step + " steps : ");
			for (int i = 0; i < moons.size(); i++) {
				System.out.println(String.format("pos=%s, vel=%s", moons.get(i), velocities.get(i)));
			}
		}
	}

	private List<SpatialCoordinates> applyVelocities(List<SpatialCoordinates> moons, List<SpatialCoordinates> velocities) {
		for (int i = 0; i < moons.size(); i++) {
			SpatialCoordinates moon = moons.get(i);
			SpatialCoordinates velocity = velocities.get(i);
			moon.setX(moon.getX() + velocity.getX());
			moon.setY(moon.getY() + velocity.getY());
			moon.setZ(moon.getZ() + velocity.getZ());
		}
		return moons;
	}

	private List<SpatialCoordinates> updateVelocities(List<SpatialCoordinates> velocities, List<SpatialCoordinates> moons) {
		for (int i = 0; i < moons.size(); i++) {
			SpatialCoordinates moon1 = moons.get(i);
			SpatialCoordinates velocity1 = velocities.get(i);
			for (int j = i + 1; j < moons.size(); j++) {
				SpatialCoordinates moon2 = moons.get(j);
				SpatialCoordinates velocity2 = velocities.get(j);
				if (moon1.getX() != moon2.getX()) {
					if (moon1.getX() > moon2.getX()) {
						velocity1.setX(velocity1.getX() - 1);
						velocity2.setX(velocity2.getX() + 1);
					} else {
						velocity1.setX(velocity1.getX() + 1);
						velocity2.setX(velocity2.getX() - 1);
					}
				}
				if (moon1.getY() != moon2.getY()) {
					if (moon1.getY() > moon2.getY()) {
						velocity1.setY(velocity1.getY() - 1);
						velocity2.setY(velocity2.getY() + 1);
					} else {
						velocity1.setY(velocity1.getY() + 1);
						velocity2.setY(velocity2.getY() - 1);
					}
				}
				if (moon1.getZ() != moon2.getZ()) {
					if (moon1.getZ() > moon2.getZ()) {
						velocity1.setZ(velocity1.getZ() - 1);
						velocity2.setZ(velocity2.getZ() + 1);
					} else {
						velocity1.setZ(velocity1.getZ() + 1);
						velocity2.setZ(velocity2.getZ() - 1);
					}
				}
			}
		}
		return velocities;
	}


}
