package com.example.toobagame.Model;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class User  {

    private static User INSTANCE;

    public User() {

    }
    public static User getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new User();
        }
        return INSTANCE;
    }

    private HashMap<String, Object> property = null;
    private String income = null;
    private String id;
    public String name;
    private String email;
    private String password;
    private String gender;
    private String age = null;
    private int balance = 0;
    private String experience = null;

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("id", id);
        result.put("name", name);
        result.put("email", email);
        result.put("password", password);
        result.put("age", age);
        result.put("gender", gender);
        result.put("balance", balance);
        result.put("experince", experience);
        result.put("property", property);
        return result;
    }

    public void User() {}

    public HashMap<String, Object> toMapProperty(){
        return property = new HashMap<>();
    }

    public User(String id, String name, String email, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public User(HashMap<String, Object> property, String income, String id, String name, String email,
                String password, String gender, String age, int balance, String experience) {
        this.property = property;
        this.income = income;
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.gender = gender;
        this.age = age;
        this.balance = balance;
        this.experience = experience;
    }

    public User(String id, String name, String email, String password, String gender, String age, int balance, String experience) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.gender = gender;
        this.age = age;
        this.balance = balance;
        this.experience = experience;
    }

    public void addBuild(String name){
        property.put(name, name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getIncome() {
        return income;
    }

    public void setIncome(String income) {
        this.income = income;
    }

    public Map<String, Object> getProperty() {
        return property;
    }

    public void setProperty(HashMap<String, Object> property) {
        this.property = property;
    }
}
