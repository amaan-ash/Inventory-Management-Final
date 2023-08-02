package com.example.productdistributionsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DeleteProductActivity extends AppCompatActivity {

    public static TextView resultDeleteView;
    private FirebaseAuth auth;
    private Button buttonScanDelete;
    private Button deleteItemBtn;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_product);

        auth=FirebaseAuth.getInstance();
        databaseReference= FirebaseDatabase.getInstance().getReference("Users");
        resultDeleteView=findViewById(R.id.barcodeDelete);
        buttonScanDelete=findViewById(R.id.buttonScanDelete);
        deleteItemBtn=findViewById(R.id.deleteItemBtn);

        buttonScanDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(DeleteProductActivity.this,ScanCodeDeleteItem.class);
                startActivity(i);
            }

        });
        deleteItemBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteFromDatabase();
            }
        });
    }

    public void deleteFromDatabase(){
            String deleteBarCodeValue = resultDeleteView.getText().toString();
            final FirebaseUser user = auth.getCurrentUser();
            String finalUser=user.getEmail();
            String resultEmail = finalUser.replace(".","");

            if(!TextUtils.isEmpty(deleteBarCodeValue)){
                databaseReference.child(resultEmail).child("Items").child(deleteBarCodeValue).removeValue();
                Toast.makeText(this,"Item is Deleted",Toast.LENGTH_SHORT).show();
                resultDeleteView.setText("");
            }
            else{
                Toast.makeText(this,"Please scan Barcode",Toast.LENGTH_SHORT).show();
            }
    }

}