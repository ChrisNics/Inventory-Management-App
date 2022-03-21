package com.example.inventorymanagement;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class transaction extends AppCompatActivity {
    DBHelper db;
    ListView lvProducts;
    EditText editSearch;
    TextView tvTotal, tvTotalIN, tvTotalIN1, tvTotalOUT, tvTotalOUT1;
    TextView tvTransactionItem;
    private ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);
        db = new DBHelper(this);
        lvProducts = findViewById(R.id.lvProducts);
        editSearch = findViewById(R.id.editSearch);
        tvTotal= findViewById(R.id.tvTotal);
        tvTotalIN = findViewById(R.id.tvTotalIN);
        tvTotalIN1 = findViewById(R.id.tvTotalIN1);
        tvTotalOUT = findViewById(R.id.tvTotalOUT);
        tvTotalOUT1 = findViewById(R.id.tvTotalOUT1);




        // listview
        lvProducts = findViewById(R.id.lvProducts);
        ArrayList<String> list = db.getTransactions();
        adapter = new ArrayAdapter<>(this, R.layout.list_item, list);
        lvProducts.setAdapter(adapter);
        tvTotal.setText(String.valueOf(lvProducts.getCount()));
        // end of listview
        tvTotalIN1.setText(String.valueOf(db.displayTotalIn()));
        tvTotalOUT1.setText(String.valueOf(db.displayTotalOut()));



//        Toast.makeText(this, String.valueOf(db.displayTotalOut()) + String.valueOf(db.displayTotalIn()) , Toast.LENGTH_SHORT).show();
        // search
        editSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                (transaction.this).adapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        }); // end of search

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

        getMenuInflater().inflate(R.menu.transactions,menu);

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();


        if(id==R.id.itemClear){
            db.clearData("transactions");
            Toast.makeText(this, "Transactions Cleared!", Toast.LENGTH_SHORT).show();
            reload();
        }else{
            Toast.makeText(this, "Transactions Failed!", Toast.LENGTH_SHORT).show();
        }

        return super.onOptionsItemSelected(item);
    }
    // end of menu

    // Restart Activity
    public void reload() {
        Intent intent = getIntent();
        overridePendingTransition(0, 0);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        finish();
        overridePendingTransition(0, 0);
        startActivity(intent);
    }
}

