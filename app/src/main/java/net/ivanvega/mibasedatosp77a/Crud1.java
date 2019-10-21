package net.ivanvega.mibasedatosp77a;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Crud1 extends AppCompatActivity {
    DAOContactos dao;
    EditText usuario;
    EditText email;
    EditText telefono;
    EditText fecha;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crud1);
        usuario = findViewById(R.id.txtNombre);
        email = findViewById(R.id.txtEmail);
        telefono = findViewById(R.id.txtTelefono);
        fecha = findViewById(R.id.txtFecha);
        dao  = new DAOContactos(this);
    }
    //Boton Agregar
    public void Agregar(View view) {
        String user = usuario.getText().toString();
        String mail = email.getText().toString();
        String tel = telefono.getText().toString();
        String fec = fecha.getText().toString();
        try {
            dao.insert(new Contacto(0,user,mail,tel,new SimpleDateFormat("yyyy/MM/dd").parse(fec)));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Toast.makeText(this,"Agregado",Toast.LENGTH_SHORT).show();
        Intent agregacion= new Intent(this,MainActivity.class);
        startActivity(agregacion);
        finish();
    }
    //Boton Cancelar
    public void Cancelar(View view) {
        Intent cancel= new Intent(this,MainActivity.class);
        startActivity(cancel);
        finish();
    }
}
