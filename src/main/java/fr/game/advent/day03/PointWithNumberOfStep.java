package fr.game.advent.day03;

public class PointWithNumberOfStep {
	private Point point;
	private Integer numberOfStep;

	public PointWithNumberOfStep(Point point, Integer numberOfStep) {
		super();
		this.point = point;
		this.numberOfStep = numberOfStep;
	}
	
	public Point getPoint() {
		return point;
	}

	public Integer getNumberOfStep() {
		return numberOfStep;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((point == null) ? 0 : point.hashCode());
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
		PointWithNumberOfStep other = (PointWithNumberOfStep) obj;
		if (point == null) {
			if (other.point != null)
				return false;
		} else if (!point.equals(other.point))
			return false;
		return true;
	}
}
