package model.domain;

public class User {
    /* Instance Fields */
    private int id;
    private final String username;
    private final String password;
    private final String name;
    private final String email;
    private final Date birthday;
    private int score;
    private boolean isAdmin = false;

    /* Constructor */
    public User(int id, String username, String password, String name, String email, Date birthday, boolean isAdmin) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.name = name;
        this.email = email;
        this.birthday = birthday;
        this.isAdmin = isAdmin;
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

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public Date getBirthday() {
        return birthday;
    }

    public int getScore() {
        return score;
    }
}
