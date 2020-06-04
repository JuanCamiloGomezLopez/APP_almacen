package com.example.appcamilo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class Main_Forgot extends AppCompatActivity {

    private Button forgotpass;
    private  EditText resetemail;
    private FirebaseAuth mAuth;
    private ProgressDialog progressDialog;
    private ProgressBar barraprogreso;
    private final int SPLASH_TIME_OUT = 3000;

    // navegation bar
    public BottomNavigationView bottonnavigationview;
    public FrameLayout frameLayout;
    public homeFragment homeFragment;
    public backFragment backFragment;
    public chatFragment chatFragment;
    //* *******

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__forgot);

        resetemail=(EditText)findViewById(R.id.txt_email2);
        forgotpass= (Button) findViewById(R.id.btn_signup2);

        progressDialog = new ProgressDialog(this);
        barraprogreso=new ProgressBar(this);
        mAuth= FirebaseAuth.getInstance();

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
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        finish();
                        setFragment(backFragment);
                        return true;

                    case R.id.homeinf:
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
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


        //* **********************************************************************

        forgotpass.setOnClickListener(new View.OnClickListener() {
            @Override



            public void onClick(View v) {


                String email = resetemail.getText().toString();

                progressDialog.setMessage("Enviando correo electrónico");
                progressDialog.show();

                        mAuth.sendPasswordResetEmail(email).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {

                                Toast.makeText(Main_Forgot.this,"Se envio el link de restablecimiento a su correo",Toast.LENGTH_LONG).show();

                                new Handler().postDelayed(new Runnable() {

                                    public void run() {
                                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                                    };
                                }, SPLASH_TIME_OUT);



                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            public void onFailure(Exception e) {
                                Toast.makeText(Main_Forgot.this, "Error el link no se envio"+e.getMessage(), Toast.LENGTH_SHORT).show();


                            }
                        });
                barraprogreso.setVisibility(View.GONE);
                progressDialog.dismiss();
            }

        });





        // ***********************************************************************
    }

    public void setFragment(Fragment fragment){

        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.framelayout,fragment);
        fragmentTransaction.commit();
    }


}
