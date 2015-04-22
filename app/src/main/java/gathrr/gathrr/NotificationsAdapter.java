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
public class NotificationsAdapter extends BaseAdapter {
    private Activity            context;
    private List<Notification>  notifications;
    private boolean             notifyChanged = false;

    public NotificationsAdapter(Activity context, JSONObject notificationsJSON) {
        super();
        this.context = context;
        this.notifications = parseHistory(notificationsJSON);
    }

    private List<Notification> parseHistory(JSONObject notificationsJSON)
    {
        List<Notification> notificationsList = new ArrayList<Notification>();
        Iterator<String> it = notificationsJSON.keys();
        String key = "";
        Notification not;
        JSONObject notificationJSONItem;
        while(it.hasNext())
        {
            key = it.next();
            not = new Notification();
            try {
                not.Fighter = key;
                notificationJSONItem = notificationsJSON.getJSONObject(key);
                not.Date = notificationJSONItem.getString("Date");
                notificationsList.add(not);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return notificationsList;
    }

    public int getCount() {
        return notifications.size();
    }

    public Object getItem(int position) {
        return notifications.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public static class ItemViewHolder {
        TextView notificationText;
        TextView notificationDate;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ItemViewHolder holder;
        LayoutInflater inflater = context.getLayoutInflater();
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.activity_history_item, null);
            holder = new ItemViewHolder();
            holder.notificationText = (TextView) convertView.findViewById(R.id.notificationText);
            holder.notificationDate = (TextView) convertView.findViewById(R.id.notificationDate);

            convertView.setTag(holder);
        }
        else {
            holder = (ItemViewHolder) convertView.getTag();
        }

        Notification not = notifications.get(position);

        holder.notificationText.setText(not.Fighter + " wants to fight!");
        holder.notificationDate.setText(not.Date);

        return convertView;
    }

    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
        notifyChanged = true;
    }
}
