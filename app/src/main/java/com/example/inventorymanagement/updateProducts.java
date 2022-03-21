package com.example.inventorymanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Field;

public class updateProducts extends AppCompatActivity {
    String NameOnly, Name, UOM, Category, Low;
    ImageButton btnCheck;
    Spinner spinCategory;
    EditText editName, editUOM, editLow;
    DBHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {  // start of oncreate
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_products);
        btnCheck = findViewById(R.id.btnCheck);
        spinCategory = findViewById(R.id.spinCategory);
        editName = findViewById(R.id.editName);
        editUOM = findViewById(R.id.editUOM);
        editLow = findViewById(R.id.editLow);
        db = new DBHelper(this);
        UOM = getIntent().getStringExtra("UOM");
        NameOnly = getIntent().getStringExtra("NameOnly");
        Low = getIntent().getStringExtra("low");



        editUOM.setText(UOM);
        editName.setText(NameOnly.substring(0, NameOnly.indexOf('(') - 1));
        editLow.setText(Low);

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

//        spinCategory.getBackground().setColorFilter(Color.parseColor("#242526"), PorterDuff.Mode.SRC_ATOP);


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

        // modals
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width * 1), (int)(height*.6));

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        params.x = 0;
        params.y = -20;

        getWindow().setAttributes(params);
        // end of modals




        // start of button check

        btnCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Category = spinCategory.getSelectedItem().toString();
                Name = editName.getText().toString();
                UOM = editUOM.getText().toString();
                Low = editLow.getText().toString();
                if(Name.equals("") || UOM.equals("") || Low.equals("")){
                    Toast.makeText(updateProducts.this, "Please fill-up all fields.", Toast.LENGTH_SHORT).show();
                }else{

                    boolean updateData= db.updateData(NameOnly, Name, UOM, Low, Category);
                    if(updateData==true){
                        Toast.makeText(updateProducts.this, "Product Updated", Toast.LENGTH_SHORT).show();
                        finish();
                        overridePendingTransition(0, 0);
                        Intent intent=new Intent(getApplicationContext(), Products.class);
                        startActivity(intent);
                        overridePendingTransition(0, 0);


                    }else{
                        Toast.makeText(updateProducts.this, NameOnly,Toast.LENGTH_SHORT).show();
                    }
                }
            }

        }); // end of button check

        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            public void uncaughtException(Thread paramThread, Throwable paramThrowable) {
                Log.e("Error"+Thread.currentThread().getStackTrace()[2],paramThrowable.getLocalizedMessage());
            }
        });


        } // end of oncreate


    }



