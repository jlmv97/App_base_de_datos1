package net.ivanvega.mibasedatosp77a;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class DAOContactos {
    SQLiteDatabase _sqLiteDatabase;
    Context ctx;

    public DAOContactos(Context ctx) {
        this.ctx = ctx;
        _sqLiteDatabase =
                new MiDB(ctx).getWritableDatabase();
    }

    public long insert(Contacto contacto){
        ContentValues contentValues
                = new ContentValues();

        contentValues.put(MiDB.COLUMNS_NAME_CONTACTO[1],
                contacto.getUsuario());
        contentValues.put(MiDB.COLUMNS_NAME_CONTACTO[2],
                contacto.getEmail());
        contentValues.put(MiDB.COLUMNS_NAME_CONTACTO[3],
                contacto.getTel());
        contentValues.put(MiDB.COLUMNS_NAME_CONTACTO[4],
                contacto.getFecNac().toString());

        return  _sqLiteDatabase.insert(MiDB.TABLE_NAME_CONTACTOS,
                null, contentValues);

    }

    public List<Contacto> getAll (){
        List<Contacto> lst=null;

        Cursor c = _sqLiteDatabase.query(MiDB.TABLE_NAME_CONTACTOS,
                MiDB.COLUMNS_NAME_CONTACTO,
                null,
                null,
                null,
                null,
                null,
                null);

           if (c.moveToFirst() ){
               lst = new ArrayList<Contacto>();
               do {
                   Contacto contacto =
                           null;
                   try {
                       contacto = new Contacto(c.getInt(0), c.getString(1),
                               c.getString(2), c.getString(3),
                               new SimpleDateFormat("yyyy/MM/dd").parse(c.getString(4)));
                   } catch (ParseException e) {
                       e.printStackTrace();
                   }

                   lst.add(contacto);

               }while(c.moveToNext());
           }
           return  lst;

    }

    public Cursor getAllCursor (){


        Cursor c = _sqLiteDatabase.query(MiDB.TABLE_NAME_CONTACTOS,
                MiDB.COLUMNS_NAME_CONTACTO,
                null,
                null,
                null,
                null,
                null,
                null);


        return  c;

    }
    public Cursor getAllByUsuario(String criterio){

        Cursor c = _sqLiteDatabase.query(
                MiDB.TABLE_NAME_CONTACTOS,
                MiDB.COLUMNS_NAME_CONTACTO,
                "usuario like %?%",
                new String[]{criterio},
                null,
                null,null

        );

        return c;
    }

    public boolean delete(String criterio){

        return _sqLiteDatabase.delete(
                MiDB.TABLE_NAME_CONTACTOS,
                "usuario=?",
                new String[]{criterio})>0;
    }

    public String buscar(String criterio){
        String valores="";
        Cursor query =_sqLiteDatabase.query(MiDB.TABLE_NAME_CONTACTOS,MiDB.COLUMNS_NAME_CONTACTO, "usuario=?",new String[]{criterio+""}, null, null,null);
        while(query.moveToNext()){
            valores = "ID: "+query.getInt(0)+"\n Usuario: "+query.getString(1)+
                      "\n Email: "+query.getString(2)+"\n Telefono: "+query.getString(3)+
                      "\n Fecha de nacimiento: "+query.getString(4);
        }
        return valores;
    }

    public Contacto preparacion(String contacto){
        Cursor query =_sqLiteDatabase.query(MiDB.TABLE_NAME_CONTACTOS,MiDB.COLUMNS_NAME_CONTACTO, "usuario=?",new String[]{contacto+""}, null, null,null);
        Contacto nice=null;
        while (query.moveToNext()){
        nice = new Contacto(query.getInt(0),query.getString(1),query.getString(2),query.getString(3),
                null);
    }
        return nice;
    }

    public boolean update(String correo,String tel,String user){
        ContentValues contentValues = new ContentValues();
        contentValues.put("email",correo);
        contentValues.put("tel",tel);
       int codos = _sqLiteDatabase.update(MiDB.TABLE_NAME_CONTACTOS,contentValues,"usuario=?",new String[]{user});

       if (codos==1){
           return true;
       }
       else {
           return false;
       }
    }


}
