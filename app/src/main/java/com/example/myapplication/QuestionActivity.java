package com.example.myapplication;

import android.content.Intent;
import android.os.Build;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Firebase.Authentication;
import com.example.myapplication.Firebase.Database;
import com.example.myapplication.Firebase.Person;
import com.example.myapplication.Firebase.Question;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class QuestionActivity extends AppCompatActivity implements View.OnClickListener {
    List<Integer> wybrane;
    String kategoria;
    private InterstitialAd mInterstitialAd;
    public int counter = 60;
    public int tick = 1;
    public static int questionNumber = 0;
    public static int numberOfQuestions = 0;
    public static int points = 0;
    public int lastQuestion = 5;
    boolean czyMoge = false;
    Question aktywnePytanie;
    CountDownTimer testowyCzasomierz;
   public List<Question> pytania = new ArrayList<>();
    Button voteUp, voteDown;

    public ValueEventListener czyMogeGlosowacFunkcja = new ValueEventListener() {
    @Override
    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
        boolean jestemTU = false;
        Boolean u = dataSnapshot.getValue(Boolean.class);
        if (u != null) {
            voteUp.setVisibility(View.INVISIBLE);
            voteDown.setVisibility(View.INVISIBLE);
        } else {

            voteUp.setVisibility(View.VISIBLE);
            voteDown.setVisibility(View.VISIBLE);
            voteUp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Database.GetInstance().VoteUp(kategoria, "Pytanie " + (questionNumber + 1), true);

                    voteUp.setVisibility(View.INVISIBLE);
                    voteDown.setVisibility(View.INVISIBLE);
                }
            });
            voteDown.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Database.GetInstance().VoteDown(kategoria, "Pytanie " + (questionNumber + 1), false);

                    voteUp.setVisibility(View.INVISIBLE);
                    voteDown.setVisibility(View.INVISIBLE);
                }
            });

        }
    }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {

    }
};


    public ValueEventListener funkcjaOdczytujaca = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            Question q;
            for (DataSnapshot ds : dataSnapshot.getChildren() ) {
                    q = ds.getValue(Question.class);
                    pytania.add(q);
            }

            Initialize();

        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };


    public void Initialize() {
    Database.GetInstance().CanVote(kategoria, "Pytanie " + (questionNumber+1), czyMogeGlosowacFunkcja);

        boolean aleToJuzBylo = true;
        int wylosowane = 0;
        while (aleToJuzBylo) {
            wylosowane = new Random().nextInt(pytania.size());
            if (!wybrane.contains(wylosowane))
                aleToJuzBylo = false;
        }

        wybrane.add(wylosowane);
        questionNumber = wylosowane;


        final TextView timer = (TextView) findViewById(R.id.timer);

        TextView pytanie = findViewById(R.id.textView4);

        RadioButton A = findViewById(R.id.answerA);
        RadioButton B = findViewById(R.id.answerB);
        RadioButton C = findViewById(R.id.answerC);
        RadioButton D = findViewById(R.id.answerD);

        pytanie.setText(pytania.get(questionNumber).Treść);

        A.setText(pytania.get(questionNumber).A);
        B.setText(pytania.get(questionNumber).B);
        C.setText(pytania.get(questionNumber).C);
        D.setText(pytania.get(questionNumber).D);



        testowyCzasomierz = new CountDownTimer(60000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                counter -= tick;

                timer.setText("Time left: " + counter);
                if (counter == 0) {
                    questionNumber++;
                    aktywnePytanie = pytania.get(questionNumber);
                    finish();
                    startActivity(getIntent());
                }
            }

            @Override
            public void onFinish() {

            }
        }.start();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        numberOfQuestions = getIntent().getIntExtra("licznik", 0);

        kategoria = getIntent().getStringExtra("kategoria");
        wybrane = new ArrayList<>();
        Database.GetInstance().PobierzWszystkiePytania(kategoria,funkcjaOdczytujaca);
        setContentView(R.layout.activity_question);


        Button logoutButton = (Button) findViewById(R.id.logOutButton);
        logoutButton.setOnClickListener(this);

        TextView amountOfQustionsTV = findViewById(R.id.amountOfQustions);

        amountOfQustionsTV.setText((numberOfQuestions  +1 )+ "/" + lastQuestion);


        Button nextQuestion = (Button) findViewById(R.id.nextQuestion);
        nextQuestion.setOnClickListener(this);

        voteUp = findViewById(R.id.upVote);
        voteDown = findViewById(R.id.downVote);
    }

    @Override
    public void onBackPressed() {
        testowyCzasomierz.cancel();
        super.onBackPressed();
    }

    @Override
    public void onClick(View view) {

        int targetId = view.getId();

        if (targetId == R.id.logOutButton) {
            Toast.makeText(getApplicationContext(), "Bye Bye :(",
                    Toast.LENGTH_SHORT).show();
            if (Build.VERSION.SDK_INT >= 16)
                this.finishAffinity();
            else
                this.finish();


            System.exit(0);
        }

        if (targetId == R.id.nextQuestion){
            RadioGroup grupa = findViewById(R.id.radioGroup);
            int wybrane = grupa.getCheckedRadioButtonId();
            HashMap<String, Integer> odpowiedzi = new HashMap<>();
            odpowiedzi.put("A", R.id.answerA);
            odpowiedzi.put("B", R.id.answerB);
            odpowiedzi.put("C", R.id.answerC);
            odpowiedzi.put("D", R.id.answerD);
            numberOfQuestions++;

            if (odpowiedzi.get(pytania.get(questionNumber).PoprawnaOdpowiedź) == wybrane) {
                points += counter;

            }

            if( numberOfQuestions >= lastQuestion){

                MobileAds.initialize(this, "ca-app-pub-8134007930372274~2673158089");

                mInterstitialAd = new InterstitialAd(this);
                mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");

                mInterstitialAd.loadAd(new AdRequest.Builder().build());

                mInterstitialAd.setAdListener(new AdListener() {
                    @Override
                    public void onAdLoaded() {
                        if (mInterstitialAd.isLoaded())
                            mInterstitialAd.show();
                    }

                    @Override
                    public void onAdFailedToLoad(int errorCode) {
                        // Code to be executed when an ad request fails.
                    }

                    @Override
                    public void onAdOpened() {
                        // Code to be executed when the ad is displayed.
                    }

                    @Override
                    public void onAdClicked() {
                        // Code to be executed when the user clicks on an ad.
                    }

                    @Override
                    public void onAdLeftApplication() {
                        // Code to be executed when the user has left the app.
                    }

                    @Override
                    public void onAdClosed() {
                        Intent changeScreen =
                                new Intent(QuestionActivity.this, ScoreAfterGameActivity.class);

                        startActivity(changeScreen);
                    }
                });
            }else {
                finish();
                Intent i = getIntent();
                i.putExtra("licznik", numberOfQuestions);
                startActivity(i);
            }

        }

    }
}
