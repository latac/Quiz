package com.example.myapplication.Firebase;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.myapplication.UserVote;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.List;

import static com.example.myapplication.Firebase.DatabaseKeys.ASTRONOMIA;
// Od razu informacja. Ja korzystam z 'pojedynczego' odczytywania, jest też
// listener, który ciągle nasłuchuje na danym węźle, dzięki czemu mamy
// real time. Ale tego nie tłumaczę, bo tego nie robiłem.

// Klasa obsługująca bazę danych w firebase
public class Database {
    IloscPytan punkty = new IloscPytan();
    HashMap<String, HashMap<String, Integer>> oceny;
    // instancja połączenia z bazą danych
    private FirebaseDatabase myDatabase;
    // singleton
    private static Database _instance;
    // singleton
    public static Database GetInstance() {
        if (_instance == null)
            _instance = new Database();

        return _instance;
    }
// singleton
    private Database() {
    oceny = new HashMap<>();
        oceny.put(DatabaseKeys.MATEMATYKA.getKey(), new HashMap<String, Integer>());
        oceny.put(DatabaseKeys.FIZYKA.getKey(), new HashMap<String, Integer>());
        oceny.put(DatabaseKeys.ASTRONOMIA.getKey(), new HashMap<String, Integer>());
        oceny.put(DatabaseKeys.PROGRAMOWANIE.getKey(), new HashMap<String, Integer>());

        myDatabase = FirebaseDatabase.getInstance();

        myDatabase.getReference(DatabaseKeys.MATEMATYKA.getKey())
                .addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                        punkty.Zwieksz(DatabaseKeys.MATEMATYKA.getKey());
                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    }

                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                        punkty.Zmniejsz(DatabaseKeys.MATEMATYKA.getKey());
                    }

                    @Override
                    public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

        myDatabase.getReference(DatabaseKeys.FIZYKA.getKey())
                .addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                        punkty.Zwieksz(DatabaseKeys.FIZYKA.getKey());
                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    }

                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                        punkty.Zmniejsz(DatabaseKeys.FIZYKA.getKey());
                    }

                    @Override
                    public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

        myDatabase.getReference(DatabaseKeys.PROGRAMOWANIE.getKey())
                .addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                        punkty.Zwieksz(DatabaseKeys.PROGRAMOWANIE.getKey());
                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    }

                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                        punkty.Zmniejsz(DatabaseKeys.PROGRAMOWANIE.getKey());
                    }

                    @Override
                    public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

        myDatabase.getReference(DatabaseKeys.ASTRONOMIA.getKey())
                .addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                        punkty.Zwieksz(DatabaseKeys.ASTRONOMIA.getKey());
                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    }

                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                        punkty.Zmniejsz(DatabaseKeys.ASTRONOMIA.getKey());
                    }

                    @Override
                    public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

        myDatabase.getReference(DatabaseKeys.OCENA_PYTAN.getKey())
                .addChildEventListener(new ChildEventListener() {

                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                        String dzial = dataSnapshot.getKey();
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            String pytanie = ds.getKey();
                            for (DataSnapshot ds2 : ds.getChildren()) {
                                if (!oceny.get(dzial).containsKey(pytanie)) {
                                    oceny.get(dzial).put(pytanie, 0);
                                }
                                oceny.get(dzial).put(pytanie,oceny.get(dzial).get(pytanie) + ((ds2.getValue(Boolean.class) == false) ? 1 : -1));
                            }

                            if (oceny.get(dzial).get(pytanie) >= 5) {
                                myDatabase.getReference(DatabaseKeys.OCENA_PYTAN.getKey())
                                        .child(dzial)
                                        .child(pytanie)
                                        .removeValue();
                                myDatabase.getReference(dzial)
                                        .child(pytanie)
                                        .removeValue();
                            }
                        }

                    }

                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

    }
// funkcja tworząca użytkownika w bazie danych
    public Person CreateUser(
            @NonNull Task<AuthResult> task,
            String login
    ) {
        Person myAccount = new Person();
        myAccount.Mail = login;

        //String uid = Authentication.GetInstance().GetUID();
        AuthResult a = task.getResult();
        FirebaseUser u = a.getUser();
        String uid = u.getUid();

        String path = DatabaseKeys.USERS.getKey() + "/" + uid;

        // Tak zawsze wygląda schemat do zapisu: najpierw pobieramy referencję do węzła głównego bazy danych
        myDatabase.getReference()
        // teraz pobieramy węzeł szczegółowy; zawsze childem schodzimy o jeden poziom (no chyba, że podamy referencję typu: "groupUser/user/testowy") to schodzimy od razu o 3 poziomy wgłąb
                .child(DatabaseKeys.USERS.getKey())
        // tutaj robię taki myk, że każdy element posiada unikalny klucz;
        // dlatego pobieram UID użytkownika i w tym momencie tworzony jest węzeł o nazwie klucza
                .child(uid)
        // przypisuję obiekt - obiekty są serializowane, więc musi istnieć konstruktor domyślny bezparametrowy, oraz zserializuje tylko te pola, które są publiczne
                .setValue(myAccount);

        return myAccount;
    }


