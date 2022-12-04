package com.example.oop;
import java.io.*;
import java.net.Socket;

public class Register {
    String username;
    String password;
    static String userId = "0";

    Register(String username, String password){
        this.username = username;
        this.password = password;
        userId = Integer.toString((Integer.parseInt(userId) + 1));
        User user = new User(username, password);
        addUser(user);
    }
    void addUser(User user){


    }
    public static boolean addUser(String username, String password) throws IOException {
        Socket client = new Socket("localhost",1000);
        //To send data
        DataOutputStream dos = new DataOutputStream(client.getOutputStream());
        //To recieve data
        BufferedReader br = new BufferedReader(new InputStreamReader(client.getInputStream()));

        String msg = "register/" + username + "/" + password +"\n";
        dos.writeBytes(msg);
        String response = br.readLine();
        if(response.equals("ok")){
            return true;
        }

        return false;
    }
}
