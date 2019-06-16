package com.example.myapplication;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.Firebase.Database;
import com.example.myapplication.Firebase.Question;

public class AddMyQuestionActivity extends AppCompatActivity {
    Button logout, add, math, phys, inf, astr;
    String kategoria = "";
    EditText pytanie,a, b, c, d, poprawna;
    Question q;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_my_question);

        logout = findViewById(R.id.logOutButton);
        add = findViewById(R.id.Add);

        math = findViewById(R.id.mathCategoryButton);
        phys = findViewById(R.id.physicsCategoryButton);
        inf = findViewById(R.id.programmingCategoryButton);
        astr = findViewById(R.id.astronomyCategoryButton);

        pytanie = findViewById(R.id.editText1);
        a = findViewById(R.id.editText2);
        b = findViewById(R.id.editText3);
        c = findViewById(R.id.editText4);
        d = findViewById(R.id.editText5);
        poprawna = findViewById(R.id.editText6);

        math.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                String old = kategoria;
                String newK = "Matematyka";
                Log.d("QWE", "1");

                math.setBackgroundColor(getColor(R.color.colorAccent));
                inf.setBackgroundColor(getColor(R.color.colorAccent));
                phys.setBackgroundColor(getColor(R.color.colorAccent));
                astr.setBackgroundColor(getColor(R.color.colorAccent));



                if (old.equals(newK)) {
                    kategoria = "";
                } else {
                    kategoria = "Matematyka";
                    math.setBackgroundColor(getColor(R.color.colorPrimary));
                }
            }
        });

        phys.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                String old = kategoria;
                String newK = "Fizyka";
                Log.d("QWE", "2");

                math.setBackgroundColor(getColor(R.color.colorAccent));
                inf.setBackgroundColor(getColor(R.color.colorAccent));
                phys.setBackgroundColor(getColor(R.color.colorAccent));
                astr.setBackgroundColor(getColor(R.color.colorAccent));



                if (old.equals(newK)) {
                    kategoria = "";
                } else {
                    kategoria = "Fizyka";
                    phys.setBackgroundColor(getColor(R.color.colorPrimary));
                }
            }
        });

        inf.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                Log.d("QWE", "3");
                String old = kategoria;
                String newK = "Programowanie";

                math.setBackgroundColor(getColor(R.color.colorAccent));
                inf.setBackgroundColor(getColor(R.color.colorAccent));
                phys.setBackgroundColor(getColor(R.color.colorAccent));
                astr.setBackgroundColor(getColor(R.color.colorAccent));


                if (old.equals(newK)) {
                    kategoria = "";
                } else {
                    kategoria = "Programowanie";
                    inf.setBackgroundColor(getColor(R.color.colorPrimary));
                }
            }
        });

        astr.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                Log.d("QWE", "4");
                String old = kategoria;
                String newK = "Astronomia";

                math.setBackgroundColor(getColor(R.color.colorAccent));
                inf.setBackgroundColor(getColor(R.color.colorAccent));
                phys.setBackgroundColor(getColor(R.color.colorAccent));
                astr.setBackgroundColor(getColor(R.color.colorAccent));




                if (old.equals(newK)) {
                    kategoria = "";
                } else {
                    kategoria = "Astronomia";
                    astr.setBackgroundColor(getColor(R.color.colorPrimary));
                }
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!pytanie.getText().toString().trim().equals("") &&
                        !a.getText().toString().trim().equals("") &&
                        !b.getText().toString().trim().equals("") &&
                        !c.getText().toString().trim().equals("") &&
                        !d.getText().toString().trim().equals("") &&
                        (poprawna.getText().toString().trim().equals("A") ||
                        poprawna.getText().toString().trim().equals("B") ||
                        poprawna.getText().toString().trim().equals("C") ||
                        poprawna.getText().toString().trim().equals("D")) &&
                        !kategoria.equals("")
                        ) {
                    q = new Question();
                    q.A = a.getText().toString().trim();
                    q.B = b.getText().toString().trim();
                    q.C = c.getText().toString().trim();
                    q.D = d.getText().toString().trim();
                    q.PoprawnaOdpowiedź = poprawna.getText().toString().trim();
                    q.Treść =  pytanie.getText().toString().trim();
                    Database.GetInstance().AddQuestion(kategoria, q);

                    Show("Dodano");
                } else {
                    Show("coś jest źle");
                }

            }
        });
    }

    private void Show(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
