package com.mscannell.bac_calculator;

import java.util.ArrayList;

import com.mscannell.bac_calculator.model.*;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

public class DrinkAdapter extends ArrayAdapter<Drink> {
	private ArrayList<Drink> listDrinks;
	private final Activity activity;
	
	public DrinkAdapter(Activity newActivity, ArrayList objects) {
		super(newActivity, R.layout.drink_row, objects);
		listDrinks = objects;
		activity = newActivity;
	}
	
	@Override
	public Drink getItem(int position) {
		return listDrinks.get(position);
	}
	
	@Override
	public long getItemId(int position) {
		return position;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final Drink currentDrink = (Drink)getItem(position);
		Button buttonEditDrink;
		ImageButton buttonDeleteDrink;
		if (convertView == null) {
			LayoutInflater layoutInflater = activity.getLayoutInflater();
			convertView = layoutInflater.inflate(R.layout.drink_row, null);
		}
		
		Integer positionTag = Integer.valueOf(position);
		buttonEditDrink = (Button)convertView.findViewById(R.id.button_edit_drink);
		buttonEditDrink.setTag(positionTag);
		buttonEditDrink.setText(currentDrink.toString());
		buttonEditDrink.setOnClickListener(listenerEditDrink);
		buttonDeleteDrink = (ImageButton)convertView.findViewById(R.id.button_delete_drink);
		buttonDeleteDrink.setTag(positionTag);
		buttonDeleteDrink.setOnClickListener(listenerDeleteDrink);
		return convertView;
	}//end method getView
	
	/**
	 * OnClickListener for when the user presses the button to edit the drink information.
	 */
	public View.OnClickListener listenerEditDrink = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			final int drinkPosition = (Integer)v.getTag();
			
			//obtain the values associated withe the drink to be edited
			int drinkType = listDrinks.get(drinkPosition).getDrinkType();
			double abv = listDrinks.get(drinkPosition).getABV();
			double ouncesPerDrink = listDrinks.get(drinkPosition).getOuncesPerDrink();
			int numDrinks = listDrinks.get(drinkPosition).getNumDrinks();
			
			//set up the dialog box & associate the data fields
			LayoutInflater inflater = LayoutInflater.from(activity);
	    	View dialogView = inflater.inflate(R.layout.add_drink_dialog, null);
	    	AlertDialog.Builder dialogAddDrinkBuilder = new AlertDialog.Builder(activity);
	    	final EditText etABV = (EditText)dialogView.findViewById(R.id.edittext_abv);
	    	final EditText etNumDrinks = (EditText)dialogView.findViewById(R.id.edittext_num_drinks);
	    	final EditText  etOzPerDrink = (EditText)dialogView.findViewById(R.id.edittext_ounces_per_drink);
	        final TextView tvABV = (TextView)dialogView.findViewById(R.id.textview_abv);
	        etABV.setText("" + abv);
	        etNumDrinks.setText("" + numDrinks);
	        etOzPerDrink.setText("" + ouncesPerDrink);
	        switch (drinkType) {
        	case Drink.BEER:	tvABV.setText(Drink.ABV_RANGE_BEER);
        						break;
        	case Drink.WINE:	tvABV.setText(Drink.ABV_RANGE_WINE);
        						break;
        	case Drink.LIQUOR:	tvABV.setText(Drink.ABV_RANGE_LIQUOR);
        						break;
        	}
	    	
	    	//Links the spinner in the main.xml file to the java code
	        final Spinner spinnerLiquorType = (Spinner)dialogView.findViewById(R.id.spinner_drink);
	        ArrayAdapter<String> adapterLiquorType = new ArrayAdapter<String>(activity, android.R.layout.simple_spinner_item, Drink.LIST_OF_DRINKS);
	        spinnerLiquorType.setAdapter(adapterLiquorType);
	        spinnerLiquorType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
	        	public void onItemSelected(AdapterView<?> parent, View v, int drinkTypePosition, long id) {
	            	switch (drinkTypePosition) {
	            	case Drink.BEER:	tvABV.setText(Drink.ABV_RANGE_BEER);
	            						break;
	            	case Drink.WINE:	tvABV.setText(Drink.ABV_RANGE_WINE);
	            						break;
	            	case Drink.LIQUOR:	tvABV.setText(Drink.ABV_RANGE_LIQUOR);
	            						break;
	            	}
	            }
	        	
	        	public void onNothingSelected(AdapterView<?> arg0) {
	        	}
	        });
	        adapterLiquorType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	        spinnerLiquorType.setSelection(drinkType);
	    	
	    	//set the action for clicking the OK button
	    	dialogAddDrinkBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
	    		public void onClick(DialogInterface dialog, int which) {
	    			try {
	    				listDrinks.get(drinkPosition).setDrinkType(spinnerLiquorType.getSelectedItemPosition());
	    				listDrinks.get(drinkPosition).setABV(Double.parseDouble(etABV.getText().toString()));
	    				listDrinks.get(drinkPosition).setNumDrinks(Integer.parseInt(etNumDrinks.getText().toString()));
	    				listDrinks.get(drinkPosition).setOuncesPerDrink(Double.parseDouble(etOzPerDrink.getText().toString()));
	    			} catch (Exception e) {
	    	    	}
	    			
	    			notifyDataSetChanged();
	    		}
	    	});
	    	
	    	//set the action for clicking the CANCEL button
	    	dialogAddDrinkBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
	    		public void onClick(DialogInterface dialog, int which) {
	    		}
	    	});	
	    	
	    	dialogAddDrinkBuilder.setView(dialogView);
	    	dialogAddDrinkBuilder.setTitle("Edit Drink");
	    	dialogAddDrinkBuilder.show();
		}
	};
	
	/**
	 * OnClickListener for when the user presses the delete drink button.
	 */
	public View.OnClickListener listenerDeleteDrink = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			int position = (Integer)v.getTag();
			listDrinks.remove(position);
			notifyDataSetChanged();
		}
	};
}
