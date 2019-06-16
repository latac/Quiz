package com.example.myapplication.Firebase;

import android.content.Context;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/*
Klasa do zarządzania połączeniami w firebase authorization (na stronie trzeba odblokowac maila)
*/
public final class Authentication {
    // instancja połaczenia z autoryzacją firebase
    private FirebaseAuth Authorization;
    // Singleton
    private static Authentication _instance;
    // Singleton
    private Authentication() {
        Authorization = FirebaseAuth.getInstance();
    }
    // Singleton
    public static Authentication GetInstance() {
        if (_instance == null)
            _instance = new Authentication();

        return _instance;
    }

// Funkcja tworząca użytkownika - interfejsem nie ma co się przejmować, po prostu udostępnia funkcję do pobrania podanego maila i podanego hasła
    public void CreateUser(Context ctx, String Mail, String Password) {
        CreateSuccess successAction = new CreateSuccess(ctx, Mail);
        CreateFailure failureAction = new CreateFailure(ctx);
// przy tworzeniu użytkownika trzeba miec listenera na powodzenie akcji, warto mieć tez listenera do obsługi błędów
        Authorization.createUserWithEmailAndPassword(Mail, Password)
                .addOnCompleteListener(successAction)
                .addOnFailureListener(failureAction);
    }
// Funkcja logująca - interfejs znowu dostarcza tylko dane do logowania
    public void SignIn(String Mail, String Password, OnCompleteListener success, OnFailureListener fail) {

// jak wyżej
        Authorization.signInWithEmailAndPassword(Mail, Password)
                .addOnCompleteListener(success)
                .addOnFailureListener(fail);
    }
// wylogowanie
    public void SignOut() {
        Authorization.signOut();
    }
// funkcja pośrednicząca zwracająca UID (unique id) dla użytkownika
    public String GetUID() { return Authorization.getCurrentUser().getUid(); }
// zwraca aktualnego użytkownika (jeżeli nie jest nikt zalogowany, to zwraca null)
    public FirebaseUser GetCurrentUser() {
        return Authorization.getCurrentUser();
    }
}
