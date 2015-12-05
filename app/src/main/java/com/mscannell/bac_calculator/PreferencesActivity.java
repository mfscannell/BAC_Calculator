package com.mscannell.bac_calculator;

import com.mscannell.bac_calculator.model.*;
import com.mscannell.bac_calculator.numberformater.*;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class PreferencesActivity extends Activity {
	private EditText edtTxtLegalLimit;
	private Button btnAccept;
	private Button btnCancel;
	private Context context;
	
	SharedPreferences sharedPreferences;
	SharedPreferences.Editor preferencesEditor;
	public static final String USER_PREFERENCE = "USER_PREFERENCE";
	public static final String PREF_LEGAL_BAC = "PREF_LEGAL_BAC";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //set up the view
        setContentView(R.layout.activity_preferences);
        edtTxtLegalLimit = (EditText)findViewById(R.id.preferences_edittext_legallimit);
        btnAccept = (Button)findViewById(R.id.button_accept_preferences);
        btnCancel = (Button)findViewById(R.id.button_cancel_preferences);
        
        context = getApplicationContext();
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        preferencesEditor = sharedPreferences.edit();
        updateUIFromPreferences();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.activity_preferences, menu);
        return true;
    }
    
    private void updateUIFromPreferences() {
    	double legalLimit = sharedPreferences.getFloat(PREF_LEGAL_BAC, Person.DEFAULT_LEGAL_LIMIT);
    	edtTxtLegalLimit.setText("" + NumberFormater.formatFixedTwo(legalLimit));
    }
    
    /**
     * Update the settings according to the user's changes.
     * @param view The Accept Button pressed to accept the new settings.
     */
    public void updateSettings(View view) {
    	savePreferences();
    	PreferencesActivity.this.setResult(RESULT_OK);
    	finish();
    }
    
    /**
     * Cancel updating the settings.
     * @param view The Cancel Button pressed to cancel changing the settings.
     */
    public void cancelSettings(View view) {
    	PreferencesActivity.this.setResult(RESULT_CANCELED);
    	finish();
    }
    
    /**
     * Save the settings.
     */
    private void savePreferences() {
    	try {
    		//Person.setLegalLimit(Double.valueOf(edtTxtLegalLimit.getText().toString()));
        	preferencesEditor.putFloat(PREF_LEGAL_BAC, Float.valueOf(edtTxtLegalLimit.getText().toString()));
        	preferencesEditor.commit();
        	preferencesEditor.apply();
    	} catch (Exception e) {
    	}
    }
}
