package com.example.oop;

public class FillInTheBlanksCard extends Card{



    FillInTheBlanksCard(String question, String answer, String difficulty) {
        super(question, answer, difficulty);
        super.cardType = "Fill in the blanks";
    }

}
