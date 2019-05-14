package edu.ktu.appsas;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;

public class Settings_activity extends AppCompatActivity {

    private static final String PREFERENCES_NAME = "PlayerSettings";
    private static final String KEY_NAME = "PlayerName";
    private static final String KEY_AGE = "PlayerAge";
    private static final String KEY_TIMES = "NumberOfGuesses";
    private static final String KEY_DIFFICULTY = "difficulty";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        loadSettings();
    }

    protected  void loadSettings(){
        SharedPreferences preferences = getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
        String playerName = preferences.getString(KEY_NAME, "Player0");
        String difficulty = preferences.getString(KEY_DIFFICULTY, "medium");
        int playerAge = preferences.getInt(KEY_AGE, 0);
        int numberOfGuesses = preferences.getInt(KEY_TIMES, 7);

        EditText nameField = findViewById(R.id.nameEditText);
        EditText ageField = findViewById(R.id.ageEditText);
        RadioGroup difficultyGroup = (RadioGroup)findViewById(R.id.difficulty_group);
        switch (difficulty)
        {
            case "easy":
                difficultyGroup.check(R.id.radio_easy);
                break;
            case "medium":
                difficultyGroup.check(R.id.radio_medium);
                break;
            case "hard":
                difficultyGroup.check(R.id.radio_hard);
                break;
        }

        nameField.setText(playerName);
        ageField.setText(Integer.toString(playerAge));
    }
    protected void saveSettings ()
    {
        SharedPreferences.Editor preferencesEditor = getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE).edit();

        EditText nameField = findViewById(R.id.nameEditText);
        EditText ageField = findViewById(R.id.ageEditText);
        RadioGroup difficultyGroup = (RadioGroup)findViewById(R.id.difficulty_group);

        String playerName = nameField.getText().toString();
        int playerAge = Integer.parseInt(ageField.getText().toString());
        String difficulty = "easy";
        switch(difficultyGroup.getCheckedRadioButtonId())
        {
            case R.id.radio_easy:
                difficulty = "easy";
                break;
            case R.id.radio_medium:
                difficulty = "medium";
                break;
            case R.id.radio_hard:
                difficulty = "hard";
                break;
        }
        preferencesEditor.putString("difficulty", difficulty);

        preferencesEditor.putString(KEY_NAME, playerName);
        preferencesEditor.putInt(KEY_AGE, playerAge);

        preferencesEditor.apply();
    }
    public void onButtonClick (View view){
        if (view.getId()  == R.id.save_settings_btn)
        {
            saveSettings();
            finish(); // actitvy uzdarymas

        }
    }

}
