package gathrr.gathrr;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

import gathrr.utility.ApiHelper;


public class NoFightersActivity extends ActionBarActivity {

    int timeout = 4000; // make the activity visible for 4 seconds
    Timer timer = new Timer();
    JSONObject fighter;
    String userId = "user1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_fighters);

        lookForNewFighter();
    }

    private void lookForNewFighter()
    {
        timer.schedule(new TimerTask() {

            @Override
            public void run() {
                fighter = ApiHelper.getNextFighter(userId);
                if(fighter != null) {
                    finish();
                    Intent homepage = new Intent(NoFightersActivity.this, BrowseActivity.class);
                    startActivity(homepage);
                    return;
                }
                lookForNewFighter();
            }
        }, timeout);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_no_fighters, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
