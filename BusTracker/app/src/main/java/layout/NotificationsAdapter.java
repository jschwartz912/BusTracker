package layout;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import juniordesignteam25.bustracker.R;
import juniordesignteam25.bustracker.singleClassNotUsedAnymore;


/**
 * Created by nicol on 3/2/2016.
 */
public class NotificationsAdapter extends ArrayAdapter<singleClassNotUsedAnymore> {

    Context context;
    int layoutResourceId;
    singleClassNotUsedAnymore notifications[] = null;

    public NotificationsAdapter(Context context, int layoutResourceId, singleClassNotUsedAnymore[] notifications) {
        super(context, layoutResourceId, notifications);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.notifications = notifications;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View rowView = inflater.inflate(R.layout.class_row, parent, false);
        TextView className = (TextView) rowView.findViewById(R.id.firstLine);
        TextView placeAndTime = (TextView) rowView.findViewById(R.id.secondLine);
        CheckBox checkbox = (CheckBox) rowView.findViewById(R.id.checkbox);
        singleClassNotUsedAnymore c = notifications[position];
        className.setText(c.getName());
        placeAndTime.setText(c.getLocation() + " @ " + c.getStartTime());
        checkbox.setChecked(true);

        return rowView;
    }
}
