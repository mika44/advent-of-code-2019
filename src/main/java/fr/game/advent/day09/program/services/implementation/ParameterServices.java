package fr.game.advent.day09.program.services.implementation;

import fr.game.advent.day09.program.model.Parameter;
import fr.game.advent.day09.program.model.ParameterUsage;
import fr.game.advent.day09.program.model.Program;
import fr.game.advent.day09.program.services.ParameterServicesI;

public class ParameterServices implements ParameterServicesI {

	@Override
	public Long getParameterValue(Parameter parameter, Program program) {
		if (ParameterUsage.TO_READ.equals(parameter.getParameterUsage())) {
			return getParameterValueToRead(parameter, program);
		} else {
			return getParameterValueToWrite(parameter, program);
		}
	}

	@Override
	public String parameterToString(Parameter parameter, Program program) {
		if (ParameterUsage.TO_WRITE.equals(parameter.getParameterUsage())) {
			return parameterToString(new Parameter(ParameterUsage.TO_READ, parameter.getMode(), parameter.getOffsetIP()), program);
		}
		StringBuffer sb = new StringBuffer();
		switch (parameter.getMode()) {
			case POSITION_MODE: 
				sb.append("[").append(program.readMemoryIPOffset(parameter.getOffsetIP())).append("]"); 
				break;
			case RELATIVE_MODE:
				Long offsetBr = program.readMemoryIPOffset(parameter.getOffsetIP());
				sb.append("[BR+").append(offsetBr).append("=").append(program.getRelativeBase() + offsetBr).append("]"); 
				break;
			default: break;
		}
		sb.append("(").append(getParameterValue(parameter, program)).append(")");
		return sb.toString();
	}

	
	private Long getParameterValueToRead(Parameter parameter, Program program) {
		Long operande = program.readMemoryIPOffset(parameter.getOffsetIP());
		switch (parameter.getMode()) {
			case RELATIVE_MODE: return program.readMemoryRelativeBaseOffset(operande);
			case POSITION_MODE: return program.readMemoryDirectAccess(operande);
			default : /*IMMEDIATE_MODE*/ return operande;
		}
	}

	private Long getParameterValueToWrite(Parameter parameter, Program program) {
		Long operande = program.readMemoryIPOffset(parameter.getOffsetIP());
		switch (parameter.getMode()) {
			case RELATIVE_MODE: return program.getRelativeBase() + operande;
			default: /*POSITION_MODE*/ return operande;
		}
	}
}
