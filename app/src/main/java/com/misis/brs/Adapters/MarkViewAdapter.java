package com.misis.brs.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.misis.brs.Database.Mark;
import com.misis.brs.R;

import java.util.Vector;

public class MarkViewAdapter extends BaseAdapter{

    private Context context;
    private static Vector<Mark> marks;

    public MarkViewAdapter(Context context, Vector<Mark> marks)
    {
        this.context = context;
        this.marks = marks;

    }

    public static void setMarks(Vector<Mark> marks) {
        MarkViewAdapter.marks = marks;
    }

    @Override
    public int getCount() {
        return marks.size();
    }

    @Override
    public Object getItem(int position) {
        return marks.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.view_mark_simple_item, null);

        ((TextView) view.findViewById(R.id.markType)).setText(marks.get(position).getMarkType().name());
        ((TextView) view.findViewById(R.id.description)).setText(marks.get(position).getDescription());

        String mark = marks.get(position).getMark() + "/" + marks.get(position).getMaxMark();

        ((TextView) view.findViewById(R.id.mark)).setText(mark);
        return view;
    }
}