package com.mscannell.bac_calculator.model;

import java.text.DecimalFormat;

public class Drink {
	/**A constant array listing all the available types of drinks.
	 * 
	 */
	public final static String[] LIST_OF_DRINKS = {"Beer", "Wine", "Hard Liquor"};
	
	/**A constant indicating the drink being consumed is a beer.
	 * 
	 */
	public final static int BEER = 0;
	
	/**A constant indicating the drink being consumed is a wine.
	 * 
	 */
	public final static int WINE = 1;
	
	/**A constant indicating the drink being consumed is hard liquor.
	 * 
	 */
	public final static int LIQUOR = 2;
	public static final String ABV_RANGE_BEER = "% ABV\nBeer: 3% - 15%";
	public static final String ABV_RANGE_WINE= "% ABV\nWine: 8% - 20%";
	public static final String ABV_RANGE_LIQUOR = "% ABV\nHard Alcohol: 30% - 40%";
	
	private int drinkType = 0;
	private double abv = 0;
	private double ouncesPerDrink = 0;
	private int numDrinks = 0;
	private double ouncesOfAlcohol = 0;
		
	private DecimalFormat fixed_one = new DecimalFormat("#.#");
	private DecimalFormat fixed_three = new DecimalFormat("#.###");
	
	/**
	 * Public constructor.
	 */
	public Drink() {
	}
	
	/**
	 * This method sets the type of drink is being consumed.
	 * @param newDrinkType The type of drink that is being consumed
	 */
	public void setDrinkType(int newDrinkType) {
		drinkType = newDrinkType;
	}
	
	/**
	 * This method sets the percent alcohol-by-volume (ABV) a drink contains.
	 * @param newABV The amount of alcohol-by-volume in the drink
	 */
	public void setABV(double newABV) {
		abv = newABV;
	}
	
	/**
	 * This method sets the fluid ounces per drink being consumed.
	 * @param newOunces The number of fluid ounces per drink being consumed
	 */
	public void setOuncesPerDrink(double newOunces) {
		ouncesPerDrink = newOunces;
	}
	
	/**
	 * Sets the number of drinks being consumed have the same drink type, ABV, and ounces.
	 * @param newNumDrinks The number of drinks being consumed having the same drink type, ABV, and ounces
	 */
	public void setNumDrinks(int newNumDrinks) {
		numDrinks = newNumDrinks;
	}
	
	/**
	 * Returns a constant indicating the type of drink being consumed.
	 * @return 
	 */
	public int getDrinkType() {
		return drinkType;
	}
	
	/**
	 * Returns the Percent alcohol-by-volume of the drink being consumed.
	 * @return
	 */
	public double getABV() {
		return abv;
	}
	
	/**
	 * Returns the fluid ounces per drink being consumed.
	 * @return
	 */
	public double getOuncesPerDrink() {
		return ouncesPerDrink;
	}
	
	/**
	 * Returns the number of drinks being consumed having the same drink type, ABV, and ounces per drink
	 * @return
	 */
	public int getNumDrinks() {
		return numDrinks;
	}
	
	/**
	 * Returns a string description of the drink being consumed
	 */
	public String toString() {
		String drinkName = "";
		
		switch (drinkType) {
		case BEER:		drinkName = "Beer";
						break;
		case WINE:		drinkName = "Wine";
						break;
		case LIQUOR:	drinkName = "Hard Liquor";
						break;
		default:		drinkName = "";
						break;
		}
		
		return "" + numDrinks + " " + Double.valueOf(fixed_one.format(ouncesPerDrink)) + " oz " + drinkName + "\n(" + Double.valueOf(fixed_one.format(abv)) + "% ABV)";
	}
	
	/**
	 * Returns the total ounces of alcohol being consumed based on the fluid ounces per drink, number of drinks, and %alcohol by volume.
	 * @return
	 */
	public double getOuncesOfAlcohol() {
		return ouncesPerDrink * numDrinks * abv / 100.0;
	}
}
