package edu.ktu.appsas;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Game_Activity extends Activity /*implements WebTask.OnPostExecuteListener*/ {

    private int maxTurns = 7;
    private int currentTurn = 0;
    private int minNumber = 1;
    private int maxNumber = 10;
    private int randomNumber = -1;
    private Boolean hasWon = false;

    TextView turnsText;
    TextView rangeText;
    TextView resultText;

    EditText guessInput;
    Button guessBtn;

    AchievementsDatabaseHandler myDb;

    private static final String PREFERENCES_NAME = "PlayerSettings";
    private static final String KEY_NAME = "PlayerName";
    private static final String KEY_DIFFICULTY = "difficulty";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        turnsText = findViewById(R.id.turns);
        rangeText = findViewById(R.id.guessRange);
        //resultText = findViewById(R.id.guessResult);

        SharedPreferences sharedPreferences = getSharedPreferences(PREFERENCES_NAME, 0);
        String difficulty = sharedPreferences.getString(KEY_DIFFICULTY, "medium");
        switch (difficulty)
        {
            case "easy":
                maxTurns = 4;
                maxNumber = 10;
                break;
            case "medium":
                maxTurns = 5;
                maxNumber = 50;
                break;
            case "hard":
                maxTurns = 6;
                maxNumber = 100;
                break;
        }

        turnsText.setText(getString(R.string.truns_left) + maxTurns);

        String rangeString = "Input a number between " + minNumber + " and " + maxNumber;
        rangeText.setText(rangeString);


        guessInput = findViewById(R.id.guessedNumber);

        guessBtn = findViewById(R.id.guessBtn);

        guessBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GuessNumber();
            }
        });
        Random rand = new Random();
        randomNumber = rand.nextInt(maxNumber - minNumber) + minNumber;

        initializeCustomAdapter();

        myDb = new AchievementsDatabaseHandler(this);

    }

    void GuessNumber() {
        int guessedNumber = Integer.parseInt(guessInput.getText().toString());

        int guessResult = 0;

        if (guessedNumber > randomNumber) {
            guessResult = 1;
        } else if (guessedNumber < randomNumber) {
            guessResult = -1;
        }
        else {
            hasWon = true;
            showAlertDialog();

            return;

        }

        currentTurn++;
        turnsText.setText(getString(R.string.truns_left) + (maxTurns - currentTurn));

        //addToSimpleAdapter(guessedNumber, guessResult, maxTurns - currentTurn);
        //addToArrayAdapter(guessedNumber,guessResult,maxTurns-currentTurn);
        addToCustomAdapter(guessedNumber, guessResult, maxTurns - currentTurn);
        if (currentTurn >= maxTurns) {
            //Game lost!

            showAlertDialog();

        }
    }

    void showAlertDialog() {
        String message = "";
        AlertDialog.Builder builder = new AlertDialog.Builder(Game_Activity.this);
        builder.setCancelable(false);

        builder.setNegativeButton("HOME", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();

            }
        });
        builder.setPositiveButton("TRY AGAIN", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
                startActivity(getIntent());
            }
        });

        if (hasWon) {
            builder.setTitle("GAME WON!");
            SharedPreferences sharedPreferences = getSharedPreferences(PREFERENCES_NAME, 0);
            String name = sharedPreferences.getString(KEY_NAME, "palyer0");
            String difficulty = sharedPreferences.getString(KEY_DIFFICULTY, "medium");
            myDb.addEntry(new AchievementsEntry(0, name, currentTurn + 1, randomNumber, difficulty));

            int turns = currentTurn + 1;
            message = "You guessed number from " + turns + " guesses!";
        } else {
            builder.setTitle("GAME ENDED");
            message = "You've ran out of turns! Better luck next time! \n";
        }
        builder.setMessage(message + "\n The random number was: " + Integer.toString(randomNumber));
        builder.show();
    }


    GuessHistoryAdapter customAdapter;
    List<GuessHistoryData> customAdapterData;

    void initializeCustomAdapter() {
        customAdapterData = new ArrayList<>();
        customAdapter = new GuessHistoryAdapter(this, customAdapterData);

        ListView listView = findViewById(R.id.resultList);
        listView.setAdapter(customAdapter);
    }

    void addToCustomAdapter(int guessedNumber, int result, int turnsLeft) {
        customAdapterData.add(new GuessHistoryData(guessedNumber, result, turnsLeft));
        customAdapter.notifyDataSetChanged();
    }



}
