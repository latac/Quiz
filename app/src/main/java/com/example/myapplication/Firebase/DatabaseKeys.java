package com.example.myapplication.Firebase;

/**
 * Klucze do bazy danych.
 */
public enum DatabaseKeys {
    /**
     * Klucz do węzła Users
     */
    USERS("user"),
    ASTRONOMIA("Astronomia"),
    FIZYKA("Fizyka"),
    KATEGORIA("Kategoria"),
    MATEMATYKA("Matematyka"),
    OCENA_PYTAN("Ocena Pytań"),
    PROGRAMOWANIE("Programowanie");

    private String key;

    DatabaseKeys(String myKey) {
        this.key = myKey;
    }

    /**
     * Funkcja zwracająca tekst dla klucza.
     * @return Tekst dla danego klucza.
     */
    public String getKey() {
        return key;
    }
}
