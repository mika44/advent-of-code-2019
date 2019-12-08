package fr.game.advent.day07.program;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import fr.game.advent.day07.program.instructions.Instruction;
import fr.game.advent.day07.program.instructions.Mode;


public class Program {

	private String name;
	private InputStream in;
	private OutputStream out;
	private List<Integer> memory;
	private Integer instructionPointer;
	private BufferedReader reader;
	private PrintWriter writer;
	
	public Program(List<Integer> memory, InputStream in) {
		this(memory, in, System.out);
	}

	public Program(List<Integer> memory, InputStream in, OutputStream out) {
		this.in = in;
		this.out = out;
		this.memory = new ArrayList<>(memory);
		instructionPointer = 0;
		this.reader = new BufferedReader(new InputStreamReader(in));
		this.writer = new PrintWriter(new OutputStreamWriter(out));
	}
	
	public Program(String name, List<Integer> memory, InputStream in, OutputStream out) {
		this(memory, in, out);
		this.name = name;
	}
	

	private Integer readMemoryDirectAccess(Integer position) {
		return memory.get(position);
	}
	
	public Integer readMemoryIPOffset(Integer offset) {
		return memory.get(instructionPointer + offset);
	}

	public Integer readMemoryIPOffset(Integer offset, Mode accessMode) {
		Integer operande = readMemoryIPOffset(offset);
		return Mode.POSITION_MODE.equals(accessMode) ? readMemoryDirectAccess(operande) : operande;
	}
	
	public void writeMemory(Integer position, Integer value) {
		memory.set(position, value);
	}

	public void setIP(Integer newInstructionPointer) {
		instructionPointer = newInstructionPointer;
	}
	
	public void shiftIP(Integer deltaIP) {
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
		Integer lastOutput = null;
		Instruction instructionCourante = new Instruction(readMemoryIPOffset(0));
		while (!instructionCourante.isHaltInstruction()) {
			Integer output = instructionCourante.getExecution().apply(this);
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
