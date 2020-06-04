package com.example.appcamilo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.Adapter;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static androidx.recyclerview.widget.RecyclerView.*;

public class Main_Buscar extends AppCompatActivity {

    private  DatabaseReference mdatabase;
    RecyclerView recyclerview;
    ArrayList<blog> list;
    myadapter adapter;
    SearchView buscartool;

    // navegation bar
    public BottomNavigationView bottonnavigationview;
    public FrameLayout frameLayout;
    public homeFragment homeFragment;
    public backFragment backFragment;
    public chatFragment chatFragment;
    //* *******

    @Override
    // cmienza oncreate
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__buscar);


        // navegation
        backFragment=new backFragment();
        homeFragment=new homeFragment();
        chatFragment=new chatFragment();

        bottonnavigationview = findViewById(R.id.bottomNavigation);
        frameLayout= findViewById(R.id.framelayout);

        setFragment(homeFragment);
        setFragment(backFragment);
        setFragment(chatFragment);

        bottonnavigationview.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch ( (menuItem.getItemId())){

                    case R.id.atrasinf:
                        startActivity(new Intent(getApplicationContext(),Main_Login.class));
                        finish();
                        setFragment(backFragment);
                        return true;

                    case R.id.homeinf:
                        startActivity(new Intent(getApplicationContext(),Main_Login.class));
                        finish();
                        setFragment(homeFragment);
                        return true;

                    case R.id.chatinf:
                        FirebaseAuth.getInstance().signOut();
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        finish();
                        setFragment(chatFragment);
                        return true;
                }

                return false;
            }
        });
// fin navegation /************************


        recyclerview=(RecyclerView)findViewById(R.id.Recycler_buscar);
        recyclerview.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerview.setLayoutManager(linearLayoutManager);
        buscartool = (SearchView) findViewById(R.id.buscartool);
        list = new ArrayList<blog>();

// ***************** se entra en la base de datos

        mdatabase = FirebaseDatabase.getInstance().getReference();

        mdatabase.child("almacen").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {

                    for (DataSnapshot ds : dataSnapshot.getChildren()) {

                        blog p = ds.getValue(blog.class);
                        list.add(p);

                        adapter = new myadapter(Main_Buscar.this, list);
                        recyclerview.setAdapter(adapter);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });



    }  // termina oncreate

    // empieza oncreateoptionmenu (crear el seach view)
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.searchtool,menu);
        MenuItem item=menu.findItem(R.id.buscartool);
        SearchView searchView=(SearchView)item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {

                adapter.getFilter().filter(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
    // termina oncreateoptionsmenu

    public void setFragment(Fragment fragment){

        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.framelayout,fragment);
        fragmentTransaction.commit();
    }


} // termina clase principal
