package fr.game.advent.day09.program.model;

public class Parameter {
	
	private ParameterUsage parameterUsage;
	private Mode mode;
	private Long offsetIP;
	
	public Parameter(ParameterUsage parameterUsage, Mode mode, Long offsetIP) {
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

	public Long getOffsetIP() {
		return offsetIP;
	}

	@Override
	public String toString() {
		return String.format("Parameter [parameterUsage=%s, mode=%s, offsetIP=%s]", parameterUsage, mode, offsetIP);
	}
}
