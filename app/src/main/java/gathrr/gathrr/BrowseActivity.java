package gathrr.gathrr;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

/**
 * Created by Andrew on 2/25/2015.
 */
public class BrowseActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.browse);
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
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
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

    public void nextFighter()
    {

    }

    public void addToViewed()
    {

    }
}