package com.example.elias.andorid_assighment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by ELIAS on 13/09/2019.
 */

public class CustomListAdp extends ArrayAdapter<String> {
    private final Context context;
    private final ArrayList<String> values;
    private int k = 1;
    HELPER db;


    ViewHolder viewHolder;
    ViewHolder hello;
    public CustomListAdp(Context context, ArrayList<String> values){
        super(context,R.layout.pantrylayout, values);
        this.context = context;
        this.values = values;}
    private class  ViewHolder
    {
        ImageView photo;
        TextView name;

        public TextView getName() {
            return name;
        }

    }

    public View getView(final int position, View convertView, ViewGroup parent){
        if(convertView==null)
        {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(R.layout.pantrylayout, null);
            viewHolder=new ViewHolder();


            //cache the views
            viewHolder.photo=(ImageView) convertView.findViewById(R.id.icon2);
            viewHolder.name=(TextView) convertView.findViewById(R.id.label2);

            //link the cached views to the convertview
            convertView.setTag( viewHolder);

        }
        else
            viewHolder=(ViewHolder) convertView.getTag();
        db = new HELPER(this.context);
        viewHolder.name.setText(values.get(position));


String pal = values.get(position);

int itemID = Character.getNumericValue(pal.charAt(0));


        viewHolder.photo.setImageBitmap(db.showpic(itemID));

        return convertView;
    }



}
