package fr.game.advent.day05.program.instructions;

public class Parameter {
	
	private ParameterUsage parameterUsage;
	private Mode mode;
	private int offsetIP;
	
	public Parameter(ParameterUsage parameterUsage, Mode mode, int offsetIP) {
		super();
		this.parameterUsage = parameterUsage;
		this.mode = mode;
		this.offsetIP = offsetIP;
	}

	public ParameterUsage getParameterUsage() {
		return parameterUsage;
	}

	public Mode getMode() {
		return mode;
	}

	public int getOffsetIP() {
		return offsetIP;
	}

	@Override
	public String toString() {
		return String.format("Parameter [parameterUsage=%s, mode=%s, offsetIP=%s]", parameterUsage, mode, offsetIP);
	}
}
