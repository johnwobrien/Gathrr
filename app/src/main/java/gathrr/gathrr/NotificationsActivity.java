package gathrr.gathrr;

import android.app.ListActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

import gathrr.utility.ApiHelper;
import gathrr.utility.User;


public class NotificationsActivity extends ListActivity {

    private String userId;
    ListView listview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        userId = User.User;

        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.activity_history);
        }
        catch(Exception ex){ ex.printStackTrace();}

        listview = (ListView)findViewById(R.id.historylist);
        CreateList task = new CreateList();
        task.setActivity(this);
        new Thread(task).start();
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
            case R.id.signout:
                intent = new Intent(this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                this.startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private class CreateList implements Runnable {
        public NotificationsActivity activity;
        NotificationsAdapter notAdapter;

        public void setActivity(NotificationsActivity a) {
            this.activity = a;
        }

        @Override
        public void run() {
            JSONObject notifications = ApiHelper.getNotifications(userId);
            notAdapter = new NotificationsAdapter(activity, notifications);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    listview.setAdapter(notAdapter);
                }
            });
        }
    }
}
