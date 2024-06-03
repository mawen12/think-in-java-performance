package com.mawen.memory.enums;

import java.util.Random;

/**
 * The Enum's Name is cached by default, so it will not allocate memory everytime.
 *
 * <p>Run VM options:
 * <pre>{@code
 * 	-Xms1G
 * 	-Xmx1G
 * 	-XX:+UnlockExperimentalVMOptions
 * 	-XX:+UseEpsilonGC
 * 	-XX:+HeapDumpOnOutOfMemoryError
 * 	-XX:HeapDumpPath=/Users/mawen/Documents/Github/mawen12/think-in-java-performance/EnumAllocation01Example.hprof
 * }</pre>
 *
 * @author <a href="1181963012mw@gmail.com">mawen12</a>
 * @since 2024/6/3
 */
public class EnumAllocation02Example {

	private static final int COUNT = 1000000000;

	private final static Random random = new Random();

	private static final String[] array = {"UNKNOWN", "INT", "STRING", "LONG", "DOUBLE", "SHORT"};

	public static void main(String[] args) throws InterruptedException {
		System.out.println("Start....");
		for (int i = 0; i < COUNT; i++) {
			String name = readNameFromData(random);
			TypeInfo.findType(name);
		}
		System.out.println("End....");
		Thread.sleep(10000L);
	}

	private static String readNameFromData(final Random random) {
		return array[random.nextInt(6)];
	}

	enum TypeInfo {
		UNKNOWN,
		INT,
		STRING,
		LONG,
		DOUBLE,
		SHORT;

		public static TypeInfo findType(String name) {
			return Enum.valueOf(TypeInfo.class, name);
		}
	}
}
