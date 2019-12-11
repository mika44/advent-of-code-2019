package fr.game.advent.day09.program.services.implementation;

import java.util.logging.Logger;

import fr.game.advent.day09.program.model.Parameter;
import fr.game.advent.day09.program.model.Program;
import fr.game.advent.day09.program.services.ExecutionI;
import fr.game.advent.day09.program.services.ParameterServicesI;
import fr.game.utils.LoggerUtils;

public abstract class Execution implements ExecutionI {
	
	protected Logger log = LoggerUtils.getLogger();
	protected ParameterServicesI parameterServices = new ParameterServices();

	protected Long[] getParametersValues(Program program, Parameter[] parameters) {
		Long[] operandes = new Long[parameters.length];
		for (int i = 0; i < operandes.length; i++) {
			operandes[i] = parameterServices.getParameterValue(parameters[i], program);
		}
		return operandes;
	}

	protected abstract String logInfo(Program program, Parameter[] parameters, Long[] operandes);
	
	protected abstract Long execute(Program program, Parameter[] parameters, Long[] operandes);
	
	@Override
	public Long execute(Program program, Parameter[] parameters) {
		Long[] operandes = getParametersValues(program, parameters);
		log.info(logInfo(program, parameters, operandes));
		return execute(program, parameters, operandes);
	}

}
