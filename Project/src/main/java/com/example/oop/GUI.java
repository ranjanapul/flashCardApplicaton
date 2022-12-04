package com.example.oop;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicReference;

import static com.example.oop.Register.addUser;

public class GUI extends Application {
    Stage window;
    Scene scene1;
    Scene scene2;
    Scene scene3;
    Scene addCategory;
    Scene showCards;
    Scene addCards;
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        window = primaryStage;
        Label username = new Label("Username: ");
        Label password = new Label("Password:  ");
        Label signup = new Label("New User? ");
        TextField nameInput = new TextField();
        TextField passwordInput = new TextField();
        passwordInput.setPromptText("password");
        Button loginButton = new Button(("Log in"));
        Button registerButton = new Button("Sign up");


        HBox user = new HBox();
        user.setAlignment(Pos.CENTER);
        user.getChildren().addAll(username, nameInput);

        HBox buttons = new HBox();
        buttons.setAlignment(Pos.CENTER);
        buttons.getChildren().addAll(signup, registerButton);


        HBox pass = new HBox();
        pass.setAlignment(Pos.CENTER);
        pass.getChildren().addAll(password, passwordInput);

        VBox vb = new VBox(10);
        vb.setAlignment(Pos.CENTER);
        vb.getChildren().addAll(user, pass, loginButton, buttons);


        //Scene 2:
//        String userName = nameInput.getText();
//        System.out.println("Hey"+userName);
//        Label category = new Label(userName);
//        category.setFont(new Font("Arial", 24));
//        category.setMaxWidth(Double.MAX_VALUE);
//        category.setAlignment(Pos.TOP_CENTER);
//        Scene scene2 = new Scene(category,300,200);


