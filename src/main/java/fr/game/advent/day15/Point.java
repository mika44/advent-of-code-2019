package fr.game.advent.day15;

import java.util.Comparator;
import java.util.Objects;

public class Point implements Comparable<Point> {

	private long x;
	private long y;

	public Point(long x, long y) {
		super();
		this.x = x;
		this.y = y;
	}

	public long getX() {
		return x;
	}

	public void setX(long x) {
		this.x = x;
	}

	public long getY() {
		return y;
	}

	public void setY(long y) {
		this.y = y;
	}

	@Override
	public int hashCode() {
		return Objects.hash(x, y);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Point other = (Point) obj;
		return x == other.x && y == other.y;
	}

	@Override
	public String toString() {
		return String.format("Point [x=%s, y=%s]", x, y);
	}

	@Override
	public int compareTo(Point o) {
		return Comparator.comparing(Point::getX).thenComparing(Point::getY).compare(this, o);
	}

}
