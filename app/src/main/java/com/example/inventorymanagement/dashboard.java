package com.example.inventorymanagement;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class dashboard extends AppCompatActivity {
    DBHelper db;
    ImageView btnDashboard, btnDashboard1, btnLowStock, btnLowStock1, btnTransaction, btnTransaction1;
    TextView btnDashboard2, btnLowStock2, btnTransaction2, tvAllProductsValue1, tvAllProductsValue2;
    ListView lvProducts, lvProductsLow;


    private ArrayAdapter<String> adapter, adapterLow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        ActionBar actionBar = getSupportActionBar();
        TextView tv = new TextView(getApplicationContext());
        Typeface typeface = ResourcesCompat.getFont(this, R.font.lilita_one);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT, // Width of TextView
                RelativeLayout.LayoutParams.WRAP_CONTENT); // Height of TextView
        tv.setLayoutParams(lp);
        tv.setText("Inventory Management System"); // ActionBar title text
        tv.setTextSize(20);
        tv.setTextColor(Color.WHITE);
        tv.setTypeface(typeface, typeface.ITALIC);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setCustomView(tv);

        db = new DBHelper(this);
        btnDashboard = findViewById(R.id.btnDashboard);
        btnDashboard1 = findViewById(R.id.btnDashboard1);
        btnDashboard2 = findViewById(R.id.btnDashboard2);
        btnLowStock = findViewById(R.id.btnLowStock);
        btnLowStock1 = findViewById(R.id.btnLowStock1);
        btnLowStock2 = findViewById(R.id.btnLowStock2);
        btnTransaction = findViewById(R.id.btnTransaction);
        btnTransaction1 = findViewById(R.id.btnTransaction1);
        btnTransaction2 = findViewById(R.id.btnTransaction2);
        lvProducts = findViewById(R.id.lvProducts);
        lvProductsLow = findViewById(R.id.lvProductsLow);
        tvAllProductsValue1 = findViewById(R.id.tvAllProductsValue1);
        tvAllProductsValue2 = findViewById(R.id.tvAllProductsValue2);

        // listview
        lvProducts = findViewById(R.id.lvProducts);
        ArrayList<String> list = db.getProducts();
        adapter = new ArrayAdapter<>(this, R.layout.list_item,list);
        lvProducts.setAdapter(adapter);
        tvAllProductsValue1.setText(String.valueOf(lvProducts.getCount()));
        // end of listview

        // listview Low Products
        lvProductsLow = findViewById(R.id.lvProductsLow);
        ArrayList<String> listLow = db.getLowProducts();
        adapterLow = new ArrayAdapter<>(this, R.layout.list_item,listLow);
        lvProductsLow.setAdapter(adapterLow);
        tvAllProductsValue2.setText(String.valueOf(lvProductsLow.getCount()));
        // end of listview




        // Start of button dashboard
        btnDashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               gotoProducts();
            }
        });

        btnDashboard1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoProducts();
            }
        });

        btnDashboard2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoProducts();
            }
        });


        // Start of button low stock
        btnLowStock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoLowStock();
            }
        });

        btnLowStock1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoLowStock();
            }
        });

        btnLowStock2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoLowStock();
            }
        });


        // Start of button transaction
        btnTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoTransaction();
            }
        });

        btnTransaction1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoTransaction();
            }
        });

        btnTransaction2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoTransaction();
            }
        });


    }  // end of oncreate


    // go to products function
    public void gotoProducts () {
        finish();
        overridePendingTransition(0, 0);
        Intent intent=new Intent(getApplicationContext(), Products.class);
        startActivity(intent);
        overridePendingTransition(0, 0);
    }

    // go to products function
    public void gotoLowStock () {
        finish();
        overridePendingTransition(0, 0);
        Intent intent=new Intent(getApplicationContext(), lowProducts.class);
        startActivity(intent);
        overridePendingTransition(0, 0);
    }

    // go to products function
    public void gotoTransaction () {
        finish();
        overridePendingTransition(0, 0);
        Intent intent=new Intent(getApplicationContext(), transaction.class);
        startActivity(intent);
        overridePendingTransition(0, 0);
    }
}

