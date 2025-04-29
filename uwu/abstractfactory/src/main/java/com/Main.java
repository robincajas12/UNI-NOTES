package com;

import java.util.Arrays;


public class Main {

	public static void main(String[] args) {

		ConfigLoader cg = new ConfigLoader("/config.properties");

		System.out.println(cg.p.getProperty("theme"));

	}

}
