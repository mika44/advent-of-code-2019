package fr.game.advent.day12;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import fr.game.utils.AbstractGame;
import fr.game.utils.FileUtils;

public class GameTwo extends AbstractGame<SpatialCoordinates, Long> {
	
	private static final String INPUT_FILENAME = "day12/input-day12-1";
	
	
	public GameTwo() {
		super(FileUtils::getListFromFile, INPUT_FILENAME, SpatialCoordinates::getSpatialCoordinates);
	}

	private class Instant {
		private List<SpatialCoordinates> moons;
		private List<SpatialCoordinates> velocities;
		
		private Instant(List<SpatialCoordinates> moons, List<SpatialCoordinates> velocities, long step) {
			this.moons = moons.stream().map(SpatialCoordinates::cloneSpatialCoordinates).collect(Collectors.toList());
			this.velocities = velocities.stream().map(SpatialCoordinates::cloneSpatialCoordinates).collect(Collectors.toList());
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getOuterType().hashCode();
			result = prime * result + ((moons == null) ? 0 : moons.hashCode());
			result = prime * result + ((velocities == null) ? 0 : velocities.hashCode());
			return result;
		}
		
		public long energy() {
			long totalEnergy = 0;
			for (int i = 0; i < moons.size(); i++) {
				long pot = moons.get(i).energy();
				long kin = velocities.get(i).energy();
				totalEnergy = totalEnergy * 1010101 + pot * kin;
			}
			return totalEnergy;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Instant other = (Instant) obj;
			if (!getOuterType().equals(other.getOuterType()))
				return false;
			if (moons == null) {
				if (other.moons != null)
					return false;
			} else if (!moons.equals(other.moons))
				return false;
			if (velocities == null) {
				if (other.velocities != null)
					return false;
			} else if (!velocities.equals(other.velocities))
				return false;
			return true;
		}

		private GameTwo getOuterType() {
			return GameTwo.this;
		}

		
	}
	
	@Override
	public Long play(List<SpatialCoordinates> moons) {
		long step = 0;
		List<SpatialCoordinates> velocities = moons.stream().map(s -> new SpatialCoordinates()).collect(Collectors.toList());
		Instant currentInstant = new Instant(moons, velocities, step);
		Map<Long, Long> stepsByEnergies = new HashMap<>();
		long currentEnergy = currentInstant.energy();
		while (stepsByEnergies.get(currentEnergy) == null && step < 4686774924L) {//!previousInstants.contains(currentInstant)) {
			stepsByEnergies.put(currentEnergy, step);
			step++;
			velocities = updateVelocities(velocities, moons);
			moons = applyVelocities(moons, velocities);
			currentInstant = new Instant(moons, velocities, step);
			currentEnergy = currentInstant.energy();
			if (step % 1000000 == 0) System.out.println(step);
		}
		return step;
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
