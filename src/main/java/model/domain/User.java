package model.domain;

public class User {
    /* Instance Fields */
    private int id;
    private String username;
    private String password;
    private String name;
    private String email;
    private Date birthday;
    private boolean isAdmin;
    private boolean isBan;

    /* Constructors */
    public User(int id, String username, String password, String name, String email, Date birthday, boolean isAdmin, boolean isBan) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.name = name;
        this.email = email;
        this.birthday = birthday;
        this.isAdmin = isAdmin;
        this.isBan = isBan;
    }

    public User(int id, String username, String name) {
        this.id = id;
        this.username = username;
        this.name = name;
    }

    public User(String username, String password, String name, String email, Date birthday) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.email = email;
        this.birthday = birthday;
    }

    /* Static Methods */
    public static void assertValidPassword(String password) throws Exception {
        String passRegex = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$";
        if (!password.matches(passRegex))
            throw new Exception("Please Choose A strong Password (Containing at least 8 characters including 1 digit\n" +
                    "and 1 Capital Letter)");
    }

    public static void assertValidUsername(String username) throws Exception {
        if (!username.matches("^.{4,}$"))
            throw new Exception("username must include at least 4 characters!");
        if (!username.matches("^\\w+$"))
            throw new Exception("username contains Special Characters! Please remove them and try again");
    }

    /* Getters And Setters */
    public void setBan(boolean ban) {
        isBan = ban;
    }

    public boolean isBan() {
        return isBan;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

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

    public Date getBirthday() {
        return birthday;
    }

    @Override
    public String toString() {
        String table = "%-5s | %-" + name.length() + "s | %-" + username.length() + "s | %-10s | %-30s | %-8s | %-5s\n";
        return String.format(table, "ID", "Name", "Username", "Birthday", "Email Add", "Is Admin", "Is Ban") +
                "‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾\n"
                + String.format(table, id, name, username, birthday, email, isAdmin, isBan);
    }
}
