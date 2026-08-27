package casinoProject.data;

import java.io.*;
import java.nio.file.*;
import java.util.*;

import casinoProject.model.User;


public class PropertiesData {

    private static final Path FILE_PATH = Paths.get("Data/users.properties");
    private Properties props;

    public PropertiesData() {
        props = new Properties();
        loadData();
    }


    private void loadData() {
        try {
            if (!Files.exists(FILE_PATH)) {
                Files.createDirectories(FILE_PATH.getParent());
                Files.createFile(FILE_PATH);
            }
            try (FileReader reader = new FileReader(FILE_PATH.toFile())) {
                props.load(reader);
            }
        } catch (IOException e) {
            System.out.println("Klarte ikke å laste brukerdata: " + e.getMessage());
        }
    }

    private void saveData() {
        try (FileWriter writer = new FileWriter(FILE_PATH.toFile())) {
            props.store(writer, "Brukerdata - brukernavn og saldo");
        } catch (IOException e) {
            System.out.println("Klarte ikke å lagre brukerdata: " + e.getMessage());
        }
    }


    public double getBalance(String username) {
        String value = props.getProperty(username);
        if (value == null) {
            throw new NoSuchElementException("Brukernavn " + username + " finnes ikke.");
        }
        return Double.parseDouble(value);
    }

    public boolean getUser(String username) {
        return props.containsKey(username);
    }

    public void updateBalance(String username, double amount) {
        double current = getBalance(username);
        double newBalance = current + amount;
        if (newBalance < 0) {
            throw new IllegalArgumentException("Saldoen kan ikke bli negativ.");
        }
        props.setProperty(username, String.valueOf(newBalance));
        saveData();
    }

    
    public void addUser(String username) {
        if (props.containsKey(username)) {
            throw new IllegalArgumentException("Brukernavn " + username + " er allerede i bruk.");
        }
        props.setProperty(username, "0.0");
        saveData();
    }

    public void updateFromUser(User user){
        props.setProperty(user.getUsername(), String.valueOf(user.getBalance()));
        saveData();
    }

    public void removeUser(String username) {
        props.remove(username);
        saveData();
    }
    

}
