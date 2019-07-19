package com.misis.brs.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.misis.brs.Database.Hometask;
import com.misis.brs.R;

import java.util.Vector;

public class HometasksViewAdapter extends BaseAdapter {

    private Context context;
    private Vector<Hometask> hometasks;

    public HometasksViewAdapter(Context context,Vector<Hometask> hometasks){
        this.context = context;
        this.hometasks = hometasks;
    }

    @Override
    public int getCount() {
        return hometasks.size();
    }

    @Override
    public Object getItem(int position) {
        return hometasks.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.view_task_simple_item,null);

        ((TextView) view.findViewById(R.id.taskText)).setText(hometasks.get(position).getDescription());
        //TODO разобраться со временем
        ((TextView) view.findViewById(R.id.taskDate)).setText(hometasks.get(position).getDeadline()+ "");
        ((CheckBox) view.findViewById(R.id.notification)).setChecked(hometasks.get(position).getCheckNotify());
        ((CheckBox) view.findViewById(R.id.done)).setChecked(hometasks.get(position).getCheckDone());

        return view;
    }
}
