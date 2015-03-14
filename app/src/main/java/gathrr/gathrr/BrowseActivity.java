package gathrr.gathrr;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import gathrr.utility.ApiHelper;

/**
 * Created by Andrew on 2/25/2015.
 */
public class BrowseActivity extends ActionBarActivity {

    ImageView fighterImage;
    TextView browseMessage;
    String userId = "user1";
    String fighterId;
    JSONObject fighter;
    ImageView imgView;
    Bitmap bmp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.browse);
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
        fighterImage = (ImageView) findViewById(R.id.fighterImage);
        browseMessage = (TextView) findViewById(R.id.browseMessage);
        imgView = (ImageView) findViewById(R.id.fighterImage);
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

    private void setMessage(JSONObject ftr)
    {
        try {
            fighterId = ftr.getString("id");
        }
        catch(JSONException ex)
        {
            fighterId = "unknown";
        }

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                browseMessage.setText("Would you like to fight " + fighterId);
            }
        });

    }

    private void nextFighter()
    {
        fighter = ApiHelper.getNextFighter(userId);
        setFighterImage(fighter);
        setMessage(fighter);
    }

    private void setFighterImage(String src)
    {
        try {
            URL url = new URL(src);
            bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    imgView.setImageBitmap(bmp);
                }
            });
        }
        catch(IOException ex)
        {
            System.err.print(ex.getStackTrace());
        }
    }

    private void setFighterImage(JSONObject ftr)
    {
        String src;
        try {
            src = ftr.getString("picture");
        }
        catch(JSONException ex)
        {
            src = "https://placekitten.com/g/200/300";
        }
        setFighterImage(src);
    }

    private class AcceptFight extends AsyncTask<Void,Void,Void>
    {
        @Override
        protected Void doInBackground(Void... params)
        {
            //add to viewed fighters
            String idSeen;
            try {
                idSeen = fighter.getString("id");
            }
            catch(JSONException ex)
            {
                idSeen = "";
            }
            ApiHelper.addSeen(userId, idSeen);

            //send notification to the accepted fighter

            //present next fighter
            nextFighter();

            return null;
        }
    }
    private class DenyFight extends AsyncTask<Void,Void,Void>
    {
        @Override
        protected Void doInBackground(Void... params)
        {
            //add to viewed fighters
            String idSeen;
            try {
                idSeen = fighter.getString("id");
            }
            catch(JSONException ex)
            {
                idSeen = "";
            }
            ApiHelper.addSeen(userId, idSeen);

            //present next fighter
            nextFighter();

            return null;
        }
    }
    private class NextFighter extends AsyncTask<Void,Void,Void>
    {
        @Override
        protected Void doInBackground(Void... params)
        {
            //present next fighter
            nextFighter();

            return null;
        }
    }
}


