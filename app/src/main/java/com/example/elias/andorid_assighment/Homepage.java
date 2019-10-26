package com.example.elias.andorid_assighment;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import java.util.Calendar;

public class Homepage extends AppCompatActivity {
    private Button Shopping, pantry;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Homepage");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        Shopping = findViewById(R.id.Shopping);

        Shopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Shooping_List();
            }
        });

final Button calin = findViewById(R.id.Calender);
calin.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Calendar cal = Calendar.getInstance();
        Intent intent = new Intent(Intent.ACTION_EDIT);
        intent.setType("vnd.android.cursor.item/event");
        intent.putExtra("beginTime", cal.getTimeInMillis());
        intent.putExtra("allDay", true);
        intent.putExtra("rrule", "FREQ=YEARLY");
        intent.putExtra("endTime", cal.getTimeInMillis()+60*60*1000);
        intent.putExtra("title", "A Test Event from android app");
        startActivity(intent);
    }
});
    }
    public void Shooping_List()
    {
        Intent intent1 = new Intent(this, MainActivity.class);
        startActivity(intent1);
    }

}
