package gathrr.gathrr;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.URL;

import gathrr.utility.ApiHelper;

/**
 * Created by Andrew on 2/25/2015.
 */
public class BrowseActivity extends ActionBarActivity {

    ImageView fighterImage;
    String userId = "user1";
    JSONObject fighter;
    Drawable nextFighterImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.browse);
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
        fighterImage = (ImageView) findViewById(R.id.fighterImage);
        new NextFighter().execute();
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
        new AcceptFight().execute();
        fighterImage.setImageDrawable(nextFighterImage);
    }

    public void denyFight(View view)
    {
        new DenyFight().execute();
    }

    public static Drawable LoadImageFromWebOperations(String url) {
        try {
            InputStream is = (InputStream) new URL(url).getContent();
            Drawable d = Drawable.createFromStream(is, "src name");
            return d;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    private void setFighterImage(String src)
    {
        ImageView imgView = (ImageView) findViewById(R.id.fighterImage);
        imgView.setImageURI(Uri.parse(src));
    }

    private class AcceptFight extends AsyncTask<Void,Void,Void>
    {
        @Override
        protected Void doInBackground(Void... params)
        {
            Drawable pic = LoadImageFromWebOperations("https://placekitten.com/g/200/300");
            nextFighterImage = pic;
            /*
            //add to viewed fighters
            String id;
            try {
                id = fighter.getString("id");
            }
            catch(JSONException ex)
            {
                id = "";
            }
            ApiHelper.addSeen(id);

            //send notification to the accepted fighter

            //present next fighter
            fighter = ApiHelper.getNextFighter(userId);
            */
            return null;
        }
    }
    private class DenyFight extends AsyncTask<Void,Void,Void>
    {
        @Override
        protected Void doInBackground(Void... params)
        {
            //add to viewed fighters
            String id;
            try {
                id = fighter.getString("id");
            }
            catch(JSONException ex)
            {
                id = "";
            }
            ApiHelper.addSeen(id);

            //present next fighter
            fighter = ApiHelper.getNextFighter(userId);
            return null;
        }
    }
    private class NextFighter extends AsyncTask<Void,Void,Void>
    {
        @Override
        protected Void doInBackground(Void... params)
        {
            //present next fighter
            fighter = ApiHelper.getNextFighter(userId);

            return null;
        }
    }
}


