package com.example.oop;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
public abstract class Card {
    protected String question;
    protected String answer;
    protected String difficulty;
    protected String lastSeen;
    public String cardType;

    Card(String question, String answer, String difficulty){
        this.question = question;
        this.answer = answer;
        this.difficulty = difficulty;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        lastSeen = dtf.format(now);

    }



    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getLastSeen() {
        return lastSeen;
    }

    public void setLastSeen(String lastSeen) {
        this.lastSeen = lastSeen;
    }

    void printCard(Card card){

    }
    void editCard(Card card){
        this.question = card.question;
        this.answer = card.answer;
        this.difficulty = card.difficulty;
    }
}
