package com.alddeul.capstone;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class list_adapter extends ArrayAdapter<listitemActivity> {


    public list_adapter(Context context, ArrayList<listitemActivity> listitemList){
        super(context,R.layout.list_item,listitemList);


    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        listitemActivity item = getItem(position);

        if(convertView==null)
        {
            convertView= LayoutInflater.from(getContext()).inflate(R.layout.list_item,parent,false);
        }

        TextView list=convertView.findViewById(R.id.listtext);

        list.setText(item.fileid);


        return super.getView(position, convertView, parent);
    }
}