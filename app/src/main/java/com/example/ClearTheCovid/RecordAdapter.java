package com.example.ClearTheCovid;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class RecordAdapter extends BaseAdapter {
    private List<Record> recordList;
    private Context context;


    public RecordAdapter(List<Record> recordList, Context context) {
        this.recordList = recordList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return recordList.size();
    }

    @Override
    public Object getItem(int position) {
        return recordList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.record_layout, parent,false);
        }
        Record record = recordList.get(position);
        TextView username = convertView.findViewById(R.id.username);
        TextView points = convertView.findViewById(R.id.points);
        TextView recordIndex = convertView.findViewById(R.id.record_index);

        username.setText(record.getName());
        points.setText(String.valueOf(record.getPoint()));
        recordIndex.setText(record.getId());

        return convertView;
    }
}
