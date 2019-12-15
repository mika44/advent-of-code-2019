package fr.game.advent.day13.program.model;

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

public class Program {
	
	private String name;
	
	private InputStream in;
	private BufferedReader reader;
	
	private OutputStream out;
	private PrintWriter writer;

	private Map<Long, Long> memory;
	private Long instructionPointer;
	private Long relativeBase;
	
	
	public Program(List<Long> memory) {
		this(memory, System.in);
	}

	public Program(List<Long> memory, InputStream in) {
		this(memory, in, System.out);
	}

	public Program(List<Long> memory, InputStream in, OutputStream out) {
		this.in = in;
		this.reader = new BufferedReader(new InputStreamReader(in));
		
		this.out = out;
		this.writer = new PrintWriter(new OutputStreamWriter(out));
		
		this.memory = copyMemoryToMap(memory);
		instructionPointer = 0L;
		relativeBase = 0L;
	}

	public Program(String name, List<Long> memory, InputStream in, OutputStream out) {
		this(memory, in, out);
		this.name = name;
	}

	
	/* REGISTERS ACCESS */
	public Long getInstructionPointer() {
		return instructionPointer;
	}

	public void setIP(Long newInstructionPointer) {
		instructionPointer = newInstructionPointer;
	}

	public void shiftIP(Long deltaIP) {
		instructionPointer = instructionPointer + deltaIP;
	}

	public Long getRelativeBase() {
		return relativeBase;
	}

	public void shiftRelativeBase(Long offset) {
		relativeBase = relativeBase + offset;
	}

	/* MEMORY ACCESS */
	public void writeMemory(Long position, Long value) {
		memory.put(position, value);
	}

	public Long readMemoryDirectAccess(Long position) {
		return memory.getOrDefault(position, 0L);
	}

	public Long readMemoryIPOffset(Long offset) {
		return memory.getOrDefault(instructionPointer + offset, 0L);
	}

	public Long readMemoryRelativeBaseOffset(Long offset) {
		return memory.getOrDefault(relativeBase + offset, 0L);
	}
	
	/* OTHERS */
	public BufferedReader getReader() {
		return reader;
	}

	public PrintWriter getWriter() {
		return writer;
	}

	public String getName() {
		return name;
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

	public void close() {
		try {
			writer.close();
			out.close();
			reader.close();
			in.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}	}
}
