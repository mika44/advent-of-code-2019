package fr.game.advent.day12;

import java.util.Arrays;

public class Axis {

	private long[] moons;
	private long[] velocities;

	public Axis(Axis other) {
		this.moons = new long[] { other.moons[0], other.moons[1], other.moons[2], other.moons[3] };
		this.velocities = new long[] { other.velocities[0], other.velocities[1], other.velocities[2], other.velocities[3]  };
	}

	public Axis(long c1, long c2, long c3, long c4) {
		this.moons = new long[] { c1, c2, c3, c4 };
		this.velocities = new long[] { 0L, 0L, 0L, 0L};
	}

	public Axis() {
		this(0L, 0L, 0L, 0L);
	}

	public long getMoon(int i) {
		return moons[i]; 
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
			long moon1 = moons[i];
			for (int j = i + 1; j < moons.length; j++) {
				long moon2 = moons[j];
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
