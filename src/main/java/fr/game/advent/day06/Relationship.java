package fr.game.advent.day06;

public class Relationship {

	private String center;
	private String inOrbitAround;

	private Relationship(String center, String inOrbitAround) {
		super();
		this.center = center;
		this.inOrbitAround = inOrbitAround;
	}

	public String getCenter() {
		return center;
	}

	public String getInOrbitAround() {
		return inOrbitAround;
	}

	public static Relationship fromString(String relationshipString) {
		String[] splitRelationshipString = relationshipString.split("\\)");
		return new Relationship(splitRelationshipString[0], splitRelationshipString[1]);
	}

}
