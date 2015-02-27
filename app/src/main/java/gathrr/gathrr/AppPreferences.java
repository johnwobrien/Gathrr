package gathrr.gathrr;

import android.os.Bundle;
import android.preference.PreferenceActivity;

/**
 * Created by jshippling on 2/27/15.
 */

public class AppPreferences extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //this is deprecated but i don't really give a heck
        addPreferencesFromResource(R.xml.preferences);
    }

}