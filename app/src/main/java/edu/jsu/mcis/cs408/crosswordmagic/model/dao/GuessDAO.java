package edu.jsu.mcis.cs408.crosswordmagic.model.dao;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

public class GuessDAO {
    public static final String TAG = "GuessDAO";
    private final DAOFactory daoFactory;

    public GuessDAO(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    public int create(int puzzleid, int wordid) {

        /* use this method if there is NOT already a SQLiteDatabase open */

        SQLiteDatabase db = daoFactory.getWritableDatabase();
        int result = create(db, puzzleid, wordid);
        db.close();
        return result;

    }

    public int create(SQLiteDatabase db, int puzzleid, int wordid) {

        /* use this method if there IS already a SQLiteDatabase open */

        String puzzleid_field = daoFactory.getProperty("sql_field_puzzleid");
        String wordid_field = daoFactory.getProperty("sql_field_wordid");

        ContentValues values = new ContentValues();
        values.put(puzzleid_field, puzzleid);
        values.put(wordid_field, wordid);

        int key = (int) db.insert(daoFactory.getProperty("sql_table_guesses"), null, values);

        return key;

    }
}
