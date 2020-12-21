package edu.jbishop.simon_game_final_project;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private final int BLUE = 1;
    private final int RED = 2;
    private final int YELLOW = 3;
    private final int GREEN = 4;

    //buttons for playing.
    Button btnPlay ;
    ImageButton flashButton,btnRed, btnBlue, btnYellow, btnGreen;


   //variables for transfering data
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    //game code variables
    ArrayList<Integer> gameSequence;
    int arrayIndex = 0;
    private Object mutex = new Object();
    int seq=0;
    int sequenceCount = 4, n = 0;
    Handler handler;
    Runnable runnable;

    //timer for the button flashing. provided from class sample
    CountDownTimer ct = new CountDownTimer(6000,  1500) {

        public void onTick(long millisUntilFinished) {
            oneButton();
        }

        public void onFinish() {
            for (int i = 0; i< arrayIndex; i++)
                Log.d("game sequence", String.valueOf(gameSequence.get(i)));
            Intent i = new Intent(MainActivity.this, play_page.class);
            i.putIntegerArrayListExtra("numbers", gameSequence);
            startActivityForResult(i,1);
        }
    };

    //this is what is used for transfering the data between the activities,
    //this code was gotten from youtube and with the help of class mates
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode==1){
            seq=0;
            gameSequence.clear();
            editor.putInt("seqcount",data.getIntExtra("seq",4));
            editor.putInt("score",data.getIntExtra("score",0));
            editor.commit();
            sequenceCount=data.getIntExtra("seq",4);
            if (data.getStringExtra("Result").equals("S")){
                handler.postDelayed(runnable,1500);
            }
            else {
                AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Game Over");
                builder.setMessage("Your Score was "+data.getIntExtra("score",0));
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.show();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        handler=new Handler();
        runnable =new Runnable() {
            @Override
            public void run() {
                if (seq<sequenceCount) {
                    oneButton();
                    handler.postDelayed(this, 1500);
                    seq++;
                }
                else {
                    handler.removeCallbacks(this);
                    Intent i = new Intent(MainActivity.this, play_page.class);
                    i.putIntegerArrayListExtra("numbers", gameSequence);
                    startActivityForResult(i,1);
                }
            }
        };
        preferences = getSharedPreferences("score_ref", MODE_PRIVATE);
        editor = preferences.edit();
        gameSequence=new ArrayList<>();

        btnPlay = findViewById(R.id.btn_Play);
        btnRed = findViewById(R.id.btnRedFlash);
        btnBlue = findViewById(R.id.btnBlueFlash);
        btnGreen = findViewById(R.id.btnGreenFlash);
        btnYellow = findViewById(R.id.btnYellowFlash);
    }


//all of the below taken from in class samples.
    public void doPlay(View view) {
        handler.postDelayed(runnable,1500);
    }
    private void oneButton() {
        n = getRandom(4);
        switch (n) {
            case 1:
                flashButton(btnBlue);
                gameSequence.add(BLUE);
                break;
            case 2:
                flashButton(btnRed);
                gameSequence.add(RED);
                break;
            case 3:
                flashButton(btnYellow);
                gameSequence.add(YELLOW);
                break;
            case 4:
                flashButton(btnGreen);
                gameSequence.add(GREEN);
                break;
            default:
                break;
        }   // end switch
    }
    private int getRandom(int maxValue) {
        return ((int) ((Math.random() * maxValue) + 1));
    }
    private void flashButton(ImageButton button) {
        flashButton = button;
        Handler handler = new Handler();
        Runnable r = new Runnable() {
            public void run() {

                flashButton.setPressed(true);
                flashButton.invalidate();
                flashButton.performClick();
                Handler handler1 = new Handler();
                Runnable r1 = new Runnable() {
                    public void run() {
                        flashButton.setPressed(false);
                        flashButton.invalidate();
                    }
                };
                handler1.postDelayed(r1, 600);

            }
        };
        handler.postDelayed(r, 600);
    }

}