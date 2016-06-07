package layout;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

import juniordesignteam25.bustracker.R;
import juniordesignteam25.bustracker.singleClassNotUsedAnymore;

/**
 * Created by yuetinggg on 2/12/16.
 */
public class exportClassListAdapter extends BaseAdapter{
    Context context;
    protected List<singleClassNotUsedAnymore> classes;
    LayoutInflater inflater;

    public exportClassListAdapter(Context context, List<singleClassNotUsedAnymore> classes) {
        this.context = context;
        this.classes = classes;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return classes.size();
    }

    @Override
    public Object getItem(int position) {
        return classes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return (long) classes.get(position).getClassNum();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        System.out.println(classes.size());
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.class_row, parent, false);
        TextView className = (TextView) rowView.findViewById(R.id.firstLine);
        TextView placeAndTime = (TextView) rowView.findViewById(R.id.secondLine);
        CheckBox imageView = (CheckBox) rowView.findViewById(R.id.checkbox);
        singleClassNotUsedAnymore c = classes.get(position);
        className.setText(c.getName());
        placeAndTime.setText(c.getLocation() + " @ " + c.getStartTime() + "-" + c.getEndTime());



        return rowView;
    }
}
