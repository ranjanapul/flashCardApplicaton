package com.example.oop;

public class SubjectiveCard extends Card{

    SubjectiveCard(String question, String answer, String difficulty) {
        super(question, answer, difficulty);
        super.cardType = "Subjective Question";
    }
}
