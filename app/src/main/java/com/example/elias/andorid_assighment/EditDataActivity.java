package com.example.elias.andorid_assighment;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EditDataActivity extends AppCompatActivity {
private Button btnSave, btnDelete;
private EditText editable_item_name , editable_item_price, editable_item_location, editable_item_quantity ;
HELPER database;
private int seletcedID;
private String product_name, quantity, location;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_data);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("EditDataActivity");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);




        btnDelete = (Button)findViewById(R.id.btndelete);
        btnSave  = (Button)findViewById(R.id.btnsave);

        editable_item_name = (EditText)findViewById(R.id.editableTextname);
        editable_item_price = (EditText)findViewById(R.id.editableTextprice);
        editable_item_location =(EditText)findViewById(R.id.editableTextlocation);
        editable_item_quantity = (EditText)findViewById(R.id.editableTextquantity);
        database = new HELPER(this);

        Intent recievedIntent = getIntent();


        seletcedID = recievedIntent.getIntExtra("id", -1);

        product_name = recievedIntent.getStringExtra("product_name");
        quantity = recievedIntent.getStringExtra("quantity");
        String price = recievedIntent.getStringExtra("price");
        location = recievedIntent.getStringExtra("location");





editable_item_name.setText(product_name);
editable_item_location.setText(location);
editable_item_price.setText(price);
editable_item_quantity.setText(quantity);


btnSave.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
      String newname = editable_item_name.getText().toString();
      String newlocation = editable_item_location.getText().toString();
      double prices = Double.parseDouble(editable_item_price.getText().toString());
     int newquantity = Integer.parseInt(editable_item_quantity.getText().toString());



        if(!newname.equals(""))
        {
            database.updatename(newname,seletcedID, newlocation, prices, newquantity);
            Toast.makeText(EditDataActivity.this, "Successfully  Inserted Records ", Toast.LENGTH_SHORT).show();


        }else{
            Toast.makeText(EditDataActivity.this, "MUST ENTER A NAME", Toast.LENGTH_SHORT).show();
        }

    }
});

btnDelete.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        database.deleteName(seletcedID);
        editable_item_name.setText(" ");
        editable_item_location.setText(" ");
        editable_item_price.setText(" ");
        editable_item_quantity.setText(" ");
        Toast.makeText(EditDataActivity.this, "item deleted from database", Toast.LENGTH_SHORT).show();

    }
});
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

    }





}
