package com.example.myapplication.Firebase;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;



/**
 * Klasa nasłuchująca zakończenia utworzenia konta dla użytkownika.
 */
public class CreateSuccess implements OnCompleteListener<AuthResult> {
    Context ctx;
    String Mail;


    public CreateSuccess(Context ctx, String Mail) {
        this.ctx = ctx;
        this.Mail = Mail;
    }
// Tutaj jest po prostu logika, co ma się wydarzyć po 'założeniu nowego konta' - ja akurat muszę
// utworzyć podobną kopię w bazie danych
    @Override
    public void onComplete(@NonNull Task<AuthResult> task) {
        if (task.isSuccessful()) {
            Database.GetInstance().CreateUser(task, Mail);
            Intent intent = new Intent();

            SuccessMsg(Mail);

        } else {
            FailMsg();
        }
    }

    /**
     * Funkcja pomocnicza do wyświetlenia powitania.
     * @param login login użytkownika zarejestrowanego
     */
    private void SuccessMsg (String login) {
        String msg = "Sing up completed!";
        Toast.makeText(ctx, msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * Funkcja pomocnicza do wyświetlenia błędu przy tworzeniu użytkownika.
     */
    private void FailMsg() {
        String msg = "Sing up failed!";
        Toast.makeText(ctx, msg, Toast.LENGTH_SHORT).show();
    }
}



