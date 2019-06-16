package com.example.myapplication;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.myapplication.Firebase.Database;
import com.example.myapplication.Firebase.Person;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class TopScoresActivity extends AppCompatActivity implements View.OnClickListener {
    List<Person> users = new ArrayList<>();

    ValueEventListener funkcjaWczytujaca = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            for (DataSnapshot ds : dataSnapshot.getChildren()) {
                users.add(ds.getValue(Person.class));
            }
            Initialize();
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };

    public void Initialize() {
        TextView[] tv = new TextView[9];
        tv[0] = findViewById(R.id.top1);
        tv[1] = findViewById(R.id.top2);
        tv[2] = findViewById(R.id.top3);
        tv[3] = findViewById(R.id.top5);
        tv[4] = findViewById(R.id.top6);
        tv[5] = findViewById(R.id.top7);
        tv[6] = findViewById(R.id.top8);
        tv[7] = findViewById(R.id.top9);
        tv[8] = findViewById(R.id.top10);

        Collections.reverse(users);
        for (int i = users.size(); i > 0; i--)
        {
            int pos = users.size() - i;
                tv[pos].setText(users.get(pos).Punkty + " " + users.get(pos).Mail);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_scores);

        Database.GetInstance().GetHighscore(funkcjaWczytujaca);
    }

    @Override
    public void onClick(View v) {

    }
}
