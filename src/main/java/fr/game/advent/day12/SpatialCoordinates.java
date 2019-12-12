package fr.game.advent.day12;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.RegExUtils;

public class SpatialCoordinates {

	private int x;
	private int y;
	private int z;

	public SpatialCoordinates() {
		this(0, 0, 0);
	}

	public SpatialCoordinates(int x, int y, int z) {
		super();
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getZ() {
		return z;
	}

	public void setZ(int z) {
		this.z = z;
	}

	public int energy() {
		return Math.abs(x) + Math.abs(y) + Math.abs(z);
	}
	
	public String toStringEnergy() {
		return String.format("%d + %d + %d = %d", Math.abs(x), Math.abs(y), Math.abs(z), energy());
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
		result = prime * result + z;
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
		SpatialCoordinates other = (SpatialCoordinates) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		if (z != other.z)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return String.format("<x=%s, y=%s, z=%s>", x, y, z);
	}

	
	public static SpatialCoordinates getSpatialCoordinates(String input) {
		List<Integer> coordinates = Arrays.stream(RegExUtils.removeAll(input, "<|>|=|x|y|z| ").split(",")).map(Integer::valueOf).collect(Collectors.toList());
		return new SpatialCoordinates(coordinates.get(0), coordinates.get(1), coordinates.get(2));
	}
}
