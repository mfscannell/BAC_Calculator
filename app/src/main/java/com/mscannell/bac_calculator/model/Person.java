package com.mscannell.bac_calculator.model;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedList;

public class Person {
	private ArrayList<Drink> listOfDrinks = new ArrayList<Drink>();
	private int gender;
	private int weight;
	private double time;
	private double bac;
	private double timeToLegal;
	private double timeToZero;
	private double totalOuncesAlcohol;
	

	private final double R_MALE = 0.73;
	private final double R_FEMALE = 0.66;
	private static double legalLimit = 0.08;
	public static final float DEFAULT_LEGAL_LIMIT = (float) 0.08;
	
	/**A constant indicating the gender of the person as a man.
	 * 
	 */
	public final static int MALE = 0;
	
	/**A constant indicating the gender of the person as a woman.
	 * 
	 */
	public final static int FEMALE = 1;
	
	private DecimalFormat fixed_one = new DecimalFormat("#.#");
	private DecimalFormat fixed_three = new DecimalFormat("#.###");
	
	public Person() {
		gender = MALE;
		weight = 150;
		time = 1;
		bac = 0;
		timeToLegal = 0;
		timeToZero = 0;
		totalOuncesAlcohol = 0;
	}
	
	public void setGender(int new_gender) {
		gender = new_gender;
	}
	
	public void setWeight(int new_weight) {
		weight = new_weight;
	}
	
	public void setTime(double new_time) {
		time = new_time;
	}
	
	public static void setLegalLimit(double newLegalLimit) {
		legalLimit = newLegalLimit;
	}
	
	public int getGender() {
		return gender;
	}
	
	public int getWeight() {
		return weight;
	}
	
	public double getTime() {
		return time;
	}
	
	public double getBAC() {
		calculateBAC();
		return bac;
	}
	
	public double getTimeToLegal() {
		calculateBAC();
		timeToLegal = (bac - legalLimit) / 0.015;
		return timeToLegal;
	}
	
	public double getTimeTo0() {
		calculateBAC();
		timeToZero = bac / 0.015;
		return timeToZero;
	}
	
	public double getTimeLose0Point01() {
		return 0.01 / 0.015;
	}
	
	public ArrayList<Drink> getListOfDrinks() {
		return listOfDrinks;
	}
	
	public void addDrink(Drink addedDrink) {
		listOfDrinks.add(addedDrink);
		totalOuncesAlcohol = totalOuncesAlcohol + addedDrink.getOuncesOfAlcohol();
	}
	
	public void deleteDrink(Drink deletedDrink) {
		listOfDrinks.remove(deletedDrink);
		totalOuncesAlcohol = totalOuncesAlcohol - deletedDrink.getOuncesOfAlcohol();
	}
	
	public void calculateBAC() {
		totalOuncesAlcohol = 0;
		for (int i = 0; i < listOfDrinks.size(); i++) {
			totalOuncesAlcohol = totalOuncesAlcohol + listOfDrinks.get(i).getOuncesOfAlcohol();
		}
		
		if (gender == MALE) {
			bac = totalOuncesAlcohol * 5.14 / (weight * R_MALE) - 0.015 * time;
		} else {
			bac = totalOuncesAlcohol * 5.14 / (weight * R_FEMALE) - 0.015 * time;
		}
		
		bac = Math.max(bac, 0);
	}
	
	public static double getLegalLimit() {
		return legalLimit;
	}
}
