package com.example.appcamilo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class Main_registrar extends AppCompatActivity {

    private EditText et_producto,et_descripccion,et_rm;
    private Spinner et_estado,et_ubicacion;
    private Button salir,registrar;
    private DatabaseReference consorcioccc;
    private FirebaseAuth mAuth;
    private TextView usuario2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_registrar);

        salir= (Button)findViewById(R.id.regresar);
        registrar = (Button)findViewById(R.id.registrar);
        et_producto=(EditText)findViewById(R.id.ed_producto);
        et_descripccion=(EditText)findViewById(R.id.ed_descripccion);
        et_rm=(EditText)findViewById(R.id.ed_rm);
        et_estado=(Spinner)findViewById(R.id.spinner3);
        et_ubicacion=(Spinner)findViewById(R.id.spinner4);
        usuario2=(TextView)findViewById(R.id.textView11);

        mAuth=FirebaseAuth.getInstance();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null){
            String userEmail= user.getEmail();
            boolean emailverified = user.isEmailVerified();
            String uid = user.getUid();
            usuario2.setText(userEmail);
        }


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

        consorcioccc = FirebaseDatabase.getInstance().getReference();

        registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String producto = et_producto.getText().toString();
                String descripccion = et_descripccion.getText().toString();
                String estado = et_estado.getSelectedItem().toString();
                String ubicacion = et_ubicacion.getSelectedItem().toString();
                int rm = Integer.parseInt(et_rm.getText().toString());


                if(!producto.equals("") && !descripccion.equals("") && !estado.equals("Elija un estado") && !ubicacion.equals("Elija una ubicación")) {

                    cargardatos(producto, descripccion, estado, ubicacion, rm);

                    Toast.makeText(getApplication(), "El producto se registro correctamente", Toast.LENGTH_LONG).show();

                    et_producto.setText("");
                    et_descripccion.setText("");
                    et_rm.setText("");
                    et_estado.setAdapter(adapter);
                    et_ubicacion.setAdapter(adapter1);
                } else {

                    Toast.makeText(getApplication(), "Debe llenar todos los datos", Toast.LENGTH_LONG).show();
                }
            }
        });

        // ******************************************************

        salir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                startActivity(new Intent(getApplicationContext(),Main_Login.class));
                finish();
            }
        });

// ****************************************************


    }

    private void cargardatos(String producto, String descripccion, String estado, String ubicacion, int rm) {
        Map<String, Object> registrodematerial = new HashMap<>();
        registrodematerial.put("Material", producto);
        registrodematerial.put("Descripccion", descripccion);
        registrodematerial.put("RM", rm);
        registrodematerial.put("Estado", estado);
        registrodematerial.put("Ubicacion", ubicacion);

        consorcioccc.child("almacen").push().setValue(registrodematerial);
    }

}


