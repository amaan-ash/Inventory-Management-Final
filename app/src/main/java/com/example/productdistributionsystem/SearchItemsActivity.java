package com.example.productdistributionsystem;

import android.content.Intent;

import android.os.Bundle;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class SearchItemsActivity extends AppCompatActivity {
    public static EditText resultSearchView;
   private  RecyclerView recyclerView;
   private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
   private Button searchBtn;

    private MeraAdapter adapter;
    private ImageButton imageButtonSearch;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_items);


        recyclerView=findViewById(R.id.recyclerView);
        resultSearchView =findViewById(R.id.searchItems);
        searchBtn=findViewById(R.id.searchBtn);
        imageButtonSearch=findViewById(R.id.imageButtonSearch);


        firebaseAuth = FirebaseAuth.getInstance();
        final FirebaseUser users = firebaseAuth.getCurrentUser();
        String finalUser=users.getEmail();
        String resultEmail = finalUser.replace(".","");
        databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(resultEmail).child("Items");





        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                search();
            }
        });

        imageButtonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ScanCodeActivitySearch.class));
            }
        });







    }
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

    @Override
    protected void onStop() {
        super.onStop();
        try {
            adapter.stopListening();
        }
        catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }


    }
    public void search(){
        String searchText = resultSearchView.getText().toString();
        Query query = databaseReference.orderByChild("itemBarCode").startAt(searchText).endAt(searchText+"\uf8ff");
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
            Intent i=new Intent(this,SearchItemsActivity.class);
            startActivity(i);

        }
        return super.onOptionsItemSelected(item);
    }


}

