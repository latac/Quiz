package com.example.myapplication;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Firebase.Authentication;
import com.example.myapplication.Firebase.Database;
import com.example.myapplication.Firebase.DatabaseKeys;
import com.example.myapplication.Firebase.Person;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class LogedInActivity extends AppCompatActivity implements View.OnClickListener {
    public Person user;

    public ValueEventListener odczytajUzytkownika = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            //for (DataSnapshot ds : dataSnapshot.getChildren()) {
                user = dataSnapshot.getValue(Person.class);
                FillPoints();
            //}
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };

    public void FillPoints() {
        TextView point = findViewById(R.id.points);
        point.setText("" + user.Punkty);
    }

    @Override
    public void onBackPressed() {
        if (Build.VERSION.SDK_INT >= 16)
            this.finishAffinity();
        else
            this.finish();

        System.exit(0);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loged_in);

        String uid = Authentication.GetInstance().GetUID();
        Database.GetInstance().GetUser(uid, odczytajUzytkownika);

        Button mathCategoryButton = (Button) findViewById(R.id.mathCategoryButton);
        Button astronomyCategoryButton = (Button) findViewById(R.id.astronomyCategoryButton);
        Button physicsCategoryButton = (Button) findViewById(R.id.physicsCategoryButton);
        Button programmingCategoryButton = findViewById(R.id.programmingCategoryButton);
        Button topScoresButton = (Button) findViewById(R.id.topScoresButton);
        Button logoutButton = (Button) findViewById(R.id.logOutButton);

        Button add = findViewById(R.id.AddQuestion);

        topScoresButton.setOnClickListener(this);
        mathCategoryButton.setOnClickListener(this);
        astronomyCategoryButton.setOnClickListener(this);
        physicsCategoryButton.setOnClickListener(this);
        programmingCategoryButton.setOnClickListener(this);
        logoutButton.setOnClickListener(this);
        add.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int targetId = view.getId();

        QuestionActivity.points = 0;
        QuestionActivity.questionNumber = 0;

        if (targetId == R.id.mathCategoryButton) {
            Intent changeScreen =
                    new Intent(LogedInActivity.this, QuestionActivity.class);
            changeScreen.putExtra("kategoria", DatabaseKeys.MATEMATYKA.getKey());
            startActivity(changeScreen);
        }

        if (targetId == R.id.astronomyCategoryButton) {
            Intent changeScreen =
                    new Intent(LogedInActivity.this, QuestionActivity.class);
            changeScreen.putExtra("kategoria", DatabaseKeys.ASTRONOMIA.getKey());
            startActivity(changeScreen);
        }

        if (targetId == R.id.physicsCategoryButton) {

            Intent changeScreen =
                    new Intent(LogedInActivity.this, QuestionActivity.class);
            changeScreen.putExtra("kategoria", DatabaseKeys.FIZYKA.getKey());
            startActivity(changeScreen);
        }

        if (targetId == R.id.programmingCategoryButton) {
            Intent changeScreen =
                    new Intent(LogedInActivity.this, QuestionActivity.class);
            changeScreen.putExtra("kategoria", DatabaseKeys.PROGRAMOWANIE.getKey());
            startActivity(changeScreen);
        }

        if (targetId == R.id.topScoresButton){
            Toast.makeText(getApplicationContext(), "Top scores button",
                    Toast.LENGTH_SHORT).show();
            Intent changeScreen =
                    new Intent(LogedInActivity.this, TopScoresActivity.class);
            startActivity(changeScreen);
        }

        if (targetId == R.id.logOutButton) {
            Toast.makeText(getApplicationContext(), "Bye Bye :(",
                    Toast.LENGTH_SHORT).show();
            Authentication.GetInstance().SignOut();
            if (Build.VERSION.SDK_INT >= 16)
                this.finishAffinity();
            else
                this.finish();

            System.exit(0);
        }

        if (targetId == R.id.AddQuestion) {
            Intent changeScreen =
                    new Intent(LogedInActivity.this, AddMyQuestionActivity.class);
            startActivity(changeScreen);
        }

    }
}
