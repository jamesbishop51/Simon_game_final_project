package edu.jbishop.simon_game_final_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class game_over_page extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    EditText playerName;
    TextView score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over_page);

        sharedPreferences = getSharedPreferences("score_ref", MODE_PRIVATE);
        editor = sharedPreferences.edit();

        int s = sharedPreferences.getInt("score",0);
        playerName = findViewById(R.id.playerName);

        score = findViewById(R.id.score);
        score.setText(String.valueOf(s));




        editor.putInt("score",0);
        editor.commit();
    }

    public void doReplay(View view) {

        Intent i = new Intent(game_over_page.this, MainActivity.class);
        startActivity(i);
    }

    public void doHighScore(View view) {


        editor.putString("playerName", playerName.getText().toString());
        int s = sharedPreferences.getInt("score", 0);
        editor.commit();

        Intent i = new Intent(game_over_page.this, High_Score_Page.class);
        i.putExtra("score",s);

        startActivity(i);
    }
}