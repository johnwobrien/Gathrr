package gathrr.gathrr;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

/**
 * Created by jshippling on 2/27/15.
 */

public class AppPreferences extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setDefaults();
        //this is deprecated but i don't really give a heck
        addPreferencesFromResource(R.xml.preferences);
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