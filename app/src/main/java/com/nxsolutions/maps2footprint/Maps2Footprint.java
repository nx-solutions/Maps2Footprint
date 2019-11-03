package com.nxsolutions.maps2footprint;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.nxsolutions.maps2footprint.R;

public class Maps2Footprint extends Activity  {
    TextView statusTV;
    EditText latitudeET;
    EditText longitudeET;
    EditText nameET;
    EditText locationET;
    EditText phoneET;

    String TAG = "Maps2Footprint";

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String latitude;
        String longitude;
        String inputName;
        String inputLocation;
        String inputPhone;

        setContentView(R.layout.main);

        latitudeET = (EditText)findViewById(R.id.latitude);
        longitudeET = (EditText)findViewById(R.id.longitude);
        nameET = (EditText)findViewById(R.id.name);
        locationET = (EditText)findViewById(R.id.location);
        phoneET = (EditText)findViewById(R.id.phone);
        statusTV = (TextView)findViewById(R.id.status);

        int diWidth = 480;
        int diHeight = 75;
        int density = (int) getResources().getDisplayMetrics().density;

        Log.v(TAG, "Start application");

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);

        // Start of application
        Bundle extras = getIntent().getExtras();
        if (extras != null)
        {
            String intentText = extras.getString("android.intent.extra.TEXT");

            String[] intentTextStrings = intentText.split("\\n");

            Log.v(TAG, "intentText lines: " + intentTextStrings.length);
            for (int index = 0; index < intentTextStrings.length; index++)
            {
                Log.v(TAG, "intentText lines: " + intentTextStrings[index]);
            }

            inputName = "Maps2Footprint";
            inputPhone = "";

            if (intentTextStrings.length == 4)
            {
                inputName = intentTextStrings[0];
                nameET.setText(inputName);
                Log.v(TAG, "inputName:" + inputName);

                inputPhone = intentTextStrings[1];
                phoneET.setText(inputPhone);
                Log.v(TAG, "inputPhone:" + inputPhone);
            }

            inputLocation = intentTextStrings[intentTextStrings.length-2];
            Log.v(TAG, "inputLocation:" + inputLocation);

            locationET.setText(inputLocation);

            String inputURL = intentTextStrings[intentTextStrings.length-1];
            Log.v(TAG, "inputURL:" + inputURL);
            String outputURL = "application/binary";

//            FIXME: (Xander): Code to find more precise coordinates for locations that are not near an address doesn't work anymore.
//                             Need to rewrite this part.
//            Log.v(TAG, "Resolve url");
//            try {
//                URL url = new URL(inputURL);
//
//                //Set up the initial connection
//                URLConnection connection = url.openConnection();
//
//                connection.setRequestProperty("Accept", "*/*");
//                connection.setReadTimeout(10000);
//                connection.connect();
//
//                Map<String, List<String>> map = connection.getHeaderFields();
//                for (String key : map.keySet()) {
//                    List<String> values = map.get(key);
//
//                    for (String aValue : values) {
//                        Log.v(TAG, "Header field:" + key + ":" + aValue);
//                    }
//                }
//
//                try
//                {
//   					BufferedReader reader = new BufferedReader(new InputStreamReader(req.getInputStream()));
//   					String readerLine;
//					Pattern pattern = Pattern.compile("<meta content=.*&amp;ll=([0-9\\-\\.]*),([0-9\\-\\.]*)\" itemprop=\"image\">");
//
//					while ((readerLine = reader.readLine()) != null)
//					{
//						Matcher matcher = pattern.matcher(readerLine);
//
//                        if (matcher.find())
//                        {
//                            Log.v(TAG, "Reader Data:" + matcher.group(1) + " " + matcher.group(2) );
//                        }
//                        Log.v(TAG, "Reader Data:" + readerLine);
//						break;
//					}
//
//   					reader.close();
//
//
//                    Log.v(TAG, "Reader Data:" + readerLine.substring(readerLine.indexOf("<title>")));
//
//                    outputURL = readerLine.substring(readerLine.indexOf("<title>") + 7, readerLine.indexOf("</title>", readerLine.indexOf("<title>")));
//
//                    Log.v(TAG, "Output URL:" + outputURL);
//                }
//                catch (Exception ex)
//                {
//                    ex.printStackTrace();
//                    Log.e(TAG, "InputStreamer Error");
//                }
//            }
//            catch (Exception ex)
//            {
//                ex.printStackTrace();
//                Log.e(TAG, "HTTP Connect Error");
//            }

            // If the URL doesn't have a . and , in the first 10 characters then
            // its not a GPS location
            int dot_index = outputURL.indexOf(".");
            int comma_index = outputURL.indexOf(",");
            if ((dot_index > 0) && (dot_index < 4) &&
                    (comma_index > 6) && (comma_index < 10))
            {
                latitude = outputURL.substring(0, outputURL.indexOf(','));
                latitudeET.setText(latitude.toString());

                longitude = outputURL.substring(outputURL.indexOf(',')+1, outputURL.indexOf('('));
                longitudeET.setText(longitude.toString());

                Log.v(TAG, "Coordinates:" + latitude + ", " + longitude);
            }
            else
            {
                Geocoder geocoder = new Geocoder(this);
                List<Address> list = null;

                Log.v(TAG, "Get GPS coordinates from location");

                try {
                    list = geocoder.getFromLocationName(inputLocation, 1);
                } catch (IOException e) {
                    Log.e(TAG, "Impossible to connect to Geocoder", e);
                }

                if (list.size() > 0)
                {
                    Address address = list.get(0);

                    latitude = Double.toString(address.getLatitude());
                    latitudeET.setText(latitude);

                    longitude = Double.toString(address.getLongitude());
                    longitudeET.setText(longitude);

                    Log.v(TAG, "Coordinates:" + latitude + ", " + longitude);
                }
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);
        return true;
    }

    public void exportData (View view) {
        try {
            float latitude;
            float longitude;

            latitude = Float.valueOf(latitudeET.getText().toString());
            longitude = Float.valueOf(latitudeET.getText().toString());

            Log.v(TAG, "Coordinates:" + latitude + ", " + longitude);

            try {
                File root = Environment.getExternalStorageDirectory();
                Log.v(TAG, "Found SD-Card");

                if (root.canWrite()){
                    String kmlstring = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                            "<kml xmlns=\"http://www.opengis.net/kml/2.2\" xmlns:kml=\"http://www.opengis.net/kml/2.2\">\n" +
                            "<Document>\n" +
                            "<name>Maps2Location</name>\n" +
                            "<description>This kml is created by Maps2Location</description>\n" +
                            "<Placemark>\n" +
                            "<name>" + nameET.getText() + "</name>\n" +
                            "<phoneNumber>" + phoneET.getText() + "</phoneNumber>\n" +
                            "<TimeStamp><when>" + new java.util.Date().toString() + "</when></TimeStamp>\n" +
                            "<address>" + locationET.getText() + "</address>\n" +
                            "<Point><coordinates>" + longitudeET.getText() + "," + latitudeET.getText() + "</coordinates></Point>\n" +
                            "</Placemark>\n" +
                            "</Document>\n" +
                            "</kml>";
                    Log.v(TAG, kmlstring);

                    File kmlfile = new File(root, "Maps2Footprint.kml");
                    FileWriter kmlwriter = new FileWriter(kmlfile);
                    BufferedWriter out = new BufferedWriter(kmlwriter);

                    out.write(kmlstring);
                    out.close();

                    Log.v(TAG, "Maps2Footprint.kml written");
                }
            } catch (IOException e) {
                Log.e(TAG, "Could not write file " + e.getMessage());
            }

            statusTV.setText("Maps2Footprint.kml exported to the SD-Card root..");
        }
        catch (NumberFormatException e)
        {
            AlertDialog alertDialog;
            alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle("Format Error");
            alertDialog.setMessage("Invalid GPS coordinates. Required format xxx.xxxxx");
            alertDialog.show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.help:
                Intent intent = new Intent(Maps2Footprint.this, Help.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}