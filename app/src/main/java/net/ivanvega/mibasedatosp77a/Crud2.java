package net.ivanvega.mibasedatosp77a;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Crud2 extends AppCompatActivity {
    Contacto cont;
    TextView usuario;
    TextView id;
    EditText email;
    EditText tel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cont=(Contacto) getIntent().getExtras().getSerializable("Info");
        setContentView(R.layout.activity_crud2);
        usuario=findViewById(R.id.lblId);
        id=findViewById(R.id.lblUsers);
        email=findViewById(R.id.txtCorreo);
        tel=findViewById(R.id.txtTelefono);
        id.setText(cont.getId()+"");
        usuario.setText(cont.getUsuario());
        email.setText(cont.getEmail());
        tel.setText(cont.getTel());
    }
    
    public void update(View view) {
        DAOContactos dao = new DAOContactos(this);
        if(dao.update(email.getText().toString(),tel.getText().toString(),usuario.getText().toString())){
            Toast.makeText(this,"Actualizado con exito",Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this,"No se pudo actualizar",Toast.LENGTH_SHORT).show();
        }
        Intent actualizado= new Intent(this,MainActivity.class);
        startActivity(actualizado);
        finish();
    }

    public void cancelar(View view) {
        Intent cancel= new Intent(this,MainActivity.class);
        startActivity(cancel);
        finish();
    }
}
