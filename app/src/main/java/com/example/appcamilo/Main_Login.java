package com.example.appcamilo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Main_Login extends AppCompatActivity {

    private ImageView rproducto,mproducto,mbuscar,registrar_rm;
    private FirebaseAuth mAuth;
    private TextView usuario,codigo,area;

    // navegation bar
   public BottomNavigationView bottonnavigationview;
   public FrameLayout frameLayout;
   public homeFragment homeFragment;
   public backFragment backFragment;
   public chatFragment chatFragment;

    //* *******

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__login);


        rproducto=(ImageView)findViewById(R.id.imageView2);
        usuario=(TextView)findViewById(R.id.textView9);
        codigo=(TextView)findViewById(R.id.textView5);
        mproducto=(ImageView)findViewById(R.id.imageView3);
        mbuscar=(ImageView)findViewById(R.id.imageView8);
        registrar_rm=(ImageView)findViewById(R.id.generar_rm);
        area=(TextView) findViewById(R.id.textView7);


        String recuperar_nombre=getIntent().getStringExtra("datos");
        usuario.setText(recuperar_nombre);


        String recuperar_codigo=getIntent().getStringExtra("datos1");
        codigo.setText(recuperar_codigo);


        String recuperar_area=getIntent().getStringExtra("datos2");
        area.setText(recuperar_area);

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

                        setFragment(backFragment);
                        return true;

                    case R.id.homeinf:

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

        // obtener el correo de usuario current
        mAuth=FirebaseAuth.getInstance();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null){

            String userEmail= user.getEmail();

            boolean emailverified = user.isEmailVerified();

            String uid = user.getUid();

            usuario.setText(userEmail);

        }



        rproducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Main_registrar.class));
            }
        });

        mproducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Main_modificar.class));
            }
        });


        mbuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Main_Buscar.class));
            }
        });

        registrar_rm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Main_generar_RM.class));
            }
        });

    }

    public void setFragment(Fragment fragment){

        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.framelayout,fragment);
        fragmentTransaction.commit();
    }


}


