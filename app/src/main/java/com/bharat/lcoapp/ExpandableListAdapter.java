package com.bharat.lcoapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import junit.framework.Test;

import java.util.HashMap;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class ExpandableListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<String> heading;
    private HashMap<String,String> pairs;

    public ExpandableListAdapter(Context cont, List<String> list, HashMap<String,String> hash){
        context = cont;
        heading = list;
        pairs = hash;
    }

    @Override
    public int getGroupCount() {
        return heading.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return 1;
    }

    @Override
    public Object getGroup(int i) {
        return heading.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return pairs.get(heading.get(i));
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {

        String headerTitle = (String) getGroup(i);
        if(view == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.list_grp, null);
        }
        TextView headerTextView = view.findViewById(R.id.heading);
        SharedPreferences prefs = context.getSharedPreferences("Pref", MODE_PRIVATE);
        int fontSize = prefs.getInt("font",0);
        if(fontSize == 0){
            headerTextView.setTextSize(18);
       }else if(fontSize == 1){
            headerTextView.setTextSize(24);
        }else if(fontSize==2){
            headerTextView.setTextSize(32);
        }

        headerTextView.setText(headerTitle);

        return view;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {

        String childTitle = (String) getChild(i,i1);
        if(view == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.list_item, null);
        }
        TextView childTextView = view.findViewById(R.id.child);
        SharedPreferences prefs = context.getSharedPreferences("Pref", MODE_PRIVATE);
        int fontSize = prefs.getInt("font",0);
        if(fontSize == 0){
            childTextView.setTextSize(18);
        }else if(fontSize == 1){
            childTextView.setTextSize(24);
        }else if(fontSize==2){
            childTextView.setTextSize(32);
        }

        childTextView.setText(childTitle);

        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }
}
