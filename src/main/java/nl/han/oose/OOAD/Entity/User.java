package nl.han.oose.OOAD.Entity;

public class User {
    private String username;
    private String password;
    private int credits;

    public User(String username) {
        this.username = username;
        this.password = password;
        this.credits = 1000; // New users start with 1000 credits
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public int getCredits() {
        return credits;
    }

    public void deductCredits(int amount) {
        if (credits >= amount) {
            credits -= amount;
        }
    }

    public void addCredits(int boughtCredits) {
        this.credits += boughtCredits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }
}