public void DodajPytanie(DatabaseKeys kategoria, Question pytanie) {
        String t = "";
        switch (kategoria) {
            case ASTRONOMIA: t = "Astronomia"; break;
            case FIZYKA: t = "Fizyka"; break;
            case MATEMATYKA: t = "Matematyka"; break;
            case PROGRAMOWANIE: t = "Programowanie"; break;

        }

    myDatabase.getReference(kategoria.getKey())
            .child("Pytanie " + punkty.PobierzIlosc(t))
            .setValue(pytanie);
}


    public void PobierzPytanie(String kategoria, int id, ValueEventListener funkcjaOdczytujaca) {
        myDatabase.getReference(kategoria)
                .addListenerForSingleValueEvent(funkcjaOdczytujaca);
    }

    public void PobierzWszystkiePytania(String kategoria, ValueEventListener funkcjaOdczytujaca) {
        String k = kategoria;
        myDatabase.getReference(kategoria)
                .addListenerForSingleValueEvent(funkcjaOdczytujaca);
    }


    public void GetUser(String uid, ValueEventListener funkcjaOdczytujaca) {
        myDatabase.getReference(DatabaseKeys.USERS.getKey()).child(uid)
                .addListenerForSingleValueEvent(funkcjaOdczytujaca);
    }

    public void GetHighscore(ValueEventListener funkcjawczytujaca) {
        myDatabase.getReference(DatabaseKeys.USERS.getKey())
                .orderByChild("Punkty").limitToLast(10)
                .addListenerForSingleValueEvent(funkcjawczytujaca);
    }

    public void CanVote(String kategoria, String wezelPytania, ValueEventListener fwczytujaca) {
        myDatabase.getReference(DatabaseKeys.OCENA_PYTAN.getKey())
                .child(kategoria)
                .child(wezelPytania)
                .child(Authentication.GetInstance().GetUID())
                .addListenerForSingleValueEvent(fwczytujaca);
    }

    public void VoteUp(String kategoria, String wezelPytania, boolean vote) {
        myDatabase.getReference(DatabaseKeys.OCENA_PYTAN.getKey())
                .child(kategoria)
                .child(wezelPytania)
                .child(Authentication.GetInstance().GetUID())
                .setValue(vote);
    }

    public void AddPoints(int points) {
        final int myPoints = points;
        myDatabase.getReference(DatabaseKeys.USERS.getKey())
                .child(Authentication.GetInstance().GetUID())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Person activeUser = dataSnapshot.getValue(Person.class);
                        activeUser.Punkty += myPoints;
                        myDatabase.getReference(DatabaseKeys.USERS.getKey())
                                .child(Authentication.GetInstance().GetUID())
                                .setValue(activeUser);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }

    public void VoteDown(String kategoria, String wezelPytania, boolean vote) {
        myDatabase.getReference(DatabaseKeys.OCENA_PYTAN.getKey())
                .child(kategoria)
                .child(wezelPytania)
                .child(Authentication.GetInstance().GetUID())
                .setValue(vote);
    }

    final class IloscPytan {
        public int mat = 0;
        public int fiz = 0;
        public int astr = 0;
        public int inf = 0;

        public int PobierzIlosc(String kat) {
            int res = 0;
            switch (kat) {
                case "Matematyka" : res = mat; break;
                case "Fizyka" : res = fiz;break;
                case "Astronomia" : res = astr;break;
                case "Programowanie" : res = inf;break;

            }
            return  res;
        }
        public void Zwieksz(String kat) {
            switch (kat) {
                case "Matematyka" : mat++;break;
                case "Fizyka" : fiz++;break;
                case "Astronomia" : astr++;break;
                case "Programowanie" : inf++;break;

            }
        }
        public void Zmniejsz(String kat) {
            switch (kat) {
                case "Matematyka" : mat--;break;
                case "Fizyka" : fiz--;break;
                case "Astronomia" : astr--;break;
                case "Programowanie" : inf--;break;

            }
        }
    }



    public void AddQuestion(String kategoria, Question pytanie) {
        final String kat = kategoria;

        /*
        myDatabase.getReference(kategoria)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        int iterator = 0;
                        for (DataSnapshot ds : dataSnapshot.getChildren())
                            iterator++;
                        punkty.mat = iterator+1;
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
        */

        punkty.Zwieksz(kategoria);

        myDatabase.getReference(kategoria)
                .child("Pytanie " + punkty.PobierzIlosc(kategoria))
                .setValue(pytanie);
    }
}
