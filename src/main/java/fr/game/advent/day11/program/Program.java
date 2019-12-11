package fr.game.advent.day11.program;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import fr.game.advent.day11.program.instructions.Instruction;
import fr.game.advent.day11.program.instructions.Parameter;
import fr.game.advent.day11.program.instructions.ParameterUsage;
import fr.game.utils.LoggerUtils;

public class Program {
	
	private static final Logger LOGGER = LoggerUtils.getLogger();

	private String name;
	
	private InputStream in;
	private OutputStream out;
	private BufferedReader reader;
	private PrintWriter writer;

	private Map<Long, Long> memory;
	private Long instructionPointer;
	private Long relativeBase;

	public Program(List<Long> memory, InputStream in) {
		this(memory, in, System.out);
	}

	public Program(String name, List<Long> memory, InputStream in, OutputStream out) {
		this(memory, in, out);
		this.name = name;
	}

	public Program(List<Long> memory, InputStream in, OutputStream out) {
		this.in = in;
		this.out = out;
		this.reader = new BufferedReader(new InputStreamReader(in));
		this.writer = new PrintWriter(new OutputStreamWriter(out));
		this.memory = copyMemoryToMap(memory);
		instructionPointer = 0L;
		relativeBase = 0L;
	}

	private Map<Long, Long> copyMemoryToMap(List<Long> memory) {
		Map<Long, Long> result = new HashMap<Long, Long>();
		Long adresse = 0L;
		for (Long contenu : memory) {
			result.put(adresse, contenu);
			adresse++;
		}
		return result;
	}

	private Long readMemoryDirectAccess(Long position) {
		return memory.getOrDefault(position, 0L);
	}

	private Long readMemoryIPOffset(Long offset) {
		return memory.getOrDefault(instructionPointer + offset, 0L);
	}

	private Long readMemoryRelativeBaseOffset(Long offset) {
		return memory.getOrDefault(relativeBase + offset, 0L);
	}

	public void writeMemory(Long position, Long value) {
		memory.put(position, value);
	}

	public Long getParameterValue(Parameter parameter) {
		if (ParameterUsage.TO_READ.equals(parameter.getParameterUsage())) {
			return getParameterValueToRead(parameter);
		} else {
			return getParameterValueToWrite(parameter);
		}
	}

	public Long getParameterValueToRead(Parameter parameter) {
		Long operande = readMemoryIPOffset(parameter.getOffsetIP());
		switch (parameter.getMode()) {
			case IMMEDIATE_MODE: return operande;
			case RELATIVE_MODE: return readMemoryRelativeBaseOffset(operande);
			case POSITION_MODE: return readMemoryDirectAccess(operande);
		}
		return null; 
	}

	public Long getParameterValueToWrite(Parameter parameter) {
		Long operande = readMemoryIPOffset(parameter.getOffsetIP());
		switch (parameter.getMode()) {
			case RELATIVE_MODE: return relativeBase + operande;
			default: return operande;
		}
	}

	public void setIP(Long newInstructionPointer) {
		instructionPointer = newInstructionPointer;
	}

	public void shiftIP(Long deltaIP) {
		instructionPointer = instructionPointer + deltaIP;
	}

	public void shiftRelativeBase(Long offset) {
		relativeBase = relativeBase + offset;
	}

	public BufferedReader getReader() {
		return reader;
	}

	public PrintWriter getWriter() {
		return writer;
	}

	public String getName() {
		return name;
	}


	public String parameterToString(Parameter parameter) {
		if (ParameterUsage.TO_WRITE.equals(parameter.getParameterUsage())) {
			return parameterToString(new Parameter(ParameterUsage.TO_READ, parameter.getMode(), parameter.getOffsetIP()));
		}
		StringBuffer sb = new StringBuffer();
		switch (parameter.getMode()) {
			case POSITION_MODE: 
				sb.append("[").append(readMemoryIPOffset(parameter.getOffsetIP())).append("]"); 
				break;
			case RELATIVE_MODE:
				Long offsetBr = readMemoryIPOffset(parameter.getOffsetIP());
				sb.append("[BR+").append(offsetBr).append("=").append(relativeBase + offsetBr).append("]"); 
				break;
			default: break;
		}
		sb.append("(").append(getParameterValue(parameter)).append(")");
		return sb.toString();
	}
	
	
	
	public Long execute() {
		Long lastOutput = null;
		Instruction instructionCourante = new Instruction(readMemoryIPOffset(0L));
		while (!instructionCourante.isHaltInstruction()) {
			LOGGER.info("(IP, RB)=(" + instructionPointer + ", " + relativeBase +") -> " + instructionCourante);
			Long output = instructionCourante.getExecution().apply(this);
			if (output != null) lastOutput = output;
			instructionCourante = new Instruction(readMemoryIPOffset(0L));
		}
		instructionCourante.getExecution().apply(this);
		try {
			writer.close();
			out.close();
			reader.close();
			in.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return lastOutput;
	}
}
