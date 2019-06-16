package com.example.myapplication.Firebase;


public class Question {
    public String A, B, C, D, PoprawnaOdpowiedź, Treść;

    public Question() {
        this.A = this.B = this.C = this.D = this.PoprawnaOdpowiedź = this.Treść = new String();
    }

    public Question(String A, String B, String C, String D, String poprawna, String tresc) {
        this.A = A;
        this.B = B;
        this.C = C;
        this.D = D;
        this.PoprawnaOdpowiedź = poprawna;
        this.Treść = tresc;
    }
}