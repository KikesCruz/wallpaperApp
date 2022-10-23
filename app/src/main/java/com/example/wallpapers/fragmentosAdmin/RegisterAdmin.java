package com.example.wallpapers.fragmentosAdmin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.wallpapers.MainActivityAdmin;
import com.example.wallpapers.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class RegisterAdmin extends Fragment {

    TextView FechaRegistro;
    EditText name_admin, firtsname_admin, age_admin, email_admin, pass_admin;
    Button btn_registrar;

    FirebaseAuth auth;

    ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_register_admin, container, false);

        //Inicialitation de variables
        FechaRegistro = view.findViewById(R.id.RegisterDay);
        name_admin = view.findViewById(R.id.name_admin);
        firtsname_admin = view.findViewById(R.id.firtsname_admin);
        age_admin = view.findViewById(R.id.age_admin);
        email_admin = view.findViewById(R.id.email_admin);
        pass_admin = view.findViewById(R.id.pass_admin);

        btn_registrar = view.findViewById(R.id.btn_registrar);

        auth = FirebaseAuth.getInstance();//inicializa una instancia

        //captura la fecha de registro
        Date date = new Date();
        SimpleDateFormat fecha = new SimpleDateFormat("d 'de' MMMM 'del' yyyy");
        String Sfecha = fecha.format(date);
        FechaRegistro.setText(Sfecha);

        btn_registrar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String email = email_admin.getText().toString();
                String pass = pass_admin.getText().toString();

                //validacion del correo y password
                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    email_admin.setError("Correo es invalido");
                    email_admin.setFocusable(true);
                } else if (pass.length() < 6) {
                    pass_admin.setError("La contraseña debe de ser mayor a 6");
                    pass_admin.setFocusable(true);
                } else {
                    RegistrarAdmin(email, pass);
                }
            }
        });

        //mensajes de alerta
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Espere esta tregistrando");
        progressDialog.setCancelable(false);

        return view;
    }

    //metodo que registrar administradores
    private void RegistrarAdmin(String email, String pass) {
        auth.createUserWithEmailAndPassword(email, pass)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            progressDialog.dismiss();
                            FirebaseUser user = auth.getCurrentUser();
                            assert user != null;

                            String UID = user.getUid();
                            String mail = email_admin.getText().toString();
                            String pass = pass_admin.getText().toString();
                            String name_admi = name_admin.getText().toString();
                            String lastname_admi = firtsname_admin.getText().toString();
                            String age = age_admin.getText().toString();
                            int ageInt = Integer.parseInt(age);

                            HashMap<Object, Object> Admins = new HashMap<>();

                            Admins.put("UID", UID);
                            Admins.put("mail", mail);
                            Admins.put("pass", pass);
                            Admins.put("nombres", name_admi);
                            Admins.put("apellidos", lastname_admi);
                            Admins.put("edades", ageInt);

                            //Inicializar Firebase
                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            DatabaseReference reference = database.getReference("Base de datos de admins");
                            reference.child(UID).setValue(Admins);
                            //Redirige
                            startActivity(new Intent(getActivity(), MainActivityAdmin.class));
                            Toast.makeText(getActivity(), "Registro exitoso", Toast.LENGTH_SHORT).show();
                            getActivity().finish();
                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(getActivity(), "Ocurrio un error", Toast.LENGTH_SHORT).show();
                        }

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity(), ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}