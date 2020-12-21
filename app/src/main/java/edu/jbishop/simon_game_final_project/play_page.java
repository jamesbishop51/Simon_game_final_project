package edu.jbishop.simon_game_final_project;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class play_page extends AppCompatActivity {
    private final int BLUE = 1;
    private final int RED = 2;
    private final int YELLOW = 3;
    private final int GREEN = 4;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;


    private SensorManager mSensorManager;
    private Sensor mSensor;

    ImageButton btnRed,btnBlue,btnYellow,btnGreen;
    int clicked=0;

    ArrayList<Integer> clickedSequence;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_page);
        btnBlue = findViewById(R.id.btnBlue);
        btnRed = findViewById(R.id.btnRed);
        btnGreen = findViewById(R.id.btnGreen);
        btnYellow = findViewById(R.id.btnYellow);
        preferences=PreferenceManager.getDefaultSharedPreferences(play_page.this);
        editor=preferences.edit();

        clickedSequence=new ArrayList<>();
        Bundle extras = getIntent().getExtras();
        ArrayList<Integer> gameSequence = extras.getIntegerArrayList("numbers");
        for (int value : gameSequence) {
            Log.e("array", String.valueOf(value));
        }
        //this is the methods for clicking the buttons
        btnYellow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickedSequence.add(YELLOW);
                clicked++;
                if (clicked>=gameSequence.size()){
                    if (clickedSequence.equals(gameSequence)){

                        Intent intent=new Intent();
                        intent.putExtra("Result","S");
                        intent.putExtra("seq",gameSequence.size()+2);
                        int s=preferences.getInt("score",0);
                        s+=gameSequence.size();
                        intent.putExtra("score",s);

                        setResult(Activity.RESULT_OK,intent);
                    }
                    else {

                        Intent intent = new Intent(play_page.this, game_over_page.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        int s=preferences.getInt("score",0);
                        intent.putExtra("score",s);

                        setResult(Activity.RESULT_OK,intent);
                    }
                    finish();

                    clicked=0;
                    clickedSequence.clear();
                }
            }
        });
        btnGreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickedSequence.add(GREEN);
                clicked++;
                if (clicked>=gameSequence.size()){
                    if (clickedSequence.equals(gameSequence)){

                        Intent intent=new Intent();
                        intent.putExtra("Result","S");
                        intent.putExtra("seq",gameSequence.size()+2);

                        int s=preferences.getInt("score",0);
                        s+=gameSequence.size();
                        intent.putExtra("score",s);

                        setResult(Activity.RESULT_OK,intent);
                    }
                    else {

                        Intent intent = new Intent(play_page.this, High_Score_Page.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        int s=preferences.getInt("score",0);
                        intent.putExtra("score",s);

                        setResult(Activity.RESULT_OK,intent);
                    }
                    finish();
                    clicked=0;
                    clickedSequence.clear();
                }
            }
        });
        btnBlue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickedSequence.add(BLUE);
                clicked++;
                if (clicked>=gameSequence.size()){
                    if (clickedSequence.equals(gameSequence)){

                        Intent intent=new Intent();
                        intent.putExtra("Result","S");
                        intent.putExtra("seq",gameSequence.size()+2);

                        int s=preferences.getInt("score",0);
                        s+=gameSequence.size();
                        intent.putExtra("score",s);

                        setResult(Activity.RESULT_OK,intent);
                    }
                    else {

                        Intent intent = new Intent(play_page.this, High_Score_Page.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        int s=preferences.getInt("score",0);
                        intent.putExtra("score",s);

                        setResult(Activity.RESULT_OK,intent);
                    }
                    finish();
                    clicked=0;
                    clickedSequence.clear();
                }
            }
        });
        btnRed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickedSequence.add(RED);
                clicked++;
                if (clicked>=gameSequence.size()){
                    if (clickedSequence.equals(gameSequence)){

                        Intent intent=new Intent();
                        intent.putExtra("Result","S");
                        intent.putExtra("seq",gameSequence.size()+2);

                        int s=preferences.getInt("score",0);
                        s+=gameSequence.size();
                        intent.putExtra("score",s);

                        setResult(Activity.RESULT_OK,intent);
                    }
                    else {

                        Intent intent = new Intent(play_page.this, High_Score_Page.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        int s=preferences.getInt("score",0);
                        intent.putExtra("score",s);
                        setResult(Activity.RESULT_OK,intent);
                    }
                    finish();

                    clicked=0;
                    clickedSequence.clear();
                }
            }
        });


    }

}