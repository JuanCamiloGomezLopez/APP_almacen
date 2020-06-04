package com.example.appcamilo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Main_modificar extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private TextView usuario1, mproducto,et_pro,et_des;
    private Button salir1,buscardatos;
    private EditText buscar,probar1;
    private int l=0;
    private  DatabaseReference mdatabase;
    private Spinner et_estado,et_ubicacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_modificar);

        mAuth = FirebaseAuth.getInstance();

        usuario1 = (TextView) findViewById(R.id.textView18);
        salir1 = (Button) findViewById(R.id.modificar2);
        buscardatos=(Button)findViewById(R.id.buscar1);
        et_estado=(Spinner)findViewById(R.id.spinner);
        et_ubicacion=(Spinner)findViewById(R.id.spinner2);


        //******************************
        String[] opciones = {"Elija un estado","En compras", "En Stock", "Sin stock", "No hay disponibilidad"};

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, opciones);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        et_estado.setAdapter(adapter);

        //********************************

        String[] opciones1 = {"Elija una ubicación","Almacen central", "Medellin", "Almacen 1", "Almacen 2"};

        final ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, opciones1);

        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        et_ubicacion.setAdapter(adapter1);

        // ******************************

        final String estado = et_estado.getSelectedItem().toString();
        String ubicacion = et_ubicacion.getSelectedItem().toString();



        // ********************************** poner usuario en la parte superior ***********
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String userEmail = user.getEmail();
            boolean emailverified = user.isEmailVerified();
            String uid = user.getUid();
            usuario1.setText(userEmail);
        }
         // ********************************** fin poner usuario en la parte superior **************

        // *************************************** boton salir ************************
        salir1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Main_Login.class));
                finish();
            }
        });
        // ************************************** fin boton salir ************************


        // **************************** buscar productos *******************************


        buscardatos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                et_pro=(TextView) findViewById(R.id.et_pro);
                et_des=(TextView) findViewById(R.id.et_des);
                buscar=(EditText)findViewById(R.id.buscarprod);

                mdatabase= FirebaseDatabase.getInstance().getReference();

                final int camilo = Integer.parseInt(buscar.getText().toString());

                mdatabase.child("almacen").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if (dataSnapshot.exists()){

                            for(DataSnapshot ds: dataSnapshot.getChildren()){

                                int mensaje= Integer.parseInt(ds.child("rm").getValue().toString());
                                String masaje = ds.child("producto").getValue().toString();
                                String masaje1 = ds.child("descripccion").getValue().toString();
                                String masaje2 = ds.child("estado").getValue().toString();

                                if (mensaje== camilo){
                                    et_pro.setText(masaje);
                                    et_des.setText(masaje1);
                                    et_estado.setAdapter(adapter);
                                    et_ubicacion.setAdapter(adapter1);
                                    l=1;
                                }
                            }
                            if (l==0){
                                Toast.makeText(getApplication(),"la rm no existe",Toast.LENGTH_LONG).show();
                                buscar.setText("");
                            } else {l=0;}
                        }


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });











            }
        });





    }
}