package com.mawen.memory.enums;

import java.util.Random;

/**
 * The performance problem is every time call {@code Enum#values} method, it always allocates memory to store Enum Instance.
 *
 * <p>Run VM options:
 * <pre>{@code
 * 	-Xms1G
 * 	-Xmx1G
 * 	-XX:+UnlockExperimentalVMOptions
 * 	-XX:+UseEpsilonGC
 * 	-XX:+HeapDumpOnOutOfMemoryError
 * 	-XX:HeapDumpPath=/Users/mawen/Documents/Github/mawen12/think-in-java-performance/EnumAllocationExample.hprof
 * }</pre>
 *
 * <p>Jbang run command:
 * <pre>{@code
 *  jbang --enable-preview --javaagent=ap-loader@jvm-profiling-tools/ap-loader=start,event=cpu,file=profile.html memory-optimization/enum-values/src/main/java/com/mawen/memory/enums/EnumAllocationExample.java
 * }</pre>
 *
 * @author <a href="1181963012mw@gmail.com">mawen12</a>
 * @see <a href="https://gamlor.info/posts-output/2017-08-31-java-enum-hidden-allocation/en/">Java's Enum.Values Hidden Allocations</a>
 * @since 2024/6/3
 */
public class EnumAllocationExample {

	private static final int COUNT = 1000000000;

	private static Random random = new Random();

	public static void main(String[] args) throws InterruptedException {
		System.out.println("Start....");
		for (int i = 0; i < COUNT; i++) {
			int idParsedFromWire = readTypeIdFromData(random);
			TypeInfo.findType(idParsedFromWire);
		}
		System.out.println("End....");
		Thread.sleep(10000L);
	}

	private static int readTypeIdFromData(Random random) {
		return random.nextInt(6);
	}

	enum TypeInfo {
		UNKNOWN(0),
		INT(1),
		STRING(2),
		LONG(3),
		DOUBLE(4),
		SHORT(5)
		;

		private final int id;

		TypeInfo(int id) {
			this.id = id;
		}

		public static TypeInfo findType(int type) {
			for (TypeInfo t : values()) {
				if (t.id == type) {
					return t;
				}
			}
			return UNKNOWN;
		}
	}
}
