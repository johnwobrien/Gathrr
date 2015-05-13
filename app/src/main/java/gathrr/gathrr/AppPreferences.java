package gathrr.gathrr;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import gathrr.utility.ApiHelper;

/**
 * Created by jshippling on 2/27/15.
 */

public class AppPreferences extends PreferenceActivity implements Preference.OnPreferenceClickListener {

    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userID = getIntent().getStringExtra("userID");

        //this is deprecated but i don't really give a heck
        /*
        Preference button = (Preference)findPreference(getString(R.string.locationButton));
        button.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Toast.makeText(AppPreferences.this, "Set Location Coord", Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        */
        addPreferencesFromResource(R.xml.preferences);
        Preference locBtn= findPreference("locationButton");
        locBtn.setOnPreferenceClickListener(this);
        setDefaults();
    }

    @Override
    public boolean onPreferenceClick(Preference preference){
        Log.i("Preferences", preference.getKey());

        SharedPreferences latSet = getSharedPreferences("locationLat", MODE_PRIVATE);
        SharedPreferences.Editor lat = latSet.edit();
        lat.putString("locationLat", "EJs Lat");
        lat.commit();

        SharedPreferences lonSet = getSharedPreferences("locationLong", MODE_PRIVATE);
        SharedPreferences.Editor lon = lonSet.edit();
        lon.putString("locationLong", "EJs Lon");
        Toast.makeText(AppPreferences.this, "Lat/Long set to your current location", Toast.LENGTH_SHORT).show();
        lon.commit();

        /*
        Preference lat = findPreference("locationLat");
        Preference lon = findPreference("locationLong");
        if (lat != null && lon != null) {
            //lat.setSummary("EJ set this lat");
            //lon.setSummary("EJ also set this long");
            SharedPreferences.Editor latEditor = lat.getEditor();
            SharedPreferences.Editor lonEditor = lon.getEditor();
            latEditor.putString("locationLat","EJ Lat");
            lonEditor.putString("locationLong","EJ Lon");
            latEditor.commit();
            lonEditor.commit();
            Toast.makeText(AppPreferences.this, "Lat/Long set to your current location", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(AppPreferences.this, "Error trying to get current location", Toast.LENGTH_SHORT).show();
        }
        */
        return true;
    }

    private void setDefaults(){
        SharedPreferences.Editor p = PreferenceManager.getDefaultSharedPreferences(this).edit();

//        Log.d("preferences",ApiHelper.getUser("5552a99c9caa731100000251").toString());

        // set weight and gender from database
        String weight = "155";
        String gender = "Male";

        p.putString("weight", weight);
        p.putString("gender",gender);
        p.commit();
    }
    private class SetDefaults extends AsyncTask<Void,Void,Void>
    {
        @Override
        protected Void doInBackground(Void... params)
        {
            setDefaults();

            return null;
        }
    }
}
