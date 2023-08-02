package com.example.productdistributionsystem;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class ViewInventoryActivity extends AppCompatActivity{

    //declaring the variables
    private FirebaseAuth firebaseAuth;
    RecyclerView recyclerView;
    DatabaseReference databaseReference;
    private TextView totalNumOfItems, totalSum;
    private int countTotalNoOfItem =0;
    MeraAdapter adapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_inventory);


        //getting views by id
        totalNumOfItems = findViewById(R.id.totalNumOfItems);
        totalSum = findViewById(R.id.totalSum);
        firebaseAuth = FirebaseAuth.getInstance();


        final FirebaseUser users = firebaseAuth.getCurrentUser();
        String finalUser=users.getEmail();
        String resultEmail = finalUser.replace(".","");
        databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(resultEmail).child("Items");
        recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        databaseReference.addValueEventListener(new ValueEventListener() {

            //if item is added or deleted it will update in below method
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    countTotalNoOfItem= (int) snapshot.getChildrenCount();
                    totalNumOfItems.setText(String.valueOf(countTotalNoOfItem));
                }else{
                    totalNumOfItems.setText("0");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        databaseReference.addValueEventListener(new ValueEventListener() {

            //to monitor the sum of the items the below method is used
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                float sum=0.0f;
                for(DataSnapshot ds : snapshot.getChildren()){
                    Map<Float,Object> map = (Map<Float, Object>) ds.getValue();
                    Object price = map.get("itemPrice");
                    float pValue = Float.parseFloat(String.valueOf(price));
                    sum += pValue;
                    totalSum.setText(String.valueOf(sum));

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




    }


    //to make the recycler view to start getting the data from the database
    @Override
    protected void onStart() {
        super.onStart();
        search();
        try {
            adapter.startListening();
        }
        catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }



    }



    //to make the recycler view to stop getting the data from the database
    @Override
    protected void onStop() {
        try {
            super.onStop();
        }
        catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    public void search(){
        Query query = databaseReference.orderByChild("itemBarCode");
        FirebaseRecyclerOptions<Items> options=
                new FirebaseRecyclerOptions.Builder<Items>()
                        .setQuery(query,Items.class)
                        .build();

        adapter=new MeraAdapter(options);
        recyclerView.setAdapter(adapter);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_menu3,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId=item.getItemId();

        if(itemId==R.id.refreshItems){
            finish();
            Intent i=new Intent(this,ViewInventoryActivity.class);
            startActivity(i);

        }
        return super.onOptionsItemSelected(item);
    }
}