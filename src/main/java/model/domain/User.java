package model.domain;

import java.sql.Timestamp;
import java.util.ArrayList;

public class User {
    /* Instance Fields */
    private int id;
    private String username;
    private String password;
    private String name;
    private String email;
    private Date birthday;
    private int score;
    private boolean isAdmin;
    private ArrayList<Timestamp> logs;
    private ArrayList<String> oldPasswords;

    /* Constructor */
    public User() {

    }

    public User(String username, String password, String name, String email, Date birthday) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.email = email;
        this.birthday = birthday;
    }

    /* Getters And Setters */
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public ArrayList<Timestamp> getLogs() {
        return logs;
    }

    public void setLogs(ArrayList<Timestamp> logs) {
        this.logs = logs;
    }

    public ArrayList<String> getOldPasswords() {
        return oldPasswords;
    }

    public void setOldPasswords(ArrayList<String> oldPasswords) {
        this.oldPasswords = oldPasswords;
    }
}
