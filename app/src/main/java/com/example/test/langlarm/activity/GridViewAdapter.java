package com.example.test.langlarm.activity;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.test.langlarm.R;
import com.example.test.langlarm.alarm.Alarm;

import java.util.List;

public class GridViewAdapter extends ArrayAdapter<Alarm> {

    private Context mContext;
    private int resourceId;
    private List<Alarm> data;

    public GridViewAdapter(Context context, int layoutResourceId, List<Alarm> items) {
        super(context, layoutResourceId, items);
        this.mContext = context;
        this.resourceId = layoutResourceId;
        this.data = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View itemView = convertView;
        ViewHolder holder;

        if (itemView == null) {
            final LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            itemView = layoutInflater.inflate(resourceId, parent, false);

            holder = new ViewHolder();
            holder.imgItem = (ImageView) itemView.findViewById(R.id.imgItem);
            holder.txtItem = (TextView) itemView.findViewById(R.id.txtItem);
            itemView.setTag(holder);
        } else {
            holder = (ViewHolder) itemView.getTag();
        }

        Alarm item = getItem(position);
        holder.imgItem.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.alarm));
        holder.txtItem.setText(item.getFormattedTime());

        return itemView;
    }

    static class ViewHolder {
        ImageView imgItem;
        TextView txtItem;
    }

}