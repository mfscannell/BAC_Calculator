package com.mscannell.bac_calculator;

import java.text.DecimalFormat;

import com.mscannell.bac_calculator.numberformater.*;
import com.mscannell.bac_calculator.model.*;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.Color;
import android.view.Menu;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ResultsActivity extends Activity {
	private double bac;
	private double timeToLegal = 0;
	private double timeTo0 = 0;
	private double timeLose0Point01 = 0;
	private static final int TEXT_RESULTS_HEIGHT = 23;
	private static final int PADDING = 4;
	
	private TextView tvBac;
    private TextView tvBelowLegal;
    private TextView tvSober;
    private TextView tvLose0Point01;
	
	//private DecimalFormat fixedOne = new DecimalFormat("#.#");
	//private DecimalFormat fixedThree = new DecimalFormat("#.###");

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        LinearLayout resultsLayout = (LinearLayout)findViewById(R.id.resultsLayout);
        
        //Retrieve the bac value from the intent
        Bundle bundleResults = getIntent().getExtras();
      	bac = bundleResults.getDouble("bac_results");
      	timeLose0Point01 = bundleResults.getDouble("Time Lose 0.01");
      	timeToLegal = bundleResults.getDouble("Time to 0.08");
      	timeTo0 = bundleResults.getDouble("Time to 0");
      	//Person drinker = getIntent().getParcelableExtra("drinker");
      	//Person drinker = new Person();
      	
      	
      	//add the view for the user's BAC
      	tvBac = new TextView(this);
      	tvBac.setText("Your BAC is " + NumberFormater.formatFixedThree(bac));
    	tvBac.setTextColor(getBaseContext().getResources().getColor(R.color.darkgreen));
    	tvBac.setTextSize(TEXT_RESULTS_HEIGHT);
    	tvBac.setPadding(0, PADDING, 0, PADDING);
    	resultsLayout.addView(tvBac);
    	
    	//add the view for losing 0.1 BAC
    	if (bac > 0) {
    		tvLose0Point01 = new TextView(this);
    		tvLose0Point01.setText("Your BAC will drop 0.01 every " + NumberFormater.formatFixedOne(timeLose0Point01) + " hours.");
    		tvLose0Point01.setTextSize(TEXT_RESULTS_HEIGHT);
    		tvLose0Point01.setPadding(0, PADDING, 0, PADDING);
    		resultsLayout.addView(tvLose0Point01);
    	}
    	
    	if (bac > 0.5 * Person.getLegalLimit()) {
    		tvBac.setTextColor(getBaseContext().getResources().getColor(R.color.beer));
    	}
    	
    	//add the view for being legal to drive
    	if (bac > Person.getLegalLimit()) {
    		tvBac.setTextColor(getBaseContext().getResources().getColor(R.color.red));
    		tvBelowLegal = new TextView(this);
    		tvBelowLegal.setText("You will be legal to drive in " + NumberFormater.formatFixedOne(timeToLegal) + " hours.");
    		tvBelowLegal.setTextSize(TEXT_RESULTS_HEIGHT);
    		tvBelowLegal.setPadding(0, PADDING, 0, PADDING);
    		resultsLayout.addView(tvBelowLegal);
    	}
    	
    	//add the view for being sober
    	if (bac > 0) {
    		tvSober = new TextView(this);
    		tvSober.setText("You will be completely sober in " + NumberFormater.formatFixedOne(timeTo0) + " hours.");
    		tvSober.setTextSize(TEXT_RESULTS_HEIGHT);
    		tvSober.setPadding(0, PADDING, 0, PADDING);
    		resultsLayout.addView(tvSober);
    	}
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.results_activity, menu);
        return true;
    }
}
