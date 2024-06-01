package com.example.proyectomeep.SQLite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class MEEP extends SQLiteOpenHelper {

    private static final String nameDB = "ProjectMEEP.db";
    private static final int versionDB = 1;

    private static final String createTableUsuario = "CREATE TABLE IF NOT EXISTS Usuario (id INTEGER, usuario VARCHAR(255), clave VARCHAR(255));";
    private static final String dropTableUsuario = "DROP TABLE IF EXISTS Usuario;";

    private static final String createTableProyectos = "CREATE TABLE IF NOT EXISTS Proyecto (id INTEGER PRIMARY KEY AUTOINCREMENT, nombreP VARCHAR(255), fecha VARCHAR(50), area VARCHAR(255), descripcion VARCHAR(500));";
    private static final String dropTableProyectos = "DROP TABLE IF EXISTS Proyecto;";

    public MEEP(@Nullable Context context) {
        super(context, nameDB, null, versionDB);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Creación del schema de la DB
        db.execSQL(createTableUsuario);
        db.execSQL(createTableProyectos);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Actualización del schema de la DB
        db.execSQL(dropTableUsuario);
        db.execSQL(createTableUsuario);

        db.execSQL(dropTableProyectos);
        db.execSQL(createTableProyectos);
    }

    public boolean agregarUsuario(int id, String usuario, String clave) {
        SQLiteDatabase db = getWritableDatabase();
        if (db != null) {
            db.execSQL("INSERT INTO Usuario (id, usuario, clave) VALUES ("+id+", '" + usuario + "', '" + clave + "');");
            db.close();
            return true;
        }
        return false;
    }

    public boolean recordarSesion() {
        SQLiteDatabase db = getReadableDatabase();
        if (db != null) {
            Cursor cursor = db.rawQuery("SELECT id FROM Usuario;", null);
            if (cursor.moveToNext()) {
                cursor.close();
                return true;
            }
            cursor.close();
        }
        return false;
    }

    public String getValue(String campo) {
        SQLiteDatabase db = getReadableDatabase();
        String consulta = String.format("SELECT %s FROM Usuario", campo);
        if (db != null) {
            Cursor cursor = db.rawQuery(consulta, null);
            if (cursor.moveToNext()) {
                String value = cursor.getString(0);
                cursor.close();
                return value;
            }
            cursor.close();
        }
        return null;
    }

    public boolean eliminarUsuario(int id) {
        SQLiteDatabase db = getWritableDatabase();
        if (db != null) {
            db.execSQL("DELETE FROM Usuario WHERE id = " + id + ";");
            db.close();
            return true;
        }
        return false;
    }

    public void reiniciarSesion(){
        SQLiteDatabase db = getReadableDatabase();
        db.execSQL(dropTableUsuario);
        db.execSQL(createTableUsuario);
    }

    public boolean agregarProyecto(String nombre, String fecha, String area, String descripcion) {
        SQLiteDatabase db = getWritableDatabase();
        if (db != null) {
            db.execSQL("INSERT INTO Proyecto (nombreP, fecha, area, descripcion) VALUES ('" + nombre + "', '" + fecha + "', '" + area + "', '" + descripcion + "');");
            db.close();
            return true;
        }
        return false;
    }
}
