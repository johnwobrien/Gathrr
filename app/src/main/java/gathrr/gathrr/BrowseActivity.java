package gathrr.gathrr;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import FightrConnection.FightrDBClient;
import java.util.HashMap;
import android.net.Uri;

/**
 * Created by Andrew on 2/25/2015.
 */
public class BrowseActivity extends ActionBarActivity {

    ImageView fighterImage;
    String userId = "user0";
    FightrDBClient client = new FightrDBClient();
    HashMap<String, Object> fighter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.browse);
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
        fighterImage = (ImageView) findViewById(R.id.fighterImage);
        nextFighter();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.action_settings:
                Log.d("menu", "preferences");
                startActivity(new Intent(this, AppPreferences.class));
                return true;
            case R.id.action_history:
                Log.d("menu","fight history");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void acceptFight(View view)
    {
        //add to viewed fighters
        addToViewed();

        //send notification to the accepted fighter

        //present next fighter
        nextFighter();
    }

    public void denyFight(View view)
    {
        //add to viewed fighters
        addToViewed();

        //present next fighter
        nextFighter();
    }

    private void nextFighter()
    {
        fighter = client.getAllNotSeen(userId).get(0);
        String src = fighter.get("picture").toString();
        setFighterImage(src);
    }

    private void addToViewed()
    {
        client.addSeen(userId, fighter.get("id").toString());
    }

    private void setFighterImage(String src)
    {
        ImageView imgView = (ImageView) findViewById(R.id.fighterImage);
        imgView.setImageURI(Uri.parse(src));
    }
}
