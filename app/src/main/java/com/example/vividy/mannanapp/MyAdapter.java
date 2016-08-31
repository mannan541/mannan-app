package com.example.vividy.mannanapp;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by MANNAN on 3/15/2016.
 */
public class MyAdapter extends BaseAdapter {

//    private ArrayList<MyModel> array_data;
    private ArrayList<String> arrayStringList;
    Context context;

//    public MyAdapter(Context context, ArrayList<MyModel> array_data){
//        this.context = context;
//        this.array_data = array_data;
//        notifyDataSetChanged();
//    }

    public MyAdapter(Context context, ArrayList<String> arrayStringList) {
        this.context = context;
        this.arrayStringList = arrayStringList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return arrayStringList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    static class ViewHolder {
        TextView listrowName, listrowsubNumber, dateTimeStamp;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            LayoutInflater layoutInflater = ((Activity) context).getLayoutInflater();
            convertView = layoutInflater.inflate(R.layout.list_row_calendars_events, null);
            holder = new ViewHolder();

            holder.listrowName = (TextView) convertView.findViewById(R.id.inquiry_contact_name);
            holder.listrowsubNumber = (TextView) convertView.findViewById(R.id.inquiry_contact_number);
            holder.dateTimeStamp = (TextView) convertView.findViewById(R.id.inquiry_date_time_stamp);

            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.listrowName.setText(arrayStringList.get(position));
//        holder.listrowName.setText(array_data.get(position).eventName);
//        holder.listrowsubNumber.setText(array_data.get(position).description);
//        holder.dateTimeStamp.setText("start date: "
//                            +array_data.get(position).startDate
//                            +", end date: "+array_data.get(position).endDate);

        return convertView;
    }
}
