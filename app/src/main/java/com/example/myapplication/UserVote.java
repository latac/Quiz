package com.example.myapplication;

public class UserVote {
    public String uid;
    public Boolean vote;

    public UserVote() {
        uid = new String();
        vote = false;
    }

    public UserVote(String uid, boolean vote) {
        this.uid = uid;
        this.vote = vote;
    }
}

