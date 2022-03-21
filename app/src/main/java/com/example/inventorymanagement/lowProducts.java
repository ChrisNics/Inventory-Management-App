package com.example.inventorymanagement;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class lowProducts extends AppCompatActivity {
    DBHelper db;
    ListView lvProducts;
    EditText editSearch;
    TextView tvTotal;
    private ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_low_products);
        db = new DBHelper(this);
        lvProducts = findViewById(R.id.lvProducts);
        editSearch = findViewById(R.id.editSearch);
        tvTotal= findViewById(R.id.tvTotal);


        // listview
        lvProducts = findViewById(R.id.lvProducts);
        ArrayList<String> list = db.getLowProducts();

        adapter = new ArrayAdapter<>(this, R.layout.list_item,list);
        lvProducts.setAdapter(adapter);
        tvTotal.setText(String.valueOf(lvProducts.getCount()));
        // end of listview

        // search
        editSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                (lowProducts.this).adapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        }); // end of search

        // Listview click event
        lvProducts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String val =(String) parent.getItemAtPosition(position);
                String productNameOnly = val.substring(0,val.indexOf(')') + 1);
                String productName = val.substring(0, val.indexOf(')') + 1);
                String productQuantity = val.substring(val.indexOf('x') + 1, val.length()).replace("❗","");
                String UOM = val.substring(val.indexOf('(') + 1,val.indexOf(')'));
                Intent intent=new Intent(getApplicationContext(), viewProductsLow.class);
                intent.putExtra("Name",productName );
                intent.putExtra("Quantity",productQuantity);
                intent.putExtra("NameOnly",productNameOnly);
                intent.putExtra("UOM",UOM);
//                Toast.makeText(lowProducts.this, UOM, Toast.LENGTH_SHORT).show();
                startActivity(intent);


            }
        }); // end of listview click event




    } // end of oncreate

    @Override
    public void onBackPressed() {
        // your code.

        finish();
        overridePendingTransition(0, 0);
        Intent intent=new Intent(getApplicationContext(), dashboard.class);
        startActivity(intent);
        overridePendingTransition(0, 0);
    }

    // for menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.low,menu);

        return true;
    }

    // end of menu


}