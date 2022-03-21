package com.example.inventorymanagement;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class addProducts extends AppCompatActivity {
    EditText editName, editUOM, editQuantity, editCategory, editLow;
    Spinner spinCategory;
    DBHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_products);
        editName = findViewById(R.id.editName);
        editUOM = findViewById(R.id.editUOM);
        editQuantity = findViewById(R.id.editQuantity);
        editLow = findViewById(R.id.editLow);
        db = new DBHelper(this);



//
        /////////////////////////////////////// Spinner
        //get the spinner from the xml.
       spinCategory = findViewById(R.id.spinCategory);
        //create a list of items for the spinner.
        String[] items = new String[]{"Canned Foods", "Packaged Foods", "Frozen Foods","Detergents", "Beverages"};
        //create an adapter to describe how the items are displayed, adapters are used in several places in android.
        //There are multiple variations of this, but this is the basic variant.
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        //set the spinners adapter to the previously created one.
        spinCategory.setAdapter(adapter);
        try {
            Field popup = Spinner.class.getDeclaredField("mPopup");
            popup.setAccessible(true);
            // Get private mPopup member variable and try cast to ListPopupWindow
            android.widget.ListPopupWindow popupWindow = (android.widget.ListPopupWindow) popup.get(spinCategory);
            // Set popupWindow height to 500px
            popupWindow.setHeight(500);
        }
        catch (NoClassDefFoundError | ClassCastException | NoSuchFieldException | IllegalAccessException e) {
            // silently fail...
        }
        ///////////////////////////////////////////// End of Spinner / Dropdown


    }  // end of oncreate

    @Override
    public void onBackPressed() {
        // your code.

        finish();
        overridePendingTransition(0, 0);
        Intent intent=new Intent(getApplicationContext(), Products.class);
        startActivity(intent);
        overridePendingTransition(0, 0);
    }

    // for menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.add,menu);

        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        int id=item.getItemId();

        if(id==R.id.itemAdd){
            String name, uom,quantity, category, low;
            name = editName.getText().toString();
            uom = editUOM.getText().toString();
            quantity = editQuantity.getText().toString();
            category = spinCategory.getSelectedItem().toString();
            low = editLow.getText().toString();

            if(name.equals("")|| quantity.equals("") || uom.equals("")) {
                Toast.makeText(addProducts.this, "Please fill-up all fields.", Toast.LENGTH_SHORT).show();
            }else{

                boolean insertData=db.insertData(name, uom, quantity, category, low);
                if(insertData==true){
                    String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
                    db.insertTransactions(name,uom, quantity,  "IN" ,currentDate);
                    Toast.makeText(addProducts.this, "Successfully Added.", Toast.LENGTH_SHORT).show();
                    finish();
                    overridePendingTransition(0, 0);
                    startActivity(getIntent());
                    overridePendingTransition(0, 0);
                }else{
                    Toast.makeText(addProducts.this, low, Toast.LENGTH_SHORT).show();
                }
            }

        }
        return super.onOptionsItemSelected(item);
    }
    // end of menu
}