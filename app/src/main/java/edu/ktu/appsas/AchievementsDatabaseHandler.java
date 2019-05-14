package edu.ktu.appsas;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class AchievementsDatabaseHandler extends SQLiteOpenHelper {

    Context mContext;
    private static final  String DatabaseName = "Achievements.db";
    private static final int DatabaseVersion = 1;

    private static final String TABLE_NAME = "Results";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_TURNS = "turns";
    private static final String KEY_NUMBER = "number";
    private static final String KEY_DIFFICULTY = "difficulty";


    public AchievementsDatabaseHandler(Context context){
        super(context, DatabaseName, null,DatabaseVersion );
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "Create table " + TABLE_NAME + " ( " +
                KEY_ID + " INTEGER PRIMARY KEY, " +
                KEY_NAME + " TEXT, " +
                KEY_NUMBER + " INTEGER, " +
                KEY_TURNS + " INTEGER, " +
                KEY_DIFFICULTY + " TEXT " + " ) ";
        db.execSQL(query);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query = " DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(query);
        onCreate(db);
    }

    public long addEntry(AchievementsEntry entry)
    {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put(KEY_NAME, entry.getName());
        cv.put(KEY_NUMBER, entry.getNumber());
        cv.put(KEY_TURNS, entry.gerTurns());
        cv.put(KEY_DIFFICULTY, entry.getDifficulty());

        long id = db.insert(TABLE_NAME, null, cv);
        db.close();
        return id;
    }

    public AchievementsEntry getEntry (int id){
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = null;

        cursor = db.query(TABLE_NAME, new String[] { KEY_ID, KEY_NAME, KEY_NUMBER, KEY_TURNS, KEY_DIFFICULTY}, KEY_ID + "=?", new String[] { Integer.toString(id) }, null, null, null);

        AchievementsEntry entry = new AchievementsEntry();
        if(cursor != null)
        {
            if(cursor.moveToFirst())
            {
                entry.setID(cursor.getInt(0));
                entry.setName(cursor.getString(1));
                entry.setNumber(cursor.getInt(2));
                entry.setTurns(cursor.getInt(3));
                entry.setDifficulty(cursor.getString(4));

            }
        }

        cursor.close();
        db.close();

        return entry;
    }

    public ArrayList<AchievementsEntry> getAllEntries()
    {
        ArrayList<AchievementsEntry> entries = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();

        String query = "SELECT * FROM " + TABLE_NAME + " ORDER BY " + KEY_ID + " DESC";

        Cursor cursor = db.rawQuery(query, null);

        if(cursor != null)
        {
            if(cursor.moveToFirst())
            {
                do {
                    AchievementsEntry entry = new AchievementsEntry();
                    entry.setID(cursor.getInt(0));
                    entry.setName(cursor.getString(1));
                    entry.setNumber(cursor.getInt(2));
                    entry.setTurns(cursor.getInt(3));
                    entry.setDifficulty((cursor.getString(4)));

                    entries.add(entry);
                } while(cursor.moveToNext());
            }
        }

        cursor.close();
        db.close();

        return entries;
    }
    public void deleteEntry(long id)
    {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_NAME, KEY_ID + "=?", new String[]{Long.toString(id)});
        db.close();
    }



}
