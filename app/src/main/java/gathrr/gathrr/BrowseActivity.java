package gathrr.gathrr;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.conn.ssl.BrowserCompatHostnameVerifier;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;

import gathrr.utility.ApiHelper;

/**
 * Created by Andrew on 2/25/2015.
 */
public class BrowseActivity extends ActionBarActivity implements View.OnClickListener {

    private static String TAG = "BrowseActivity";

    ImageView fighterImage;
    TextView browseMessage;
    Button fight;
    Button dontFight;
    ProgressBar loading;
    String userId = "user1";
    String fighterId;
    JSONObject fighter;
    ImageView imgView;
    Bitmap bmp;

    int timeout = 4000; // make the activity visible for 4 seconds

    // Variables for handling Gestures
    // Reference: http://stackoverflow.com/questions/937313/android-basic-gesture-detection
    private static final int SWIPE_MIN_DISTANCE = 120;
    private static final int SWIPE_MAX_OFF_PATH = 250;
    private static final int SWIPE_THRESHOLD_VELOCITY = 200;
    private GestureDetector gestureDetector;
    View.OnTouchListener gestureListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //init
        super.onCreate(savedInstanceState);
        setContentView(R.layout.browse);
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
        fighterImage = (ImageView) findViewById(R.id.fighterImage);
        browseMessage = (TextView) findViewById(R.id.browseMessage);
        imgView = (ImageView) findViewById(R.id.fighterImage);
        fight = (Button) findViewById(R.id.btnFight);
        dontFight = (Button) findViewById(R.id.btnDontFight);
        loading = (ProgressBar) findViewById(R.id.loading);

        //load the fighter
        loadingFighterUp();
        new NextFighter().execute();

        // Gesture detection
        gestureDetector = new GestureDetector(this, new MyGestureDetector());
        gestureListener = new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                return gestureDetector.onTouchEvent(event);
            }
        };

        imgView.setOnClickListener(BrowseActivity.this);
        imgView.setOnTouchListener(gestureListener);
    }

    public void onClick(View v) {
        Log.i(TAG, "onClick");
        /*
        Filter f = (Filter) v.getTag();
        FilterFullscreenActivity.show(this, input, f);
        */
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
        Intent intent;
        switch (item.getItemId()) {
            case R.id.action_settings:
                Log.d("menu", "preferences");
                startActivity(new Intent(this, AppPreferences.class));
                return true;
            case R.id.action_history:
                intent = new Intent(this, HistoryActivity.class);
                this.startActivity(intent);
                return true;
            case R.id.action_notifications:
                intent = new Intent(this, NotificationsActivity.class);
                this.startActivity(intent);
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

    private void loadingFighterUp()
    {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //fight.setVisibility(View.INVISIBLE);
                //dontFight.setVisibility(View.INVISIBLE);
                fight.setEnabled(false);
                dontFight.setEnabled(false);
                browseMessage.setText(R.string.loading_next_fighter);
                imgView.setVisibility(View.INVISIBLE);
                loading.setVisibility(View.VISIBLE);
            }
        });
    }

    private void loadingFighterDown(JSONObject ftr)
    {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                fight.setVisibility(View.VISIBLE);
                dontFight.setVisibility(View.VISIBLE);
                fight.setEnabled(true);
                dontFight.setEnabled(true);
            }
        });

        //set message
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

        setFighterImage(fighter);

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                loading.setVisibility(View.INVISIBLE);
                imgView.setVisibility(View.VISIBLE);
            }
        });
    }

    private void nextFighter()
    {
        Log.i(TAG, "nextFighter");
        fighter = ApiHelper.getNextFighter(userId);
        if (fighter == null) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    browseMessage.setText(R.string.waiting_next_fighter);
                    fight.setVisibility(View.INVISIBLE);
                    dontFight.setVisibility(View.INVISIBLE);
                    fight.setEnabled(false);
                    dontFight.setEnabled(false);
                }
            });

            try {
                Thread.sleep(timeout);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            nextFighter();
            return ;
        }
        loadingFighterDown(fighter);
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

    //-----------------------------background actions-----------------------------------------------


    private class AcceptFight extends AsyncTask<Void,Void,Void>
    {
        @Override
        protected Void doInBackground(Void... params)
        {
            loadingFighterUp();
            Log.i(TAG, "AcceptFight");
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
            loadingFighterUp();
            Log.i(TAG, "DenyFight");
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

    class MyGestureDetector extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            final Animation animation = new AlphaAnimation(1, 0); // Change alpha from fully visible to invisible
            animation.setDuration(500); // duration - half a second
            animation.setInterpolator(new LinearInterpolator()); // do not alter animation rate
            animation.setRepeatCount(2); // Repeat animation infinitely
            animation.setRepeatMode(Animation.REVERSE); // Reverse animation at the end so the button will fade back in
            try {
                if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH)
                    return false;
                // right to left swipe
                if(e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                    Log.i(TAG, "onFling Left");
                    // These Toasts are here for Debug
                    //Toast.makeText(BrowseActivity.this, "Left Swipe", Toast.LENGTH_SHORT).show();
                    final Button btn = (Button) findViewById(R.id.btnDontFight);
                    btn.startAnimation(animation);
                    new DenyFight().execute();
                }  else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                    Log.i(TAG, "onFling Right");
                    // These Toasts are here for Debug
                    //Toast.makeText(BrowseActivity.this, "Right Swipe", Toast.LENGTH_SHORT).show();
                    final Button btn = (Button) findViewById(R.id.btnFight);
                    btn.startAnimation(animation);
                    new AcceptFight().execute();
                }
            } catch (Exception e) {
                // nothing
            }
            return false;
        }

        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }
    }
}


