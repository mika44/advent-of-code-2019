package fr.game.advent.day09.program.services;

import fr.game.advent.day09.program.model.Parameter;
import fr.game.advent.day09.program.model.Program;

public interface ParameterServicesI {

	Long getParameterValue(Parameter parameter, Program program);

	String parameterToString(Parameter parameter, Program program);

}