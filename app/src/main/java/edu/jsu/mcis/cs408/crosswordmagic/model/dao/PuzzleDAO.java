package edu.jsu.mcis.cs408.crosswordmagic.model.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.HashMap;

import edu.jsu.mcis.cs408.crosswordmagic.model.Puzzle;
import edu.jsu.mcis.cs408.crosswordmagic.model.PuzzleListItem;
import edu.jsu.mcis.cs408.crosswordmagic.model.Word;
import edu.jsu.mcis.cs408.crosswordmagic.model.WordDirection;

public class PuzzleDAO {

    public static final String TAG = "PuzzleDAO";
    private final DAOFactory daoFactory;

    PuzzleDAO(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    public int create(HashMap<String, String> params) {

        /* use this method if there is NOT already a SQLiteDatabase open */

        SQLiteDatabase db = daoFactory.getWritableDatabase();
        int result = create(db, params);
        db.close();
        return result;

    }

    public int create(SQLiteDatabase db, HashMap<String, String> params) {

        /* use this method if there IS already a SQLiteDatabase open */

        String name = daoFactory.getProperty("sql_field_name");
        String description = daoFactory.getProperty("sql_field_description");
        String height = daoFactory.getProperty("sql_field_height");
        String width = daoFactory.getProperty("sql_field_width");

        ContentValues values = new ContentValues();
        values.put(name, params.get(name));
        values.put(description, params.get(description));
        values.put(height, Integer.parseInt(params.get(height)));
        values.put(width, Integer.parseInt(params.get(width)));

        int key = (int) db.insert(daoFactory.getProperty("sql_table_puzzles"), null, values);

        return key;

    }

    public Puzzle find(int id) {

        /* use this method if there is NOT already a SQLiteDatabase open */

        SQLiteDatabase db = daoFactory.getWritableDatabase();
        Puzzle result = find(db, id);
        db.close();
        return result;

    }

    public Puzzle find(SQLiteDatabase db, int id) {

        /* use this method if there is NOT already a SQLiteDatabase open */

        Puzzle puzzle = null;

        String query = daoFactory.getProperty("sql_get_puzzle");
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(id)});

        if (cursor.moveToFirst()) {

            cursor.moveToFirst();

            HashMap<String, String> params = new HashMap<>();

            params.put("_id", cursor.getString(0));
            params.put("name", cursor.getString(1));
            params.put("description", cursor.getString(2));
            params.put("height", cursor.getString(3));
            params.put("width", cursor.getString(4));

            /* get data for new puzzle */

            if (!params.isEmpty())
                puzzle = new Puzzle(params);


            /* get list of words (if any) to add to puzzle */

            query = daoFactory.getProperty("sql_get_words");
            cursor = db.rawQuery(query, new String[]{String.valueOf(id)});

            if (cursor.moveToFirst()) {

                cursor.moveToFirst();

                do {

                    params = new HashMap<>();

                    params.put("_id", cursor.getString(0));
                    params.put("puzzleid", String.valueOf(id));

                    params.put("row", cursor.getString(2));
                    params.put("column", cursor.getString(3));
                    params.put("box", cursor.getString(4));
                    params.put("direction", cursor.getString(5));
                    params.put("word", cursor.getString(6));
                    params.put("clue", cursor.getString(7));

                    /* get data for the next word in the puzzle */

                    if (!params.isEmpty())
                        puzzle.addWordToPuzzle(new Word(params));

                }
                while (cursor.moveToNext());

                cursor.close();

            }

            /* get list of already-guessed words (if any) from "guesses" table */

            query = daoFactory.getProperty("sql_get_guesses");
            cursor = db.rawQuery(query, new String[]{String.valueOf(id)});

            if (cursor.moveToFirst()) {

                cursor.moveToFirst();

                do {

                    Integer box = cursor.getInt(4);
                    WordDirection direction = WordDirection.values()[cursor.getInt(5)];

                    if (puzzle != null)
                        puzzle.addWordToGuessed(box + direction.toString());

                }
                while (cursor.moveToNext());

                cursor.close();

            }

        }

        return puzzle;

    }

    public PuzzleListItem[] list() {

        /* use this method if there is NOT already a SQLiteDatabase open */

        SQLiteDatabase db = daoFactory.getWritableDatabase();
        PuzzleListItem[] result = list(db);
        db.close();
        return result;

    }

    public PuzzleListItem[] list(SQLiteDatabase db) {

        ArrayList<PuzzleListItem> puzzles = new ArrayList<>();

        String query = daoFactory.getProperty("sql_get_puzzles");
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {

            Integer id = cursor.getInt(0);
            String name = cursor.getString(1);
            puzzles.add(new PuzzleListItem(id, name));

        }

        cursor.close();

        return puzzles.toArray(new PuzzleListItem[]{});

    }

}