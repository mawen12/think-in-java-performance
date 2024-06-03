package com.mawen.memory.enums;

import java.util.Random;

/**
 * By cache the return of {@code Enum#values()}, we do need to allocate memory everytime.
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
 * <pre>{@code
 *  jbang --enable-preview --javaagent=ap-loader@jvm-profiling-tools/ap-loader=start,event=cpu,file=profile.html memory-optimization/enum-values/src/main/java/com/mawen/memory/enums/EnumAllocation01Example.java
 * }</pre>
 *
 * @author <a href="1181963012mw@gmail.com">mawen12</a>
 * @since 2024/6/3
 */
public class EnumAllocation01Example {

	private static final int COUNT = 1000000000;

	private final static Random random = new Random();

	public static void main(String[] args) throws InterruptedException {
		System.out.println("Start....");
		for (int i = 0; i < COUNT; i++) {
			int idParsedFromWire = readTypeIdFromData(random);
			TypeInfo.findType(idParsedFromWire);
		}
		System.out.println("End....");
		Thread.sleep(10000L);
	}

	private static int readTypeIdFromData(final Random random) {
		return random.nextInt(6);
	}

	enum TypeInfo {
		UNKNOWN(0),
		INT(1),
		STRING(2),
		LONG(3),
		DOUBLE(4),
		SHORT(5);

		private static final TypeInfo[] values = TypeInfo.values();

		private final int id;

		TypeInfo(int id) {
			this.id = id;
		}

		public static TypeInfo findType(int type) {
			for (TypeInfo t : values) {
				if (t.id == type) {
					return t;
				}
			}
			return UNKNOWN;
		}
	}
}
