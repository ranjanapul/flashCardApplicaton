package com.example.oop;

import java.net.Socket;
import java.util.HashSet;
import java.io.*;
import java.util.*;

public class User {
    private String name;
    private String pwd;
    private HashSet<Category> categoryList = new HashSet<Category>();


    //Creating user
    User(String name, String pwd){
        this.name = name;
        this.pwd = pwd;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public HashSet<Category> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(HashSet<Category> categoryList) {
        this.categoryList = categoryList;
    }
//    public User getUserByName(String userName){
//
//    }

    public boolean isUserValid() throws IOException {
//        File file = new File("D:\\OOP\\Project\\src\\main\\java\\com\\example\\oop\\users.txt");
//        Scanner sc = null;
//        try {
//            sc = new Scanner(file);
//        }
//        catch(Exception e){
//            System.out.println("File not found");
//        }
//
//        while(sc.hasNextLine()){
//           String st = sc.nextLine();
//           String arr[] = st.split(",");
//           if(arr[1].equals(this.name) && arr[2].equals(this.pwd))
//               return true;
//        }
//        return false;
        //Send to server
        Socket client = new Socket("localhost",1000);
        //To send data
        DataOutputStream dos = new DataOutputStream(client.getOutputStream());
        //To recieve data
        BufferedReader br = new BufferedReader(new InputStreamReader(client.getInputStream()));

        String msg = "auth/" + name + "/" + pwd +"\n";
        dos.writeBytes(msg);
        String response = br.readLine();
        if(response.equals("ok")){
            return true;
        }
        return false;

    }

    public void addCategory(String name) throws IOException {
//        categoryList.add(new Category(id, name, cardList));
//        File file = new File("D:\\OOP\\Project\\src\\main\\java\\com\\example\\oop\\categories.txt");
//        Scanner sc = null;
//        try {
//            sc = new Scanner(file);
//        }
//        catch(Exception e){
//            System.out.println("File not found");
//        }
//        while(sc.hasNextLine()){
//            String st = sc.nextLine();
//            String arr[] = st.split(",");
//            if(arr[0].equals(this.name))
//                categoryEdit(st,st+","+name);
//
//
//        }
        categoryList.add(new Category(name));
        File file = new File(this.name + ".txt");
        if(file.createNewFile()){
            FileWriter writer1 = new FileWriter(this.name + ".txt");
            writer1.write(name + "\n\nCategory,"+name);
            writer1.close();
        }
        else {
            Scanner scanner = new Scanner(file);
            String document = "";
            while (scanner.hasNextLine()){
                String content = scanner.nextLine();
                document = document + content + "\n";
            }
            document = document + "Category," + name + "\n";
            FileWriter writer = new FileWriter(this.name+".txt");
            writer.write(document);
            writer.close();
        }

    }

    public void deleteCategory(Category category){
        categoryList.remove(category);
        File file = new File(name + ".txt");
//        Scanner sc = null;
//        try {
//            sc = new Scanner(file);
//        }
//        catch(Exception e){
//            System.out.println("File not found");
//        }
//        while(sc.hasNextLine()) {
//            String st = sc.nextLine();
//            String arr[] = st.split(",");
//            String s = "";
//            for(int i=0;i< arr.length;i++){
//                if(arr[i].equals(category.getName()))
//                    continue;
//                else if(i == arr.length-1)
//                    s+=arr[i];
//                else
//                    s+=arr[i]+",";
//            }
//            categoryEdit(st,s);
//        }
        try {
            Scanner scanner = new Scanner(file);
            scanner.nextLine();
            String document = "";
            while (scanner.hasNextLine()){
                String content = scanner.nextLine();
                if(content.split(",")[0].equals("Category")){
                    if(content.split(",")[1].equals(category.getName())){
                        while (scanner.nextLine().split(",")[0].equals("Category")){
                            continue;
                        }
                    }
                }
                document = document + content + "\n";
            }
            FileWriter writer = new FileWriter(name+".txt");
            writer.write(document);
            writer.close();
        }
        catch (FileNotFoundException e){
            return;
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //Required?
//    public void editCategory(Category category) throws IOException {
//        //Category x;
//        for(Category c : categoryList){
//            if((c.getName()).equals(category.getName())) {
//                //x = c;
//                deleteCategory(c);
//                addCategory(category.getName(),category.getCardList());
//            }
//        }
//        //categoryList.remove(x);
//        //categoryList.add(category);
//    }

//    public static void main(String[] args){
//        User obj = new User("a","1","abc");
//        try {
//            System.out.println(obj.isUserValid("1", "abc"));
//        }
//        catch(Exception e){
//            System.out.println("No file");
//        }
//    }
    public static void categoryEdit(String oldLine, String newLine){
        String filePath = "D:\\OOP\\Project\\src\\main\\java\\com\\example\\oop\\users.txt";
        Scanner sc = null;
        try {
            sc = new Scanner(new File(filePath));
        }
        catch(Exception e){
            System.out.println("File not found");
        }
        StringBuffer buffer = new StringBuffer();
        while (sc.hasNextLine()) {
            buffer.append(sc.nextLine()+System.lineSeparator());
        }
        String fileContents = buffer.toString();
        System.out.println("Contents of the file: "+fileContents);
        //closing the Scanner object
        sc.close();
        //Replacing the old line with new line
        fileContents = fileContents.replaceAll(oldLine, newLine);
        //instantiating the FileWriter class
        FileWriter writer = null;
        try {
            writer = new FileWriter(filePath);
        }
        catch(Exception e){
            System.out.println("File not found");
        }
        System.out.println("");
        System.out.println("new data: "+fileContents);
        try {
            writer.append(fileContents);
            writer.flush();
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
}
