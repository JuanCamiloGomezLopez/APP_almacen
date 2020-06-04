package com.example.appcamilo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.xml.sax.DTDHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main_generar_RM extends AppCompatActivity {

    private RecyclerView recycler;
    private  Button botonrecycler;
    List<DataAdapter> dataAdapters;
    private  TextView usuario;
    private FirebaseAuth mAuth;
    private EditText text_e_descripccion,text_e_material,text_e_cantidad;
    private String estado="en tramite";
    private String ubicacion="en tramite1";


    private DatabaseReference consorcioccc;

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
        setContentView(R.layout.activity_main_generar__r_m);

        usuario=(TextView)findViewById(R.id.text_v_usuario);

        // obtener el correo de usuario current
        mAuth=FirebaseAuth.getInstance();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null){
            String userEmail= user.getEmail();
            boolean emailverified = user.isEmailVerified();
            String uid = user.getUid();
            usuario.setText(userEmail);
        }


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
                        setFragment(backFragment);
                        return true;

                    case R.id.homeinf:
                        startActivity(new Intent(getApplicationContext(),Main_Login.class));
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

// creamos un array que se llame dataAdapyer donde le ingresamos la cantdad de materiales que queramos que aparezcan en la rm
        recycler = (RecyclerView) findViewById(R.id.myrecycler);
        botonrecycler = (Button) findViewById(R.id.botonrecycler);

        dataAdapters = new ArrayList<>();
        DataAdapter d;
        d = new DataAdapter("INGRESE NOMBRE DEL MATERIAL", "", "INGRESE DESCRIPCCIÓN","","CANTIDAD","");
        dataAdapters.add(d);

        d = new DataAdapter("INGRESE NOMBRE DEL MATERIAL", "", "INGRESE DESCRIPCCIÓN","","CANTIDAD","");
        dataAdapters.add(d);

        d = new DataAdapter("INGRESE NOMBRE DEL MATERIAL", "", "INGRESE DESCRIPCCIÓN","","CANTIDAD","");
        dataAdapters.add(d);

        d = new DataAdapter("INGRESE NOMBRE DEL MATERIAL", "", "INGRESE DESCRIPCCIÓN","","CANTIDAD","");
        dataAdapters.add(d);

        d = new DataAdapter("INGRESE NOMBRE DEL MATERIAL", "", "INGRESE DESCRIPCCIÓN","","CANTIDAD","");
        dataAdapters.add(d);

        d = new DataAdapter("INGRESE NOMBRE DEL MATERIAL", "", "INGRESE DESCRIPCCIÓN","","CANTIDAD","");
        dataAdapters.add(d);

        d = new DataAdapter("INGRESE NOMBRE DEL MATERIAL", "", "INGRESE DESCRIPCCIÓN","","CANTIDAD","");
        dataAdapters.add(d);

        d = new DataAdapter("INGRESE NOMBRE DEL MATERIAL", "", "INGRESE DESCRIPCCIÓN","","CANTIDAD","");
        dataAdapters.add(d);

        d = new DataAdapter("INGRESE NOMBRE DEL MATERIAL", "", "INGRESE DESCRIPCCIÓN","","CANTIDAD","");
        dataAdapters.add(d);




        // le damos el estilo de nuestro recycler view
        recycler.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recycler.setLayoutManager(linearLayoutManager);
        // hacemos la conexion entre el array ey el recyclerview
        Myadpater adapter = new Myadpater( this,dataAdapters);
        recycler.setAdapter(adapter);

        // aqui enviamos la rm al coordinador, pero primero debemos preguntar si esta seguro que la quiere enviar
        consorcioccc = FirebaseDatabase.getInstance().getReference();

        botonrecycler.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
              // comenzamos preguntadndo si esta seguro
                AlertDialog.Builder alerta = new AlertDialog.Builder(Main_generar_RM.this);
                alerta.setMessage("Esta seguro que desea enviar la RM?");
                alerta.setCancelable(false);
                alerta.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {


                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if (dataAdapters.size() > 0) {



                            for (int i = 0; i < dataAdapters.size(); i++) {

                                if (recycler.findViewHolderForAdapterPosition(i) instanceof Myadpater.Myholder) {
                                    Myadpater.Myholder holder = (Myadpater.Myholder) recycler.findViewHolderForLayoutPosition(i);


                                    String des = holder.descripccion.getText().toString();
                                    String mat = holder.material.getText().toString();
                                    String cant = holder.cantidad.getText().toString();



                                        Toast.makeText(Main_generar_RM.this, "material        " + holder.material.getText().toString()
                                                + "descripccion         " + holder.descripccion.getText().toString()
                                                + "cantidad          " + holder.cantidad.getText().toString(), Toast.LENGTH_SHORT).show();


                                }

                            }

                        }


                    }
                });
                alerta.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.cancel();
                    }
                });
                AlertDialog titulo = alerta.create();
                titulo.setTitle("CONFIRMACIÓN");
                titulo.show();

          // apartir de aqui recoreremos todos los datos ingresados en la rm y los enviamos a una base de datos temporal (por definir)



            }
        });


    }

        private void cargardatos(String mat,String des, String cant) {
        Map<String, Object> registrodematerial = new HashMap<>();
        registrodematerial.put("Materia", mat);
            registrodematerial.put("descripccio", des);
            registrodematerial.put("cant", cant);


        consorcioccc.child("almacen").push().setValue(registrodematerial);
    }


    public void setFragment(Fragment fragment){

        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.framelayout,fragment);
        fragmentTransaction.commit();
    }

    public class Myadpater extends RecyclerView.Adapter<Myadpater.Myholder>{
        Context context;

        List<DataAdapter> data;

        public Myadpater(Context context, List<DataAdapter> data) {
            this.context=context;
            this.data = data;
        }




        @NonNull
        @Override
        public Myholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {



            Context mcontext = parent.getContext();
            int Layoutidparalistitem = R.layout.recycler_rm;
            LayoutInflater inflater = LayoutInflater.from(mcontext);
            boolean attachtoparentrapido = false;

            View view = inflater.inflate(Layoutidparalistitem, parent, attachtoparentrapido);

            Myholder viewHolder = new Myholder(view);

            return viewHolder;


        }

        @Override
        public void onBindViewHolder(@NonNull Myholder holder, int position) {
        holder.itemView.setTag(position);

        holder.material.setText(data.get(position).getMaterial());
        holder.descripccion.setText(data.get(position).getDescripccion());
        holder.cantidad.setText(data.get(position).getCantidad());
        holder.text_v_material.setText(data.get(position).getText_v_material());
        holder.text_v_descripccion.setText(data.get(position).getText_v_descripccion());
        holder.text_v_cantidad.setText(data.get(position).getText_v_cantidad());

        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        public class Myholder extends RecyclerView.ViewHolder{

              EditText material,descripccion,cantidad;
              TextView text_v_material,text_v_descripccion,text_v_cantidad;



            public Myholder(@NonNull View itemView) {
                super(itemView);


                material=(EditText)itemView.findViewById(R.id.text_e_material);
                descripccion=(EditText)itemView.findViewById(R.id.text_e_descripccion);
                cantidad=(EditText)itemView.findViewById(R.id.text_e_cantidad);
                text_v_material=(TextView)itemView.findViewById(R.id.text_v_material);
                text_v_descripccion=(TextView)itemView.findViewById(R.id.text_v_descripccion);
                text_v_cantidad=(TextView)itemView.findViewById(R.id.text_v_cantidad);

            }



        }

    }




    // creamos el string


    public class DataAdapter{

        String text_v_material,material,text_v_descripccion,descripccion,text_v_cantidad,cantidad;

        public DataAdapter(String text_v_material, String material, String text_v_descripccion, String descripccion, String text_v_cantidad, String cantidad) {
            this.text_v_material = text_v_material;
            this.material = material;
            this.text_v_descripccion = text_v_descripccion;
            this.descripccion = descripccion;
            this.text_v_cantidad = text_v_cantidad;
            this.cantidad = cantidad;
        }

        public String getText_v_material() {
            return text_v_material;
        }

        public void setText_v_material(String text_v_material) {
            this.text_v_material = text_v_material;
        }

        public String getMaterial() {
            return material;
        }

        public void setMaterial(String material) {
            this.material = material;
        }

        public String getText_v_descripccion() {
            return text_v_descripccion;
        }

        public void setText_v_descripccion(String text_v_descripccion) {
            this.text_v_descripccion = text_v_descripccion;
        }

        public String getDescripccion() {
            return descripccion;
        }

        public void setDescripccion(String descripccion) {
            this.descripccion = descripccion;
        }

        public String getText_v_cantidad() {
            return text_v_cantidad;
        }

        public void setText_v_cantidad(String text_v_cantidad) {
            this.text_v_cantidad = text_v_cantidad;
        }

        public String getCantidad() {
            return cantidad;
        }

        public void setCantidad(String cantidad) {
            this.cantidad = cantidad;
        }
    }

}
