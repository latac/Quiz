package com.example.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.myapplication.Firebase.Database;

public class ScoreAfterGameActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_after_game);
        TextView score = findViewById(R.id.textViewEnd2);

        score.setText(" " +QuestionActivity.points);

        Database.GetInstance().AddPoints(QuestionActivity.points);
    }

    @Override
    public void onClick(View v) {

    }


    @Override
    public void onBackPressed() {
finish();
        Intent changeScreen =
                new Intent(this, LogedInActivity.class);
        startActivity(changeScreen);
    }


}
