package gathrr.gathrr;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Andrew on 3/30/2015.
 */
public class HistoryItemAdapter extends BaseAdapter {
    private Activity        context;
    private List<History>   history;
    private boolean         notifyChanged = false;

    public HistoryItemAdapter(Activity context, JSONObject historyJSON) {
        super();
        this.context = context;
        this.history = parseHistory(historyJSON);
    }

    private List<History> parseHistory(JSONObject historyJSON)
    {
        List<History> historyList = new ArrayList<History>();
        Iterator<String> it = historyJSON.keys();
        String key = "";
        History hist;
        JSONObject historyJSONItem;
        while(it.hasNext())
        {
            key = it.next();
            hist = new History();
            try {
                hist.FightTitle = key;
                historyJSONItem = historyJSON.getJSONObject(key);
                hist.Date = historyJSONItem.getString("Date");
                hist.ELOChange = historyJSONItem.getString("ELOChange");
                hist.Outcome = historyJSONItem.getString("Outcome");
                history.add(hist);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return historyList;
    }

    public int getCount() {
        return history.size();
    }

    public Object getItem(int position) {
        return history.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public static class ItemViewHolder {
        TextView fightTitle;
        TextView outcome;
        TextView date;
        TextView eloChange;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ItemViewHolder holder;
        LayoutInflater inflater = context.getLayoutInflater();
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.activity_history_item, null);
            holder = new ItemViewHolder();
            holder.fightTitle = (TextView) convertView.findViewById(R.id.fightTitle);
            holder.outcome = (TextView) convertView.findViewById(R.id.outcome);
            holder.date = (TextView) convertView.findViewById(R.id.date);
            holder.eloChange = (TextView) convertView.findViewById(R.id.elochange);

            convertView.setTag(holder);
        }
        else {
            holder = (ItemViewHolder) convertView.getTag();
        }

        History his = history.get(position);

        holder.fightTitle.setText(his.FightTitle);
        holder.outcome.setText(his.Outcome);
        holder.date.setText(his.Date);
        holder.eloChange.setText(his.ELOChange);

        return convertView;
    }

    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
        notifyChanged = true;
    }
}
