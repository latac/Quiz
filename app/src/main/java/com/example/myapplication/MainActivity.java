package com.example.myapplication;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.Firebase.Authentication;
import com.example.myapplication.Firebase.Database;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button signInButton = (Button)findViewById(R.id.signInButton);
        Button signUpButton = (Button)findViewById(R.id.signUpButton);

        signInButton.setOnClickListener(this);
        signUpButton.setOnClickListener(this);

    }

    public void ShowMsg(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onClick(View view) {
    int targetId = view.getId();
        Authentication auth = Authentication.GetInstance();
        EditText login = findViewById(R.id.editText2);
        EditText password = findViewById(R.id.editText);
        if(targetId == R.id.signInButton){
            auth.SignIn(
                    login.getText().toString().trim(),
                    password.getText().toString().trim(),
                    new OnCompleteListener() {
                        @Override
                        public void onComplete(@NonNull Task task) {
                            if (task.isSuccessful()) {
                                Intent changeScreen =
                                        new Intent(MainActivity.this, LogedInActivity.class);
                                startActivity(changeScreen);
                            }
                        }
                    },
                    new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            ShowMsg("Bad input");
                        }
                    }
            );

        }
        if(targetId == R.id.signUpButton){
            auth.CreateUser(
                    this,
                    login.getText().toString().trim(),
                    password.getText().toString().trim()
            );
        }
    }
}



