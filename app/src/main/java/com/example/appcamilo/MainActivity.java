package com.example.appcamilo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private EditText usuario,password;
    private Button login,signup;
    private ProgressBar barraprogreso;
    private ProgressDialog progressDialog;
    private TextView olvidopassword;
    public String colaborador,codigo,area;
public int sumar;
    // declaramos el objeto firebaseAuth
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // se agrego un comentario para verificar github
        setContentView(R.layout.activity_main);

    // damos acceso a firfebase
        mAuth=FirebaseAuth.getInstance();

    // Referenciamos los View
        usuario=(EditText)findViewById(R.id.txt_email);
        password=(EditText)findViewById(R.id.btn_password);
        login=(Button)findViewById(R.id.btn_login);
        signup=(Button)findViewById(R.id.btn_signup);
        barraprogreso=new ProgressBar(this);
        progressDialog = new ProgressDialog(this);
        olvidopassword = (TextView) findViewById(R.id.textView2);

        // metodo onclicklistener para que ejecute cuando el usuario presiona la tecla login (CREAR CUENTA)

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = usuario.getText().toString();
                String pass = password.getText().toString();
                // damos las condiciones para el ingreso de datos
                if (TextUtils.isEmpty(email)) {
                    usuario.setError("Debes ingresar un correo electrónico");
                    return;
                }
                if (TextUtils.isEmpty(pass)) {
                    password.setError("Debes ingresar una contraseña");
                    return;
                }
                if (pass.length() != 6) {
                    password.setError("Contraseña debe tener 6 digitos");
                    return;
                }
         // comprobamos que el usuario que se vaya a registrar sea del consorcio y este en la base de datos


                switch (email){
                    case "camilo2@gmail.com":
                        colaborador= "julian pareja";
                        codigo= "cf1910125";
                        area = "Ingenieria";
                    break;
                    case "camilo@gmail.com":
                        colaborador= "Juan Camilo Gomez";
                        codigo= "cf1910132";
                        area = "Ingenieria";
                        break;
                    case "camilo3@gmail.com":
                        colaborador= "Jose Pelaez";
                        codigo= "cf1912525";
                        area = "Producción";
                    break;
                    case "camilo4@gmail.com":
                        colaborador= "Camilo gómez";
                        codigo= "cf1910566";
                        area = "Costos";
                    break;
                    case "camilo5@gmail.com":
                        colaborador= "Alexxander rojas";
                        codigo= "cf17888";
                        area = "Ingenieria";
                    break;
                    case "camilo6@gmail.com":
                        colaborador= "Alejandra jaramillo";
                        codigo= "cf188887";
                        area = "Almacen";
                    break;
                    case "camilo7@gmail.com":
                        colaborador= "Juan Camilo";
                        codigo= "cf1910132";
                        area = "Ingenieria";
                    break;
                    default:

                        AlertDialog.Builder alerta = new AlertDialog.Builder(MainActivity.this);
                        alerta.setMessage("Usted no esta autorizado para ingresar a esta aplicación").setCancelable(false)
                                .setNegativeButton("Aceptar", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                });
                            AlertDialog titulo= alerta.create();
                            titulo.setTitle("ERROR DE AUTENTICACIÓN");
                            titulo.show();

                    return;

                }


                   // REGISTRAMOS EL USUARIO EN FIREBASE

                mAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()){

                            Toast.makeText(MainActivity.this,"El registro fue exitoso",Toast.LENGTH_SHORT).show();

                            FirebaseUser user = mAuth.getCurrentUser();

                            Intent intent= new Intent(getApplicationContext(),Main_Login.class);
                            intent.putExtra("datos",colaborador);
                            intent.putExtra("datos1",codigo);
                            intent.putExtra("datos2",area);
                            startActivity(intent);



                        } else {

                            if (task.getException()instanceof FirebaseAuthUserCollisionException){

                                Toast.makeText(MainActivity.this,"El usuario ya se encuentra registrado",Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(MainActivity.this,"no se pudo registrar el usuario"+task.getException().getMessage(),Toast.LENGTH_LONG).show();
                            }

                        }
                        barraprogreso.setVisibility(View.GONE);
                        progressDialog.dismiss();
                    }
                });

            }
        });


// APARTIR DE AQUI SE TRABAJA EL INGRESO A LA APP YA QUE EL USUARIO SE ENCUENTRA REGISTRADO (INICIAR SESION

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = usuario.getText().toString();
                String pass = password.getText().toString();

                // damos las condiciones para el ingreso de datos
                if (TextUtils.isEmpty(email)){
                    usuario.setError("Debes ingresar un correo electrónico");
                    return;
                }
                if (TextUtils.isEmpty(pass)) {
                    password.setError("Debes ingresar una contraseña");
                    return;}
                if (pass.length() != 6) {
                    password.setError("Contraseña debe tener 6 digitos");

                    return;}



                // comprobamos que el usuario que se vaya a registrar sea del consorcio y este en la base de datos


                switch (email){
                    case "camilo2@gmail.com":
                        colaborador= "julian pareja";
                        codigo= "cf1910125";
                        area = "Ingenieria";
                        break;
                    case "camilo@gmail.com":
                        colaborador= "Juan Camilo Gomez";
                        codigo= "cf1910132";
                        area = "Ingenieria";
                        break;
                    case "camilo3@gmail.com":
                        colaborador= "Jose Pelaez";
                        codigo= "cf1912525";
                        area = "Producción";
                        break;
                    case "camilo4@gmail.com":
                        colaborador= "Camilo gómez";
                        codigo= "cf1910566";
                        area = "Costos";
                        break;
                    case "camilo5@gmail.com":
                        colaborador= "Alexxander rojas";
                        codigo= "cf17888";
                        area = "Ingenieria";
                        break;
                    case "camilo6@gmail.com":
                        colaborador= "Alejandra jaramillo";
                        codigo= "cf188887";
                        area = "Almacen";
                        break;
                    case "camilo7@gmail.com":
                        colaborador= "Juan Camilo";
                        codigo= "cf1910132";
                        area = "Ingenieria";
                        break;

                }

                barraprogreso.setVisibility(View.VISIBLE);

                progressDialog.setMessage("Ingresando");
                progressDialog.show();

                // INGRESAMOS EL USUARIO A LA APP

                mAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){

                            Toast.makeText(MainActivity.this,"El ingreo fue exitoso",Toast.LENGTH_SHORT).show();

                            Intent intent= new Intent(getApplicationContext(),Main_Login.class);
                            intent.putExtra("datos",colaborador);
                            intent.putExtra("datos1",codigo);
                            intent.putExtra("datos2",area);
                            startActivity(intent);

                            FirebaseUser user = mAuth.getCurrentUser();
                        }
                             else{
                            AlertDialog.Builder alerta = new AlertDialog.Builder(MainActivity.this);
                            alerta.setMessage("El usuario no se encuentra registrado").setCancelable(false)
                                    .setNegativeButton("Aceptar", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.cancel();
                                        }
                                    });
                            AlertDialog titulo= alerta.create();
                            titulo.setTitle("ERROR DE AUTENTICACIÓN");
                            titulo.show();


                        }

                        barraprogreso.setVisibility(View.GONE);
                        progressDialog.dismiss();
                    }
                });

            }
        });




        olvidopassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(),Main_Forgot.class));
            }
        });










    }


}
