package com.example.myapplication.Firebase;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.Toast;


import com.google.android.gms.tasks.OnFailureListener;

/**
 * Klasa nasłuchująca błędu przy tworzeniu użytkownika.
 */

public class CreateFailure implements OnFailureListener {
    private Context ctx;

    public CreateFailure(Context ctx) { this.ctx=ctx; }
    @Override
    public void onFailure(@NonNull Exception e) {
        Toast.makeText(ctx, e.getMessage(), Toast.LENGTH_LONG).show();
    }
}