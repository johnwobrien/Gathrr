package gathrr.gathrr;

import android.app.ListActivity;
import android.content.Intent;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.activity_history);
        }
        catch(Exception ex){ ex.printStackTrace();}

        ListView listview = (ListView)findViewById(R.id.historylist);

        JSONObject history = ApiHelper.getHistory(userId);
        HistoryItemAdapter histAdapter = new HistoryItemAdapter(this, history);
        listview.setAdapter(histAdapter);
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
}
