package com.lzy;

import java.time.LocalDateTime;
import java.time.ZoneId;

public class Test {


	public static void main(String[] args) {
		LocalDateTime now = LocalDateTime.now();

		long timestamp = now.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
	}
}
