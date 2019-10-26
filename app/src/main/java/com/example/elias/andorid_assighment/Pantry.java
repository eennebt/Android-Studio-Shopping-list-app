package com.example.elias.andorid_assighment;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class Pantry extends MainActivity {
    public ListView pantry;

    public ArrayList<String> arrayList;
    CustomListAdp listAdp = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantry);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Pantry");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        pantry = (ListView) findViewById(R.id.pantry2);
        ArrayAdapter<String> items = null;
        Intent recievedIntent = getIntent();

        arrayList = recievedIntent.getStringArrayListExtra("list");


        listAdp = new CustomListAdp(Pantry.this, arrayList);

        if (listAdp != null) {
            pantry.setAdapter(listAdp);
        }else {
        pantry.setAdapter(null);
        }
        }

    public void Menu(View view) {
        Intent intent = new Intent(this, Homepage.class);
        startActivity(intent);
    }
    }




