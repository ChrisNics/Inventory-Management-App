package com.example.inventorymanagement;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class viewProductsLow extends AppCompatActivity {

    TextView tvTransaction, tvTransaction1, tvTotalQuantity, tvTotalQuantity1, tvProducts, tvProducts1;
    SwitchCompat switchTransaction;
    ImageButton btnAdd;
    String Name, Quantity, NameOnly, QuantityXML, UOM;
    boolean transactionDecision = true;
    int mainQuantity = 0;
    EditText editQuantity;
    DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_products_low);
        tvTransaction = findViewById(R.id.tvTransaction);
        tvTransaction1 = findViewById(R.id.tvTransaction1);
        tvTotalQuantity = findViewById(R.id.tvQuantity);
        tvTotalQuantity1 = findViewById(R.id.tvTotalQuantity1);
        String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        tvProducts = findViewById(R.id.tvProducts);
        tvProducts1 = findViewById(R.id.tvProducts1);
        switchTransaction = findViewById(R.id.switchTransaction);
        btnAdd = findViewById(R.id.btnAdd);
        editQuantity = findViewById(R.id.editQuantity);
        db = new DBHelper(this);

        Name = getIntent().getStringExtra("Name");
        NameOnly = getIntent().getStringExtra("NameOnly");
        Quantity = getIntent().getStringExtra("Quantity");

//        Toast.makeText(this, Quantity, Toast.LENGTH_SHORT).show();
        UOM = getIntent().getStringExtra("UOM");



        tvProducts1.setText(Name);
        tvTotalQuantity1.setText(Quantity);

        // Calling the method for click effects
        addClickEffect(btnAdd);



        // modals
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width * 1), (int)(height*.5));

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        params.x = 0;
        params.y = -20;

        getWindow().setAttributes(params);
        // end of modals


        // When clicking the switch
        switchTransaction.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b){
                // When Close
                transactionDecision = false;
                tvTransaction1.setText("OUT");

                tvTransaction1.setTextColor(Color.parseColor("#CC0000"));
            }
            else  {
                //When In
                transactionDecision = true;
                tvTransaction1.setText("IN");
                tvTransaction1.setTextColor(Color.parseColor("#5EBA7D"));

            }
        });  // end of switch

        // start of button add
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                QuantityXML = editQuantity.getText().toString();
                boolean isValid = true;
                if(QuantityXML.equals("") || QuantityXML.equals("0")){
                    Toast.makeText(viewProductsLow.this, "Please put some value in fields!", Toast.LENGTH_SHORT).show();
                }else{

                    // switch decision
                    if (transactionDecision) {
                        mainQuantity = Integer.parseInt(Quantity) + Integer.parseInt(QuantityXML);
                    } else {
                        if (Integer.parseInt(Quantity) >= Integer.parseInt(QuantityXML)) {
                            mainQuantity = Integer.parseInt(Quantity) - Integer.parseInt(QuantityXML);
                        } else {
                            isValid = false;
                        }
                    } // end of switch decision

                    boolean updateProducts= db.addData(String.valueOf(mainQuantity), NameOnly);
                    if(updateProducts && isValid){
                        db.insertTransactions(NameOnly,UOM, QuantityXML,  tvTransaction1.getText().toString() ,currentDate);
                        Toast.makeText(viewProductsLow.this, "Quantity Updated!", Toast.LENGTH_SHORT).show();
                        finish();
                        overridePendingTransition(0, 0);
                        Intent intent=new Intent(getApplicationContext(), lowProducts.class);
                        startActivity(intent);
                        overridePendingTransition(0, 0);

                    }else{
                        Toast.makeText(viewProductsLow.this, NameOnly, Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }); // end of button add


    } // end of oncreate


    // Effects when clicking the button
    void addClickEffect(View view)
    {
        Drawable drawableNormal = view.getBackground();

        Drawable drawablePressed = view.getBackground().getConstantState().newDrawable();
        drawablePressed.mutate();
        drawablePressed.setColorFilter(Color.argb(100, 0, 0, 0), PorterDuff.Mode.SRC_ATOP);

        StateListDrawable listDrawable = new StateListDrawable();
        listDrawable.addState(new int[] {android.R.attr.state_pressed}, drawablePressed);
        listDrawable.addState(new int[] {}, drawableNormal);
        view.setBackground(listDrawable);
    }
}