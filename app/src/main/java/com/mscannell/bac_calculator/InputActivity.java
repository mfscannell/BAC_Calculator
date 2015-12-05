package com.mscannell.bac_calculator;

import java.text.DecimalFormat;

import com.mscannell.bac_calculator.model.*;

import android.os.Bundle;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;

public class InputActivity extends Activity {
	//variables for Main View
	private static Person drinker;
	private DecimalFormat fixed_one = new DecimalFormat("#.#");
	private DecimalFormat fixed_three = new DecimalFormat("#.###");
    private ListView listViewDrinks;
    
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    public static final double DEFAULT_LEGAL_BAC = 0.08;
    private static final int SHOW_PREFERENCES = 1;
    //public static final 
    
    //public static final String ABV_BEER = "% ABV\nBeer: 3% - 15%";
	//public static final String ABV_WINE= "% ABV\nWine: 8% - 20%";
	//public static final String ABV_LIQUOR = "% ABV\nHard Alcohol: 30% - 40%";
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	//set up the person
    	drinker = new Person();
    	drinker.setGender(Person.MALE);
        
        //set up the layout
        setContentView(R.layout.activity_input);
        DrinkAdapter drinkAdapter = new DrinkAdapter(this, drinker.getListOfDrinks());
        listViewDrinks = (ListView)findViewById(R.id.list_drinks);
        listViewDrinks.setAdapter(drinkAdapter);
        
