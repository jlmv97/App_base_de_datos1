package net.ivanvega.mibasedatosp77a;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Crud3 extends AppCompatActivity {
    EditText elimir;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crud3);
        elimir=findViewById(R.id.txtEliminado);
    }

    public void eliminar(View view) {
        DAOContactos dao = new DAOContactos(this);
        if (dao.delete(elimir.getText().toString())){
            Toast.makeText(this,"Eliminado con exito",Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this,"No se puedo elimininar",Toast.LENGTH_SHORT).show();
        }
    }

    public void cancelar(View view) {
        Intent cancel= new Intent(this,MainActivity.class);
        startActivity(cancel);
        finish();
    }
}
