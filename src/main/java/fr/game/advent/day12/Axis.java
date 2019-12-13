package fr.game.advent.day12;

import java.util.Arrays;

public class Axis {

	private int[] moons;
	private int[] velocities;

	public Axis(int c1, int c2, int c3, int c4) {
		this.moons = new int[] { c1, c2, c3, c4 };
		this.velocities = new int[] { 0, 0, 0, 0 };
	}

	public Axis() {
		this(0, 0, 0, 0);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(moons);
		result = prime * result + Arrays.hashCode(velocities);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Axis other = (Axis) obj;
		return Arrays.equals(moons, other.moons) && Arrays.equals(velocities, other.velocities);
	}

	public void doStep() {
		updateVelocities();
		applyVelocities();
	}
	
	private void applyVelocities() {
		for (int i = 0; i < moons.length; i++) {
			moons[i] = moons[i] + velocities[i];
		}
	}
		
	private void updateVelocities() {
		for (int i = 0; i < moons.length; i++) {
			int moon1 = moons[i];
			for (int j = i + 1; j < moons.length; j++) {
				int moon2 = moons[j];
				if (moon1 != moon2) {
					if (moon1 > moon2) {
						velocities[i]--;
						velocities[j]++;
					} else {
						velocities[i]++;
						velocities[j]--;
					}
				}
			}
		}
	}

}
