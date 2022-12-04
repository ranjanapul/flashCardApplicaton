package com.example.oop;

import java.util.*;
import java.io.*;

public class Category {
    private String name;
    private HashSet<Card> cardList;


    Category(String name){
        this.name = name;
        cardList = new HashSet<Card>();
    }
    Category(String name, HashSet<Card> cardList){
        this.name=name;
        this.cardList = cardList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HashSet<Card> getCardList() {
        return cardList;
    }

    public void setCardList(HashSet<Card> cardList) {
        this.cardList = cardList;
    }
    public void addCardInitialise(Card card, User user){
        cardList.add(card);

    }
    public void addCard(Card card, User user) throws IOException {
        cardList.add(card);
        File file = new File(user.getName()+".txt");
        if(!file.exists()){
            return;
        }
        String cardType = card.cardType;
        int type = 0;
        if(cardType.equals("True/False")){
            type = 1;
        }
        else if(cardType.equals("Fill in the blanks")){
            type = 2;
        }
        else {
            type = 3;
        }
        Scanner scanner = new Scanner(file);
        String document = "";
        while (scanner.hasNextLine()){
            String content = scanner.nextLine();
            String[] contents = content.split(",");
            if(contents[0].equals("Category")){
                if(contents[1].equals(this.name)){
                    document = document + content + "\n";
                    //complete
                    document = document + "Card," + type + "," + card.getQuestion() + "," + card.getAnswer()+"," + card.getDifficulty() +"\n";
                    continue;
                }
            }
            document = document + content + "\n";
        }

        FileWriter writer = new FileWriter(user.getName()+".txt");
        writer.write(document);
        writer.close();

    }
    public void deleteCard(Card card, User user) throws IOException {
        cardList.remove(card);
        File file = new File(user.getName()+".txt");
        if(!file.exists()){
            return;
        }
        Scanner scanner = new Scanner(file);
        String document = "";
        boolean categoryAlive = false;
        while (scanner.hasNextLine()){
            String content = scanner.nextLine();
            String[] contents = content.split(",");
            if(contents[0].equals("Category")){
                if(contents[1].equals(this.name)){
                    categoryAlive = true;
                }
            }
            if(categoryAlive){
                if(contents[0].equals("Card")){
                    if (contents[1].equals(card.getQuestion())){
                        continue;
                    }
                }
            }
            document = document + content + "\n";
        }

        FileWriter writer = new FileWriter(user.getName()+".txt");
        writer.write(document);
        writer.close();
    }
    public void editCard(Card card){
        for(Card c : cardList){
            if((c.getQuestion()).equals(card.getQuestion())){
                cardList.remove(c);
                cardList.add(card);
            }
        }
    }

}

