package com.example.productdistributionsystem;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class DashBoardActivity extends AppCompatActivity implements View.OnClickListener{

    //declaring variables
    private FirebaseAuth auth;
    private TextView firebaseNameView;

    private CardView addProduct;
    private CardView deleteProduct;
    private CardView viewProduct;
    private CardView viewInventory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);

        //finding views By Id to implement logic to the code
        addProduct =findViewById(R.id.addProduct);
        deleteProduct =findViewById(R.id.deleteProduct);
        viewProduct =findViewById(R.id.viewProduct);
        viewInventory=findViewById(R.id.viewInventory);
        firebaseNameView=findViewById(R.id.firebaseNameView);


        //to get the current user name on the screen
        auth = FirebaseAuth.getInstance();
        final FirebaseUser users = auth.getCurrentUser();
        String finalUser=users.getEmail();
        String result = finalUser.substring(0, finalUser.indexOf("@"));
        String resultEmail = result.replace(".","");
        firebaseNameView.setText(String.format("Welcome,\n \t \t \t %s", resultEmail));


        //to make the card-views clickable
        addProduct.setOnClickListener(this);
        deleteProduct.setOnClickListener(this);
        viewProduct.setOnClickListener(this);
        viewInventory.setOnClickListener(this);
    }

    //by clicking on  any CardView the below code will be executed
    @Override
    public void onClick(View view) {
        int itemId=view.getId();

        if(itemId==R.id.addProduct){
            Intent i=new Intent(DashBoardActivity.this, AddProductActivity.class);
            startActivity(i);
        }

        if(itemId==R.id.deleteProduct){
            Intent i=new Intent(DashBoardActivity.this, DeleteProductActivity.class);
            startActivity(i);
        }
        
        if(itemId==R.id.viewProduct){
            Intent i=new Intent(DashBoardActivity.this, SearchItemsActivity.class);
            startActivity(i);
        }

        if(itemId==R.id.viewInventory){
            Intent i=new Intent(DashBoardActivity.this,ViewInventoryActivity.class);
            startActivity(i);
        }
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_menu1,menu);
        return true;
    }

    //by clicking on any option from the options menu the below code will be executed
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
       int itemId=item.getItemId();
       if(itemId==R.id.logout){
           Logout();
       }
       if(itemId==R.id.addProduct){
           goToAdd();
       }
       if(itemId==R.id.deleteProduct){
           goToDelete();
       }
       if(itemId==R.id.viewInventory){
           goToInventory();
       }

       if(itemId==R.id.searchItems){
           goToItems();
       }
        return super.onOptionsItemSelected(item);
    }


    // for logging out the below method is executed
    private void Logout()
    {
                        auth.signOut();
                        finish();
                        startActivity(new Intent(DashBoardActivity.this,MainActivity.class));
                        Toast.makeText(DashBoardActivity.this,"Logout Successful", Toast.LENGTH_SHORT).show();



    }

    //for adding an item the below code will be executed
    private void goToAdd(){
        startActivity(new Intent(DashBoardActivity.this, AddProductActivity.class));

    }

    //for deleting an item the below code will be executed
    private void goToDelete(){
        startActivity(new Intent(DashBoardActivity.this,DeleteProductActivity.class));

    }

    //for checking the inventory the below code will be executed
    private void goToInventory(){
        startActivity(new Intent(DashBoardActivity.this,ViewInventoryActivity.class));

    }

    //for searching an item the below code will be executed
    private void goToItems(){
        startActivity(new Intent(DashBoardActivity.this, SearchItemsActivity.class));

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finishAffinity();
    }
}