        //set up the settings
        updateFromPreferences();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.input_activity, menu);
        return true;
    }
    
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
    	super.onActivityResult(requestCode, resultCode, data);
    	
    	if (requestCode == SHOW_PREFERENCES) {
    		if (resultCode == Activity.RESULT_OK) {
    			updateFromPreferences();
    		}
    	}
    }
    
    /**
     * Pulls up an alert dialog box to edit the personal information.
     * @param view The button pressed that launches the method.
     */
    public void editPersonalInfo(View view) {
    	//setup the dialog box
    	LayoutInflater inflater = LayoutInflater.from(this);
    	View dialogView = inflater.inflate(R.layout.edit_personal_info_dialog, null);
    	AlertDialog.Builder dialogEditPersonalBuilder = new AlertDialog.Builder(this);
    	final RadioGroup rgGender = (RadioGroup)dialogView.findViewById(R.id.radioGroupGender);
    	final EditText etWeight = (EditText)dialogView.findViewById(R.id.editTextWeight);
    	final EditText etTime = (EditText)dialogView.findViewById(R.id.editTextTime);
    	
    	//set the personal information
    	if (drinker.getGender() == Person.MALE) {
    		rgGender.check(R.id.radioButtonMale);
    	} else if (drinker.getGender() == Person.FEMALE) {
    		rgGender.check(R.id.radioButtonFemale);
    	}
    	
    	etWeight.setText(Integer.toString(drinker.getWeight()), TextView.BufferType.EDITABLE);
    	etTime.setText(Double.toString(drinker.getTime()), TextView.BufferType.EDITABLE);
    	
    	dialogEditPersonalBuilder.setView(dialogView);
    	dialogEditPersonalBuilder.setTitle("Edit Personal Information");
    	
    	//set action for clicking the OK button
    	dialogEditPersonalBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
    		public void onClick(DialogInterface dialog, int which) {
    			try {
    				drinker.setWeight(Integer.parseInt(etWeight.getText().toString()));
    				drinker.setTime(Double.parseDouble(etTime.getText().toString()));
    			
    				if (rgGender.getCheckedRadioButtonId() == R.id.radioButtonMale) {
    					drinker.setGender(Person.MALE);
    				} else if (rgGender.getCheckedRadioButtonId() == R.id.radioButtonFemale) {
    					drinker.setGender(Person.FEMALE);
    				}
    			} catch (Exception e) {
    			}
    		}
    	});
    	
    	//set action for clicking the CANCEL button
    	dialogEditPersonalBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
    		public void onClick(DialogInterface dialog, int which) {
    			
    		}
    	});
    	
    	dialogEditPersonalBuilder.show();
    	
    }//end method editPersonalInfo
    
    /**
     * Pulls up an alert dialog box to add a new drink.  If entered input is not complete valid, 
     * then a drink is not added to the list of drinks being consumed.
     * 
     * @param view The button pressed that launches the method
     */
    public void addDrink(View view) {
    	LayoutInflater inflater = LayoutInflater.from(this);
    	View dialogView = inflater.inflate(R.layout.add_drink_dialog, null);
    	AlertDialog.Builder dialogAddDrinkBuilder = new AlertDialog.Builder(this);
    	
    	//link the edit text fields
    	final EditText etABV = (EditText)dialogView.findViewById(R.id.edittext_abv);
    	final EditText etNumDrinks = (EditText)dialogView.findViewById(R.id.edittext_num_drinks);
    	final EditText  etOzPerDrink = (EditText)dialogView.findViewById(R.id.edittext_ounces_per_drink);
        final TextView tvABV = (TextView)dialogView.findViewById(R.id.textview_abv);
    	
    	//Links the spinner in the main.xml file to the java code
        final Spinner spinnerLiquorType = (Spinner)dialogView.findViewById(R.id.spinner_drink);
        ArrayAdapter<String> adapterLiquorType = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, Drink.LIST_OF_DRINKS);
        spinnerLiquorType.setAdapter(adapterLiquorType);
        spinnerLiquorType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
        	public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
            	//drinkType = position;
            	
            	switch (position) {
            	case Drink.BEER:	tvABV.setText(Drink.ABV_RANGE_BEER);
            						break;
            	case Drink.WINE:	tvABV.setText(Drink.ABV_RANGE_WINE);
            						break;
            	case Drink.LIQUOR:	tvABV.setText(Drink.ABV_RANGE_LIQUOR);
            						break;
            	}
            }
        	
        	public void onNothingSelected(AdapterView<?> arg0) {
        		// TODO Auto-generated method stub
        		
        	}
        });
        adapterLiquorType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerLiquorType.setSelection(Drink.BEER);
    	
    	//set the action for clicking the OK button
    	dialogAddDrinkBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
    		public void onClick(DialogInterface dialog, int which) {
    			try {
    				Drink newDrink = new Drink();
    				newDrink.setDrinkType(spinnerLiquorType.getSelectedItemPosition());
    				newDrink.setABV(Double.parseDouble(etABV.getText().toString()));
    				newDrink.setNumDrinks(Integer.parseInt(etNumDrinks.getText().toString()));
    				newDrink.setOuncesPerDrink(Double.parseDouble(etOzPerDrink.getText().toString()));
    				drinker.addDrink(newDrink);
    			} catch (Exception e) {
    	    	}
    		}
    	});
    	
    	//set the action for clicking the CANCEL button
    	dialogAddDrinkBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
    		public void onClick(DialogInterface dialog, int which) {
    		}
    	});	
    	
    	dialogAddDrinkBuilder.setView(dialogView);
    	dialogAddDrinkBuilder.setTitle("Add New Drink");
    	dialogAddDrinkBuilder.show();
    }//end method addDrink
   
    //This method is for selecting the gender
    public void onCheckChanged(RadioGroup group, int checkedId) {
    	if (checkedId == R.id.radioButtonMale) {
    		drinker.setGender(Person.MALE);
    	} else if (checkedId == R.id.radioButtonFemale) {
    		drinker.setGender(Person.FEMALE);
    	}
    }//end method onCheckChanged
    
    /**
     * Solves the drinker's BAC based upon the given inputs.
     * @param view The button pressed that launches the method
     */
    public void solveBAC(View view) {
    	//bundle the results into an intent and start a new activity showing the results
    	Bundle bundleResults = new Bundle();
    	bundleResults.putDouble("bac_results", drinker.getBAC());
    	bundleResults.putDouble("Time Lose 0.01", drinker.getTimeLose0Point01());
    	bundleResults.putDouble("Time to 0.08", drinker.getTimeToLegal());
    	bundleResults.putDouble("Time to 0", drinker.getTimeTo0());
		Intent intentResults = new Intent(this, ResultsActivity.class);
		intentResults.putExtras(bundleResults);
		startActivity(intentResults);
    }//end method solveBAC
    
    private void updateFromPreferences() {
    	Context context = getApplicationContext();
    	sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    	
    	float legalBAC = sharedPreferences.getFloat(PreferencesActivity.PREF_LEGAL_BAC, Person.DEFAULT_LEGAL_LIMIT);
    	Person.setLegalLimit(legalBAC);
    }
    
    /**
     * Displays the settings.
     * @param view The button pressed that launches the method.
     */
    public void displaySettings(View view) {
    	Intent intent = new Intent(this, PreferencesActivity.class);
    	startActivityForResult(intent, SHOW_PREFERENCES);
    }
}
