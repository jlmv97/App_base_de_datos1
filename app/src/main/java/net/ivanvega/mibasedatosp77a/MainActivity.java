package net.ivanvega.mibasedatosp77a;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class MainActivity extends AppCompatActivity implements Serializable {
    ListView lv;
    DAOContactos daos;
    Button habilitar;
    EditText criterio;
    AlertDialog.Builder builder;
    Contacto c;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        criterio = findViewById(R.id.txtBuscar);
        DAOContactos dao = new DAOContactos(this);

        builder = new AlertDialog.Builder(this);
        try {
            dao.insert(new Contacto(0, "perronegro",
                    "perronegro@","445",new SimpleDateFormat("yyyy/MM/dd").parse("2019/03/15")));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        try {
            dao.insert(new Contacto(0, "perroblanco",
                    "perroblanco@","544",new SimpleDateFormat("yyyy/MM/dd").parse("2019/03/15")));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        /*for (Contacto c : dao.getAll()){
             Toast.makeText(this,
                     c.usuario,
                     Toast.LENGTH_SHORT).show();
         }*/
        dao.delete("perroblanco");
        dao.delete("perronegro");

        lv =findViewById(R.id.lv);
        registerForContextMenu(lv);
        SimpleCursorAdapter adp =
                new SimpleCursorAdapter(
                        this,
                        android.R.layout.simple_list_item_2,
                        dao.getAllCursor(),
                        new String[]{"usuario","email"},
                        new int[]{android.R.id.text1, android.R.id.text2
                        },
                        SimpleCursorAdapter.IGNORE_ITEM_VIEW_TYPE

                );
        lv.setAdapter(adp);

    }
        //Menu Contextual
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.context_menu,menu);
    }
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()){
            case R.id.delete:
                Toast.makeText(this,"Eliminado",Toast.LENGTH_SHORT).show();
                return true;
            case R.id.update:
                Toast.makeText(this,"Actualizado",Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    //Menu de opciones

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menus = getMenuInflater();
        menus.inflate(R.menu.option_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.agregar:
                Intent add= new Intent(this,Crud1.class);
                startActivityForResult(add,0);
                return true;
            case R.id.eliminar:
                Intent delete= new Intent(this,Crud3.class);
                startActivityForResult(delete,0);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void Search(View view) {
        String n = criterio.getText().toString()+"";
        if(n!=""){
        DAOContactos desc = new DAOContactos(this);
            // 1. Instantiate an <code><a href="/reference/android/app/AlertDialog.Builder.html">AlertDialog.Builder</a></code> with its constructor
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

// 2. Chain together various setter methods to set the dialog characteristics
            builder.setMessage(desc.buscar(n))
                    .setTitle("Contacto")
                    .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // User clicked OK button
                }
            })
                    .setNegativeButton("Actualizar", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // User clicked OK button
                            enviarUpdate(criterio.getText().toString());
                        }
                    });
// 3. Get the <code><a href="/reference/android/app/AlertDialog.html">AlertDialog</a></code> from <code><a href="/reference/android/app/AlertDialog.Builder.html#create()">create()</a></code>
            AlertDialog dialog = builder.create();
            dialog.show();
        }else{
            AlertDialog.Builder warnign = new AlertDialog.Builder(this);
            warnign.setMessage("Ingrese un usuario a buscar")
                    .setTitle("Pongase trucha")
                    .setPositiveButton("Ya me pondre trucha :'C",new DialogInterface.OnClickListener(){
                        public void onClick(DialogInterface dialog,int id){

                        }
            });
            AlertDialog dialog2 = warnign.create();
            dialog2.show();
        }
    }
    public void enviarUpdate(String contacto){
        DAOContactos obj = new DAOContactos(this);
        Intent actualizado = new Intent(this,Crud2.class);
        actualizado.putExtra("Info",obj.preparacion(contacto));
        startActivity(actualizado);
    }


}
