package com.mawen.memeory.enums;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * <pre>{@code
 *  jbang --javaagent:ap-loader@jvm-profiling-tools/ap-loader=start,event=cpu,file=profile.html com.mawen.memeory.enums.EnumAllocationExample
 * }</pre>
 *
 * @author <a href="1181963012mw@gmail.com">mawen12</a>
 * @see <a href="https://gamlor.info/posts-output/2017-08-31-java-enum-hidden-allocation/en/">Java's Enum.Values Hidden Allocations</a>
 * @since 2024/6/3
 */
public class EnumAllocationExample {

	private static final int COUNT = 1000000000;

	private static Random random = new Random();

	public static void main(String[] args) {
		List<TypeInfo> parsedData = new ArrayList<>();
		for (int i = 0; i < COUNT; i++) {
			int idParsedFromWire = readTypeIdFromData(random);

			TypeInfo type = TypeInfo.findType(idParsedFromWire);

			parsedData.add(type);

			consumeData(parsedData);
		}
	}

	private static int readTypeIdFromData(Random random) {
		int idParsedFromWire = random.nextInt(16);
		Thread.yield();
		return idParsedFromWire;
	}

	private static void consumeData(List<TypeInfo> parsedData) {
		if (random.nextBoolean()) {
			parsedData.clear();
		}
	}

	enum TypeInfo {
		UNKNOWN(0),
		INT(1),
		STRING(2);

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
