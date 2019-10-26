package com.example.elias.andorid_assighment;

import android.content.ClipData;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TextView;
import android.graphics.BitmapFactory;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
/******************************************************************************** */
    /*FUNCTIONS */
    HELPER helper;
    private MenuItem Insert, View;
    public boolean recInserted, isRecInserted;
    private ListView Inserted;
    private TextView text, showrec;
    private Button insert, Main_Menu, ITEM_IMAGE, BUY;
    private TableLayout tableLayout, table;
    public EditText product_name, price, quantity, location;
    private ImageView imageView;
    private static int RESULT_LOAD_IMAGE = 2;
    public ListView pantry;
    boolean[] checkboxes;

    CustomAdapter adapter;
    int boxIndex = 0;

/******************************************************************************** */



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Shops");
/******************************************************************************** */


    /*BUTTONS AND VIEWS  */

        tableLayout = (TableLayout) findViewById(R.id.layout);
        insert = (Button) this.findViewById(R.id.insert);
        insert.setVisibility(android.view.View.GONE);
        Main_Menu = (Button) this.findViewById(R.id.show);
        Inserted = (ListView) this.findViewById(R.id.inserted);
        BUY = (Button) this.findViewById(R.id.Buy);
        pantry = (ListView) findViewById(R.id.pantry2);
        BUY.setVisibility(android.view.View.GONE);
        tableLayout.setVisibility(android.view.View.GONE);
        helper = new HELPER(MainActivity.this);
        imageView = (ImageView) findViewById(R.id.item_image);
        ITEM_IMAGE = (Button) findViewById(R.id.selectBtn);

/******************************************************************************** */
    /*BUTTONS AND VIEWS  */

        ITEM_IMAGE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenGallery();
            }
        });

        BUY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Submit();
                Toast.makeText(MainActivity.this, "Successfully  Inserted Records ", Toast.LENGTH_SHORT).show();

            }
        });
    /*INSERT INTO DATABASE  */

        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                helper.openReadable();

                product_name = (EditText) findViewById(R.id.product_name);
                quantity = (EditText) findViewById(R.id.quantity);
                price = (EditText) findViewById(R.id.price);
                location = (EditText) findViewById(R.id.location);


                Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] imageInByte = baos.toByteArray();


                recInserted = helper.addRow(Integer.parseInt(quantity.getText().toString()), product_name.getText().toString(), Double.parseDouble(price.getText().toString()), location.getText().toString(), imageInByte);

                if (recInserted) {
                    System.out.println("CORRECT ");
                } else {
                    System.out.println("INCORRECT ");

                }

                helper.close();

                product_name.setText("");
                quantity.setText("");
                price.setText("");
                location.setText("");
                imageView.setImageResource(R.mipmap.ic_launcher);


            }

        });
        helper.close();




        Main_Menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainMenu();
            }
        });

/******************************************************************************** */


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (item.getItemId()) {
            case R.id.Add:
                addRec();
                break;
            case R.id.View_Record:
                showRec();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
    CustomAdapter adpt;
    public boolean showRec() {
        Inserted.setVisibility(android.view.View.VISIBLE);
        BUY.setVisibility(android.view.View.VISIBLE);
        tableLayout.setVisibility(android.view.View.GONE);
        helper.openReadable();

        ArrayList < String > CONTENT = helper.retriveRows();


        adpt = new CustomAdapter(this, CONTENT);
        Inserted.setAdapter(adpt);
        Inserted.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView <?> adapterView, View view, int i, long l) {
                String name = adapterView.getItemAtPosition(i).toString();
                String[] token = name.split("\\n");
                int itemID = Character.getNumericValue(name.charAt(0));

                String product_name = token[1];
                String quantity = token[2];
                String price = token[3];
                String location = token[4];

                Cursor data = helper.getItemID(name);

                if (itemID > -1) {
                    Intent editScreenIntent = new Intent(MainActivity.this, EditDataActivity.class);
                    editScreenIntent.putExtra("id", itemID);
                    editScreenIntent.putExtra("product_name", product_name);
                    editScreenIntent.putExtra("quantity", quantity);
                    editScreenIntent.putExtra("price", price);
                    editScreenIntent.putExtra("location", location);

                    startActivity(editScreenIntent);

                } else {
                    System.out.println("DOENST EXSIST ");
                }

            }
        });
        helper.close();
        return true;
    }

    public void MainMenu() {
        Intent intent = new Intent(this, Homepage.class);
        startActivity(intent);
    }

    public boolean addRec() {
        insert.setVisibility(android.view.View.VISIBLE);
        tableLayout.setVisibility(android.view.View.VISIBLE);
        Inserted.setVisibility(android.view.View.GONE);
        return true;
    }

    public void setPantry(View view) {
        Intent intent123 = new Intent(this, Pantry.class);

        intent123.putExtra("list", st);

        startActivity(intent123);
    }
    public void DeleteDB(View view) {
        helper.clearRecords();
        Toast.makeText(MainActivity.this, "Successfully  Deleted All Records", Toast.LENGTH_SHORT).show();

    }
    private void OpenGallery() {
        Intent getImageIntent = new Intent(Intent.ACTION_GET_CONTENT);
        getImageIntent.setType("image/*");
        startActivityForResult(getImageIntent, RESULT_LOAD_IMAGE);

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK) {
            Uri fullPhotoUri = data.getData();
            imageView.setImageURI(fullPhotoUri);
        }
    }

    public ArrayList < String > st = new ArrayList < > ();
    public String str = "";


    public void Submit() {
        boolean[] checkBoxes = adpt.getCheckBoxState();


        int num = Inserted.getCount();
        for (int i = 0; i < st.size(); i++) {
            if (st.get(i) != null) {
                st.remove(i);
            }

        }
        for (int i = 0; i < num; i++) {
            str = adpt.getName(i) + " ";
            if (checkBoxes[i] == true) {
                st.add(str); // list.getAdapter().getItem(i).toString();
            }
        }





    }






}