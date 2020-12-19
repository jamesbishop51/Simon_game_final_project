package edu.jbishop.simon_game_final_project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class High_Score_Page extends AppCompatActivity {


    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high__score__page);
        listView = findViewById(R.id.listview_highscores);


        DatabaseHandler db = new DatabaseHandler(this);

        db.emptyHighScores();     // empty table if required

        Log.i("Insert: ", "Inserting ..");
        //adding one high score for testing
        db.addHighScore(new HighScore("20 NOV 2020", "Bob", 18));
        db.addHighScore(new HighScore("22 NOV 2020", "Gemma", 22));
        db.addHighScore(new HighScore("30 NOV 2020", "Joe", 30));
        db.addHighScore(new HighScore("01 DEC 2020", "DarthV", 22));
        db.addHighScore(new HighScore("02 DEC 2020", "Gandalf the great", 132));

        // Reading all scores
        Log.i("Reading: ", "Reading all scores..");
        List<HighScore> highScores = db.getAllHighScores();


        for (HighScore hs : highScores) {
            String log =
                    "Id: " + hs.getScore_id() +
                            ", Date: " + hs.getGame_date() +
                            " , Player: " + hs.getPlayer_name() +
                            " , Score: " + hs.getScore();

            // Writing HighScore to log
            Log.i("Score: ", log);
        }

        Log.i("divider", "====================");

        HighScore singleScore = db.getHighScore(5);
        Log.i("High Score 5 is by ", singleScore.getPlayer_name() + " with a score of " +
                singleScore.getScore());

        Log.i("divider", "====================");

        // Calling SQL statement
        List<HighScore> top5HighScores = db.getTopFiveScores();

        for (HighScore hs : top5HighScores) {
            String log =
                    "Id: " + hs.getScore_id() +
                            ", Date: " + hs.getGame_date() +
                            " , Player: " + hs.getPlayer_name() +
                            " , Score: " + hs.getScore();

            // Writing HighScore to log
            Log.i("Score: ", log);
        }
        Log.i("divider", "====================");

        HighScore highScore = top5HighScores.get(top5HighScores.size() - 1);
        // highScore contains the 5th highest score
        Log.i("fifth Highest score: ", String.valueOf(highScore.getScore()) );

        // simple test to add a hi score
        int myCurrentScore = 40;
        // if 5th highest score < myCurrentScore, then insert new score
        if (highScore.getScore() < myCurrentScore) {
            db.addHighScore(new HighScore("08 DEC 2020", "Elrond", 40));
        }

        Log.i("divider", "====================");

        // Calling SQL statement
        top5HighScores = db.getTopFiveScores();
        List<String> scoresStr;
        scoresStr = new ArrayList<>();

        int j = 1;
        for (HighScore hs : top5HighScores) {

            String log =
                    "Id: " + hs.getScore_id() +
                            ", Date: " + hs.getGame_date() +
                            " , Player: " + hs.getPlayer_name() +
                            " , Score: " + hs.getScore();

            // store score in string array
            scoresStr.add(j++ + " : "  +
                    hs.getPlayer_name() + "\t" +
                    hs.getScore());
            // Writing HighScore to log
            Log.i("Score: ", log);
        }

        Log.i("divider", "====================");
        Log.i("divider", "Scores in list <>>");
        for (String ss : scoresStr) {
            Log.i("Score: ", ss);
        }

        ArrayAdapter<String> itemsAdapter =
                new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, scoresStr);
        listView.setAdapter(itemsAdapter);

    }


    }
