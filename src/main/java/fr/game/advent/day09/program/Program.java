package fr.game.advent.day09.program;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import fr.game.advent.day09.program.instructions.Instruction;
import fr.game.advent.day09.program.instructions.Mode;
import fr.game.advent.day09.program.instructions.Parameter;
import fr.game.advent.day09.program.instructions.ParameterUsage;

public class Program {

	private String name;
	private InputStream in;
	private OutputStream out;
	private Map<Long, Long> memory;
	private Integer instructionPointer;
	private BufferedReader reader;
	private PrintWriter writer;

	public Program(List<Long> memory, InputStream in) {
		this(memory, in, System.out);
	}

	public Program(List<Long> memory, InputStream in, OutputStream out) {
		this.in = in;
		this.out = out;
		this.memory = copyMemoryToMap(memory);
		instructionPointer = 0;
		this.reader = new BufferedReader(new InputStreamReader(in));
		this.writer = new PrintWriter(new OutputStreamWriter(out));
	}

	private Map<Long, Long> copyMemoryToMap(List<Long> memory) {
		// TODO Auto-generated method stub
		return null;
	}

	public Program(String name, List<Long> memory, InputStream in, OutputStream out) {
		this(memory, in, out);
		this.name = name;
	}

	private Long readMemoryDirectAccess(Long position) {
		return memory.get(position);
	}

	private Long readMemoryIPOffset(Long offset) {
		return memory.get(instructionPointer + offset);
	}

	public void writeMemory(Long position, Long value) {
		memory.put(position, value);
	}

	public Long getParameterValue(Parameter parameter) {
		Long operande = readMemoryIPOffset(parameter.getOffsetIP());
		if (Mode.IMMEDIATE_MODE.equals(parameter.getMode()) || ParameterUsage.TO_WRITE.equals(parameter.getParameterUsage())) {
			return operande;
		} else {
			return readMemoryDirectAccess(operande);
		}
	}

	public void setIP(Long newInstructionPointer) {
		instructionPointer = newInstructionPointer;
	}

	public void shiftIP(Long deltaIP) {
		instructionPointer = instructionPointer + deltaIP;
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

	public Integer execute() {
		Long lastOutput = null;
		Instruction instructionCourante = new Instruction(readMemoryIPOffset(0));
		while (!instructionCourante.isHaltInstruction()) {
			System.out.println(instructionPointer + " -> " + instructionCourante);
			Long output = instructionCourante.getExecution().apply(this);
			if (output != null) lastOutput = output;
			instructionCourante = new Instruction(readMemoryIPOffset(0));
		}
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
