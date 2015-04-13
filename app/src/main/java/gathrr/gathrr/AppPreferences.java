package gathrr.gathrr;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by jshippling on 2/27/15.
 */

public class AppPreferences extends PreferenceActivity implements Preference.OnPreferenceClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setDefaults();
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
    }

    @Override
    public boolean onPreferenceClick(Preference preference){
        Log.i("Preferences", preference.getKey());
        Toast.makeText(AppPreferences.this, "Set Location Coord", Toast.LENGTH_SHORT).show();
        return true;
    }

    private void setDefaults(){
        SharedPreferences.Editor p = PreferenceManager.getDefaultSharedPreferences(this).edit();

        // set weight and gender from database
        String weight = "123";
        String gender = "Other";

        p.putString("weight", weight);
        p.putString("gender",gender);
        p.commit();
    }





}