        loginButton.setOnAction(e -> {

            User usr = new User(nameInput.getText(), passwordInput.getText());

            try {
                if(usr.isUserValid()){
                    String userName = nameInput.getText();
                    Label category = new Label("Categories");
                    category.setFont(new Font("Arial", 24));
                    category.setMaxWidth(Double.MAX_VALUE);
                    category.setAlignment(Pos.TOP_CENTER);

                    //call function(User user)

                    File file = new File(userName+".txt");
                    Scanner sc = null;
                    try {
                        sc = new Scanner(file);
                    }
                    catch(Exception ex){
                        System.out.println("File not found");
                        try {
                            if(file.createNewFile()){
                                FileWriter writer = new FileWriter(userName+".txt");
                                writer.write(userName+"\n\n");
                                writer.close();
                                sc = new Scanner(file);
                            }
                        } catch (IOException exc) {
                            System.out.println("Hello");
                            exc.printStackTrace();
                        }
                    }

                    sc.nextLine();
                    System.out.println("Hello1");

                    ArrayList<Category> categories = new ArrayList<Category>();//if size = 0, then no category.
                    HashSet<Category> categoryHashSet = new HashSet<Category>();
                    usr.setCategoryList(categoryHashSet);
                    Category category1 = new Category("");
                    while(sc.hasNextLine()) {
                        //For first line
                        String st = sc.nextLine();

                        String contents[] = st.split(",");
                        //Continue here
                        if (contents[0].equals("Category")) {
                            //                    String[] categories = new String[arr.length-1];
                            //                    for(int i=1;i<arr.length;i++){
                            //                        categories[i-1] = arr[i];
                            category1 = new Category(contents[1]);
                            categories.add(category1);
                            System.out.println("Added");
                            categoryHashSet.add(category1);
                        }
                        else if (contents[0].equals("Card")) {
                            int cardType = Integer.parseInt(contents[1]);
                            Card card;
                            switch (cardType) {
                                case 1:
                                    //Card type 1
                                    card = new TrueFalseCard(contents[2], contents[3], contents[4]);
                                    category1.addCardInitialise(card, usr);
                                    break;
                                case 2:
                                    //Card type 2
                                    card = new FillInTheBlanksCard(contents[2], contents[3], contents[4]);
                                    category1.addCardInitialise(card, usr);
                                    break;
                                case 3:
                                    //Card type 3
                                    card = new SubjectiveCard(contents[2], contents[3], contents[4]);
                                    category1.addCardInitialise(card, usr);
                                    break;
                            }
                        }
                        System.out.println("1");

                        if (categories.size() > 0) {
                            VBox vbox = new VBox(10);
                            vbox.setAlignment(Pos.CENTER);
                            vbox.getChildren().add(category);
                            int x = 0;
                            for (int i = 1; i <= categories.size() / 5 + 1; i++) {
                                HBox hbox = new HBox(10);
                                hbox.setAlignment(Pos.CENTER);
                                for (int j = 1; j <= Math.min(5, categories.size() - 5 * (i - 1)); j++) {
                                    Label name = new Label(categories.get(x).getName());
                                    Category c = categories.get(x++);
                                    Button openButton = new Button("Open");
                                    openButton.setOnAction(ev -> {
                                        viewCards(usr, c);


                                    });
                                    //To do
//                                    openButton.setOnAction(a -> {
//                                        openCardView(user, categories.get(x - 1).getName());
//                                    });
                                    Button deleteButton = new Button("Delete");
                                    openButton.setMinHeight(20);
                                    VBox vbo = new VBox(10);
                                    vbo.setAlignment(Pos.CENTER);
                                    vbo.getChildren().addAll(name, openButton, deleteButton);
                                    hbox.getChildren().addAll(vbo);
                                }
                                vbox.getChildren().addAll(hbox);
                            }
                            Button add = new Button("+");
                            add.setOnAction(ev ->{
                                createCategory(usr);
                            });
                            Button activityMap = new Button("Activity Map");
                            Button publicCards = new Button("View Public Cards");
                            HBox hb = new HBox(10);
                            hb.setAlignment(Pos.CENTER);
                            hb.getChildren().addAll(activityMap,publicCards);
                            vbox.getChildren().addAll(add,hb);
                            scene2 = new Scene(vbox, 300, 200);
                            window.setScene(scene2);

                        }
                        else{
                            createCategory(usr);
                        }

                    }




                }
                else{
                    Alerts.display("Invalid", "Invalid username or password");

                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });


        Scene scene1 = new Scene(vb, 300, 200);
        window.setScene(scene1);
        window.show();






        Label usernameReg = new Label("Username: ");
        Label passwordReg = new Label("Password:  ");
        TextField nameInputReg = new TextField();
        TextField passwordInputReg = new TextField();
        Button createUserButton = new Button(("Create User"));

        HBox userReg = new HBox();
        userReg.setAlignment(Pos.CENTER);
        userReg.getChildren().addAll(usernameReg, nameInputReg);

        HBox passReg = new HBox();
        passReg.setAlignment(Pos.CENTER);
        passReg.getChildren().addAll(passwordReg, passwordInputReg);


        VBox vbReg = new VBox(10);
        vbReg.setAlignment(Pos.CENTER);
        vbReg.getChildren().addAll(userReg, passReg, createUserButton);

        scene3 = new Scene(vbReg, 300, 200);

        registerButton.setOnAction(e ->{
            window.setScene(scene3);
        });

        createUserButton.setOnAction(e -> {
            String name = nameInputReg.getText();
            String passw = passwordInputReg.getText();
            boolean isRegistered = false;
            try {
                isRegistered = addUser(name, passw);
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            if(isRegistered){
                Alerts.display("Success","User created successfully");
                window.setScene(scene1);
            }
            else {
                Alerts.display("Error", "User already exists");
            }


        });




    }
    void openCardView(User user, String categoryName){

    }
    void createCategory(User usr){
        Label create = new Label("Create a new Category");
        create.setFont(new Font("Arial", 24));
        Label categoryName = new Label("Name:");
        TextField categoryInput = new TextField();
        categoryInput.setPromptText("Enter category name");


        Button okButton = new Button("Ok");
        okButton.setOnAction(e -> {
            try {
                usr.addCategory(categoryInput.getText());
            } catch (IOException ex) {
                System.out.println("Cannot add category");
            }
            //Update here
            HashSet<Category> categoryHashSet= usr.getCategoryList();
            ArrayList<Category> categories = new ArrayList<Category>();
            for (Category category1: categoryHashSet){
                categories.add(category1);
            }
            Label category = new Label("Categories");
            category.setFont(new Font("Arial", 24));
            category.setMaxWidth(Double.MAX_VALUE);
            category.setAlignment(Pos.TOP_CENTER);
            if (categories.size() > 0) {
                VBox vbox = new VBox(10);
                vbox.setAlignment(Pos.CENTER);
                vbox.getChildren().add(category);
                int x = 0;
                for (int i = 1; i <= categories.size() / 5 + 1; i++) {
                    HBox hbox = new HBox(10);
                    hbox.setAlignment(Pos.CENTER);
                    for (int j = 1; j <= Math.min(5, categories.size() - 5 * (i - 1)); j++) {
                        Label name = new Label(categories.get(x).getName());
                        Category c = categories.get(x++);
                        Button openButton = new Button("Open");
                        openButton.setOnAction(ev -> {
                            viewCards(usr,c);

                        });
                        //To do
//                                    openButton.setOnAction(a -> {
//                                        openCardView(user, categories.get(x - 1).getName());
//                                    });
                        Button deleteButton = new Button("Delete");
                        openButton.setMinHeight(20);
                        VBox vbo = new VBox(10);
                        vbo.setAlignment(Pos.CENTER);
                        vbo.getChildren().addAll(name, openButton, deleteButton);
                        hbox.getChildren().addAll(vbo);
                    }
                    vbox.getChildren().addAll(hbox);
                }
                Button add = new Button("+");
                add.setOnAction(ev ->{
                    createCategory(usr);
                });
                Button activityMap = new Button("Activity Map");
                Button publicCards = new Button("View Public Cards");
                HBox hb = new HBox(10);
                hb.setAlignment(Pos.CENTER);
                hb.getChildren().addAll(activityMap,publicCards);
                vbox.getChildren().addAll(add,hb);
                scene2 = new Scene(vbox, 300, 200);
                window.setScene(scene2);

            }
//            window.setScene(scene2);
        });

        HBox cat = new HBox(10);
        cat.setAlignment(Pos.CENTER);
        cat.getChildren().addAll(create);

        HBox cat2 = new HBox(10);
        cat2.setAlignment(Pos.CENTER);
        cat2.getChildren().addAll(categoryName,categoryInput);

        HBox cat3 = new HBox(10);
        cat3.setAlignment(Pos.CENTER);
        cat3.getChildren().addAll(okButton);

        VBox vb = new VBox(10);
        vb.setAlignment(Pos.CENTER);
        vb.getChildren().addAll(cat,cat2,cat3);

        addCategory = new Scene(vb, 300, 200);
        //usr.addCategory();
        window.setScene(addCategory);



    }
    void viewCards(User usr, Category c){
        HashSet<Card> cards = c.getCardList();
        VBox vb = new VBox(10);
        vb.setAlignment(Pos.CENTER);
//        VBox vb1 = new VBox(10);
        for(Card card: cards){
            Label question = new Label("Question: "+card.getQuestion());
            Label type = new Label(card.cardType);

            Button showAns = new Button("Show Answer");
            Button makePublic = new Button("Make Card Public");
            Button delete = new Button("Delete Card");

            HBox hb = new HBox(10);
            hb.setAlignment(Pos.CENTER);
            hb.getChildren().addAll(makePublic,delete);

            delete.setOnAction(e ->{
                deletionOfCard(usr,c,card);

            });

            showAns.setOnAction(e -> {
                Alerts.display("Answer",card.answer);
            });
            VBox vb1 = new VBox(10);
            vb1.setAlignment(Pos.CENTER);
            vb1.getChildren().addAll(type,question,showAns,hb);
            String cssLayout = "-fx-border-color: red;\n" +
                    "-fx-border-insets: 5;\n" +
                    "-fx-border-width: 3;\n" +
                    "-fx-border-style: dashed;\n";

            vb1.setStyle(cssLayout);
            vb.getChildren().addAll(vb1);
        }
//        Button makePublic = new Button("Make Card Public");
//        Button showAns = new Button("Show Answer");
        Button backToCat = new Button("Back to categories");
        Button addCard = new Button("+");
        backToCat.setOnAction(e -> {
            window.setScene(scene2);
        });
        addCard.setOnAction(e ->{
            addACard(usr,c);
        });

        HBox hb = new HBox(10);
        hb.setAlignment(Pos.CENTER);
        hb.getChildren().addAll(backToCat,addCard);
        vb.getChildren().addAll(hb);

        showCards = new Scene(vb,300,200);
        window.setScene(showCards);


    }
    void addACard(User usr, Category c){
        Label addCard = new Label("Add Card");
        addCard.setFont(new Font("Arial", 24));
        addCard.setMaxWidth(Double.MAX_VALUE);
        addCard.setAlignment(Pos.TOP_CENTER);

        Label questionLabel = new Label("Question: ");
        TextField questionInput = new TextField();

        HBox question = new HBox(10);
        question.setAlignment(Pos.CENTER);
        question.getChildren().addAll(questionLabel,questionInput);

        Label answerLabel = new Label("Answer: ");
        TextField answerInput = new TextField();

        HBox answer = new HBox(10);
        answer.setAlignment(Pos.CENTER);
        answer.getChildren().addAll(answerLabel,answerInput);

        Label difficultyLabel = new Label("Difficulty: ");
        TextField difficultyInput = new TextField();

        HBox difficulty = new HBox(10);
        difficulty.setAlignment(Pos.CENTER);
        difficulty.getChildren().addAll(difficultyLabel,difficultyInput);

        Label cardType = new Label("Specify Card Type: ");
        ComboBox<String> comboBox = new ComboBox<>();
        comboBox.getItems().addAll("True/False", "Fill in the blanks", "Subjective Question");

        HBox type = new HBox(10);
        type.setAlignment(Pos.CENTER);
        type.getChildren().addAll(cardType,comboBox);

        Button createCard = new Button("Create");
        createCard.setOnAction(e ->{
            String ctype = comboBox.getValue();
            Card car;
            if(ctype.equals("True/False")){
                car = new TrueFalseCard(questionInput.getText(),answerInput.getText(), difficultyInput.getText());
            }
            else if(ctype.equals("Fill in the blanks")){
                car = new FillInTheBlanksCard(questionInput.getText(),answerInput.getText(), difficultyInput.getText());
            }
            else{
                car =  car = new SubjectiveCard(questionInput.getText(),answerInput.getText(), difficultyInput.getText());
            }
            additionOfCard(usr,c,car);
        });

        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(question,answer,type,difficulty,createCard);

        addCards = new Scene(layout,300,200);
        window.setScene(addCards);






    }
    void additionOfCard(User usr, Category category, Card card) {
        try {
            category.addCard(card, usr);
        }
        catch(Exception e){
            System.out.println("Cannot add card");
        }
        viewCards(usr,category);
    }

    void deletionOfCard(User usr, Category category, Card card){
        try{
            category.deleteCard(card,usr);
        }
        catch(Exception e){
            System.out.println("Cannot delete card");
        }
        viewCards(usr,category);
    }

}
