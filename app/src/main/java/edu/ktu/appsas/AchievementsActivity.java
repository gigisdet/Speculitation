package edu.ktu.appsas;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AchievementsActivity extends Activity {

    ArrayList<AchievementsEntry> mContents;

    AchievementsAdapter mAdapter;

    ListView mListView;

    AchievementsDatabaseHandler myDb;

    ArrayList<AchievementsEntry> contacts;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achievements);

        myDb = new AchievementsDatabaseHandler(this);
        mListView = (ListView) findViewById(R.id.achievementsList);


        contacts = new ArrayList<>(myDb.getAllEntries());

        mAdapter = new AchievementsAdapter(this, contacts);
        mListView.setAdapter(mAdapter);


        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String name = contacts.get(position).getName();
                int guessNumber = contacts.get(position).getNumber();
                int guessTimes = contacts.get(position).gerTurns();
                long AchievementID = contacts.get(position).getID();
                String difficulty = contacts.get(position).getDifficulty();

                showAlertDialog(AchievementID, name, guessNumber, guessTimes, difficulty);

            }
        });

    }


    void showAlertDialog(final long id, String name, int guessNumber, int guessTimes, String difficulty) {
        String message = "";
        String messageDelete = "";
        AlertDialog.Builder builder = new AlertDialog.Builder(AchievementsActivity.this);
        builder.setCancelable(true);

        builder.setNegativeButton("DELETE", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Intent intent = new Intent(Game_Activity.this, MainActivity.class);
                //startActivity(intent);
                long deleteID = id;
                myDb.deleteEntry(deleteID);
                mAdapter.notifyDataSetChanged();

                Context context = getApplicationContext();
                CharSequence text = "Record was deleted!";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();

                contacts = new ArrayList<>(myDb.getAllEntries());

                mAdapter = new AchievementsAdapter(AchievementsActivity.this, contacts);
                mListView.setAdapter(mAdapter);

            }
        });
        builder.setPositiveButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });


        builder.setTitle("Delete record");


        message = "Do you want to delete this record?  " + "\n";
        messageDelete = "Player name: " + name + " \n" + "Guessed number: " + guessNumber + "\n" + "Difficulty: " + difficulty;
        builder.setMessage(message + "\n" + messageDelete);
        builder.show();
    }
}
