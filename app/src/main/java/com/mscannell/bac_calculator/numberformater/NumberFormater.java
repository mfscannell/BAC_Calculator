package com.mscannell.bac_calculator.numberformater;

import java.text.DecimalFormat;

public class NumberFormater {
	private static DecimalFormat fixedOne = new DecimalFormat("#.#");
	private static DecimalFormat fixedTwo = new DecimalFormat("#.##");
	private static DecimalFormat fixedThree = new DecimalFormat("#.###");
	
	public static double formatFixedOne(double num) {
		return Double.valueOf(fixedOne.format(num));
	}
	
	public static double formatFixedTwo(double num) {
		return Double.valueOf(fixedTwo.format(num));
	}
	
	public static double formatFixedThree(double num) {
		return Double.valueOf(fixedThree.format(num));
	}

}
