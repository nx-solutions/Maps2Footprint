package com.nxsolutions.maps2footprint;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class Help extends Activity {
	TextView helpTV;

	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.help);
    }
}