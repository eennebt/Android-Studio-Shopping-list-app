package com.example.elias.andorid_assighment;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by ELIAS on 3/09/2019.
 */

public class CustomAdapter extends ArrayAdapter<String> {

    private final Context context;
    private final ArrayList<String> values;
    private boolean checked = false;
    private int i = 1;
    HELPER db;

// hello world
    boolean[] checkboxState;

    ViewHolder viewHolder;
    ViewHolder hello;
    public CustomAdapter(Context context, ArrayList<String> values){
        super(context,R.layout.rowlayout, values);
        this.context = context;
        this.values = values;
        checkboxState = new boolean[values.size()];
    }
    private class  ViewHolder
    {
        ImageView photo;
        TextView name;
        CheckBox checkBox;


        public CheckBox getCheckBox() {
            return checkBox;
        }

        public TextView getName() {
            return name;
        }

    }

    public View getView(final int position, View convertView, ViewGroup parent){
        if(convertView==null)
        {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(R.layout.rowlayout, null);
            viewHolder=new ViewHolder();


            //cache the views
            viewHolder.photo=(ImageView) convertView.findViewById(R.id.icon);
            viewHolder.name=(TextView) convertView.findViewById(R.id.label);
            viewHolder.checkBox=(CheckBox) convertView.findViewById(R.id.checkBox);

            //link the cached views to the convertview
            convertView.setTag( viewHolder);

        }
        else
            viewHolder=(ViewHolder) convertView.getTag();
db = new HELPER(this.context);
        viewHolder.name.setText(values.get(position));

        String s = values.get(position);
        int itemID = Character.getNumericValue(s.charAt(0));


        viewHolder.photo.setImageBitmap(db.showpic(itemID));
        i++;
        viewHolder.checkBox.setChecked(checkboxState[position]);


        //for managing the state of the boolean
        //array according to the state of the
        //CheckBox

        viewHolder.checkBox.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                if(((CheckBox)v).isChecked()) {
                    checkboxState[position] = true;
                }
                else
                    checkboxState[position]=false;

            }
        });



        return convertView;
    }

    public boolean[] getCheckBoxState(){
        return checkboxState;
    }

    public String getName(int pos){
        String val = values.get(pos);
        return val;
    }




}