package edu.ktu.appsas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }


    public void onButtonClick(View view) {
        if (view.getId() == R.id.start_game_btn) {
            StartGameCLick();
        } else if (view.getId() == R.id.settings_btn) {
            SettingsCLick();
        } else if (view.getId() == R.id.about_btn) {
            AboutCLick();
        } else if (view.getId() == R.id.achievments_btn) {
            AchievementsClick();
        }
    }

    public void StartGameCLick() {
        Intent intent = new Intent(this, Game_Activity.class);
        startActivity(intent);
    }

    public void SettingsCLick() {
        Intent intent = new Intent(this, Settings_activity.class);
        startActivity(intent);
    }

    public void AboutCLick() {
        Intent intent = new Intent(this, AboutActivity.class);
        startActivity(intent);
    }

    public void AchievementsClick() {
        Intent intent = new Intent(this, AchievementsActivity.class);
        startActivity(intent);
    }

}
