package edu.jbishop.simon_game_final_project;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;


public class DatabaseHandler extends SQLiteOpenHelper{
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "final_project_database";
    private static final String TABLE_HIGH_SCORES = "high_scores";
    private static final String KEY_SCORE_ID = "score_id";
    private static final String KEY_PLAYER_NAME = "player_name";
    private static final String KEY_GAME_DATE = "game_date";
    private static final String KEY_SCORE = "score";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        //3rd argument to be passed is CursorFactory instance
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //creates the table
        String CREATE_HIGHSCORES_TABLE = "CREATE TABLE " + TABLE_HIGH_SCORES + "(" +
                KEY_SCORE_ID + " INTEGER PRIMARY KEY," +
                KEY_GAME_DATE + " TEXT NOT NULL," +
                KEY_PLAYER_NAME + " TEXT NOT NULL," +
                KEY_SCORE + " INTEGER NOT NULL" +
                ")";
        db.execSQL(CREATE_HIGHSCORES_TABLE);

    }
    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HIGH_SCORES);

        // Create tables again
        onCreate(db);
    }

    /*
     * CRUD Helper methods
     */
    public void emptyHighScores() {
        // Drop older table if existed
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HIGH_SCORES);

        // Create tables again
        onCreate(db);
    }

    // code to add the new highScore
    void addHighScore(HighScore highScore) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_GAME_DATE, highScore.getGame_date());
        values.put(KEY_PLAYER_NAME, highScore.getPlayer_name());
        values.put(KEY_SCORE, highScore.getScore());

        // Inserting Row
        db.insert(TABLE_HIGH_SCORES, null, values);
        //2nd argument is String containing nullColumnHack
        db.close(); // Closing database connection
    }

    // code to get the single highScore
    HighScore getHighScore(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_HIGH_SCORES, new String[]{
                        KEY_SCORE_ID,
                        KEY_GAME_DATE,
                        KEY_PLAYER_NAME,
                        KEY_SCORE},
                KEY_SCORE_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        HighScore highScore = new HighScore(Integer.parseInt(
                cursor.getString(0)),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getInt(3));
        // return high score
        return highScore;
    }

    // code to get all highScores in a list view
    public List<HighScore> getAllHighScores() {
        List<HighScore> highScoreList = new ArrayList<HighScore>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_HIGH_SCORES;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                HighScore highScore = new HighScore();
                highScore.setScore_id(Integer.parseInt(cursor.getString(0)));
                highScore.setGame_date(cursor.getString(1));
                highScore.setPlayer_name(cursor.getString(2));
                highScore.setScore(cursor.getInt(3));
                // Adding hi score to list
                highScoreList.add(highScore);
            } while (cursor.moveToNext());
        }

        // return highScore list
        return highScoreList;
    }

    // code to update the single highScore
    public int updateHighScore(HighScore highScore) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_PLAYER_NAME, highScore.getPlayer_name());
        values.put(KEY_GAME_DATE, highScore.getGame_date());
        values.put(KEY_SCORE, highScore.getScore());

        // updating row
        return db.update(TABLE_HIGH_SCORES, values, KEY_SCORE_ID + " = ?",
                new String[]{String.valueOf(highScore.getScore_id())});
    }

    // Deleting single highScore
    public void deleteHighScore(HighScore highScore) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_HIGH_SCORES, KEY_SCORE_ID + " = ?",
                new String[]{String.valueOf(highScore.getScore_id())});
        db.close();
    }

    // Getting top 5 scores
    public List<HighScore> getTopFiveScores() {
        List<HighScore> highScoreList = new ArrayList<HighScore>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_HIGH_SCORES +
                " ORDER BY SCORE DESC " +
                " LIMIT 5";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                HighScore highScore = new HighScore();
                highScore.setScore_id(Integer.parseInt(cursor.getString(0)));
                highScore.setGame_date(cursor.getString(1));
                highScore.setPlayer_name(cursor.getString(2));
                highScore.setScore(cursor.getInt(3));
                // Adding hi score to list
                highScoreList.add(highScore);
            } while (cursor.moveToNext());
        }

        // return hi score list
        return highScoreList;
    }
}




