package examenreyna03.android.examen03lastversion.Presentators;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

public class DBHelper extends SQLiteOpenHelper {

    //CONTRUCTOR DE LA CLASE PRINCIPAL SLITEOPENHELPER
    //************************************************
    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }


    public void queryData(String sql){
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(sql);
    }


    //METODO PARA REGISTRAR CONTACTOS
    //*******************************
    public void insertarcontacto(String nombre, String telefono, byte[] foto){

        SQLiteDatabase database = getWritableDatabase();
        String sql = "INSERT INTO CONTACTO VALUES(NULL, ?, ?, ?)";

        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();

        statement.bindString(1, nombre);
        statement.bindString(2, telefono);
        statement.bindBlob(3, foto);

        statement.executeInsert();
    }

    //METODO PARA EDITAR CONTACTOS
    //****************************

    public void editarcontacto(String nombre, String telefono, byte[] foto, int id){
        SQLiteDatabase database = getWritableDatabase();

        String sql = "UPDATE CONTACTO SET nombre=?, telefono=?, foto=? WHERE id=?";

        SQLiteStatement statement = database.compileStatement(sql);

        statement.bindString(1, nombre);
        statement.bindString(2, telefono);
        statement.bindBlob(3, foto);
        statement.bindDouble(4, (double)id);

        statement.execute();
        database.close();
    }



    //METODO PARA ELIMINAR CONTACTOS
    //******************************

    public void eliminarcontacto(int id){
        SQLiteDatabase database = getWritableDatabase();
        //query to delete record using id
        String sql = "DELETE FROM CONTACTO WHERE id=?";

        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();
        statement.bindDouble(1, (double)id);

        statement.execute();
        database.close();
    }


    public Cursor getData(String sql){
        SQLiteDatabase database = getReadableDatabase();
        return database.rawQuery(sql, null);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
