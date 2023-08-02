package com.example.productdistributionsystem;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddProductActivity extends AppCompatActivity {
    //declaring the variables
    private TextInputEditText editProductName;
    private TextInputEditText editCategory;
    private TextInputEditText editPrice;
    private TextView itemBarCode;
    private FirebaseAuth firebaseAuth;
    public static TextView resultTextView;
    private Button scanButton;
    private Button addItemButton;
    DatabaseReference databaseReference;
    DatabaseReference databaseReferencecat;
    @SuppressLint("CutPasteId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        firebaseAuth = FirebaseAuth.getInstance();

        databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        databaseReferencecat = FirebaseDatabase.getInstance().getReference("Users");


        //finding views by Id
        resultTextView = findViewById(R.id.barcodeView);
        addItemButton = findViewById(R.id.addItemButton);
        scanButton = findViewById(R.id.scanButton);
        editProductName = findViewById(R.id.editProductName);
        editCategory = findViewById(R.id.editCategory);
        editPrice = findViewById(R.id.editPrice);
        itemBarCode = findViewById(R.id.barcodeView);





        scanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ScanCodeAddItem.class));
            }
        });

        addItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addItem();
            }
        });



    }


    // adding item to database
    public  void addItem(){
        String itemNameValue = editProductName.getText().toString();
        String itemCategoryValue = editCategory.getText().toString();
        String itemPriceValue = editPrice.getText().toString();
        String itemBarCodeValue = itemBarCode.getText().toString();
        final FirebaseUser users = firebaseAuth.getCurrentUser();
        String finalUser=users.getEmail();
        String resultEmail = finalUser.replace(".","");


        if(itemNameValue.isEmpty()){
            editProductName.setError("It's Empty");
            editProductName.requestFocus();
            return;
        }
        if(itemCategoryValue.isEmpty()){
            editCategory.setError("It's Empty");
            editCategory.requestFocus();
            return;
        }
        if(itemPriceValue.isEmpty()){
            editPrice.setError("It's Empty");
            editPrice.requestFocus();
            return;
        }
        if (itemBarCodeValue.isEmpty()) {
            itemBarCode.setError("It's Empty");
            itemBarCode.requestFocus();
            return;
        }


        if(!TextUtils.isEmpty(itemNameValue)&&!TextUtils.isEmpty(itemCategoryValue)&&!TextUtils.isEmpty(itemPriceValue)){

            Items items = new Items(itemNameValue,itemCategoryValue,itemPriceValue,itemBarCodeValue);
            databaseReference.child(resultEmail).child("Items").child(itemBarCodeValue).setValue(items);
            databaseReferencecat.child(resultEmail).child("ItemByCategory").child(itemCategoryValue).child(itemBarCodeValue).setValue(items);
            editProductName.setText("");
            itemBarCode.setText("");
            editPrice.setText("");
            editCategory.setText("");
            Toast.makeText(this,itemNameValue+" Added",Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this,"Please Fill all the fields",Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_menu2,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId=item.getItemId();

        if(itemId==R.id.refresh){
        startActivity(new Intent(this, AddProductActivity.class));
        finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
