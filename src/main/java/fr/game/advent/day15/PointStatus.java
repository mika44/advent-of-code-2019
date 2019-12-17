package fr.game.advent.day15;

public enum PointStatus {
	VOID	("."),
	WALL	("#"),
	OS		("O"),
	UNKNOWN ("-")
	;
	
	private String printStatus;

	private PointStatus(String printStatus) {
		this.printStatus = printStatus;
	}

	public String getPrintStatus() {
		return printStatus;
	}
}
