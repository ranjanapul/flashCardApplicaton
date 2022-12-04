package com.example.oop;

public class TrueFalseCard extends Card {



    TrueFalseCard(String question, String answer, String difficulty){
        super(question, answer, difficulty);
        super.cardType = "True/False";

    }

    void editCard(TrueFalseCard card){
        this.question = card.question;
        this.answer = card.answer;
        this.difficulty = card.difficulty;


    }

}
