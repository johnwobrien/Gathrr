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


public class HistoryActivity extends ListActivity {

    private String userId = "user4";
    ListView listview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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


    private class CreateList implements Runnable {
        public HistoryActivity activity;
        HistoryItemAdapter histAdapter;

        public void setActivity(HistoryActivity a) {
            this.activity = a;
        }

        @Override
        public void run() {
            JSONObject history = ApiHelper.getHistory(userId);
            histAdapter = new HistoryItemAdapter(activity, history);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    listview.setAdapter(histAdapter);
                }
            });
        }
    }
}
