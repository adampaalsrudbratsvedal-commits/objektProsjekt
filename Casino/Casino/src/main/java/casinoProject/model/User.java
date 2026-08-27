package casinoProject.model;

public class User {
    
    private final String username;
    private double balance;

    public User(String username, double balance){
        this.username = username;
        this.balance = balance;
    }

    public String getUsername() {
        return username;
    }

    public double getBalance() {
        return balance;
    }

    public void changeBalance(double amount){
        if (getBalance()+amount < 0){
            throw new IllegalArgumentException("Du har ikke nok penger");
        }
        balance += amount;
    }
}
