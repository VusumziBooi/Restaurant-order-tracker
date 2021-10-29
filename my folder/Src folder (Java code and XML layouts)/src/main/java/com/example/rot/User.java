package com.example.rot;

public class User {
    String Username;
    String Email;
    String Phone_num;
    String Password;

    public User(String username, String email, String phone_num, String password){
        Username = username;
        Email = email;
        Phone_num = phone_num;
        Password = password;
    }

    public String getUsername() {
        return Username;
    }

    public String getEmail() {
        return Email;
    }

    public String getPhone_num() {
        return Phone_num;
    }

    public String getPassword() {
        return Password;
    }
}
