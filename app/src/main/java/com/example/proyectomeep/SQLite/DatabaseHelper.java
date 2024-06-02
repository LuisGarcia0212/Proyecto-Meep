package com.example.proyectomeep.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "projects.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_PROJECTS = "projects";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_PINNED = "pinned";
    public static final String COLUMN_FAVORITE = "favorite";
    // Sentencia SQL para crear la tabla de proyectos
    private static final String CREATE_TABLE_PROJECTS = "CREATE TABLE " + TABLE_PROJECTS + "(" +
            COLUMN_ID + " INTEGER PRIMARY KEY," +
            COLUMN_NAME + " TEXT," +
            COLUMN_DESCRIPTION + " TEXT," +
            COLUMN_PINNED + " INTEGER DEFAULT 0," +
            COLUMN_FAVORITE + " INTEGER DEFAULT 0)";
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_PROJECTS + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_NAME + " TEXT," +
                COLUMN_DESCRIPTION + " TEXT," + // Asegúrate de incluir la columna "description" aquí
                COLUMN_PINNED + " INTEGER," +
                COLUMN_FAVORITE + " INTEGER)";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PROJECTS);
        onCreate(db);
        db.execSQL("ALTER TABLE " + TABLE_PROJECTS + " ADD COLUMN " + COLUMN_DESCRIPTION + " TEXT");
    }

    public void updateProject(int id, boolean pinned, boolean favorite) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_PINNED, pinned ? 1 : 0);
        values.put(COLUMN_FAVORITE, favorite ? 1 : 0);

        db.update(TABLE_PROJECTS, values, COLUMN_ID + "=?", new String[]{String.valueOf(id)});
        db.close();
    }

    public Cursor getProject(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_PROJECTS, new String[]{COLUMN_ID, COLUMN_NAME, COLUMN_DESCRIPTION, COLUMN_PINNED, COLUMN_FAVORITE},
                COLUMN_ID + "=?", new String[]{String.valueOf(id)}, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }
    public Cursor getAllProjects() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(TABLE_PROJECTS, new String[]{COLUMN_ID, COLUMN_NAME, COLUMN_DESCRIPTION, COLUMN_PINNED, COLUMN_FAVORITE}, // Incluir description
                null, null, null, null, COLUMN_PINNED + " DESC, " + COLUMN_NAME + " ASC");
    }
    public void addProject(String name, String description, boolean pinned, boolean favorite) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_DESCRIPTION, description); // Incluir la descripción
        values.put(COLUMN_PINNED, pinned ? 1 : 0);
        values.put(COLUMN_FAVORITE, favorite ? 1 : 0);
        db.insert(TABLE_PROJECTS, null, values);
        db.close();
    }

}
