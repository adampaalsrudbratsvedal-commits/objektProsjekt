package casinoProject.controller;

import casinoProject.data.PropertiesData;
import casinoProject.model.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class MainMenuController{

    @FXML
    private TextField usernameField, amountField;

    @FXML
    private Label usernameLabel, balanceLabel, messageLabel;

    @FXML
    private Button startBlackjackButton;

    private PropertiesData propertiesData = new PropertiesData();
    private User currentUser;
    
    @FXML
    private void handleLogin(){
        String username = usernameField.getText().trim();
        if (username.isEmpty()) {
            messageLabel.setText("Du må skrive inn et brukernavn.");
            return;
        }
        if (!propertiesData.getUser(username)){
            propertiesData.addUser(username);
        }

        double balance = propertiesData.getBalance(username);
        currentUser = new User(username, balance);

        usernameLabel.setText(username);
        balanceLabel.setText(balance+"kr");
        startBlackjackButton.setDisable(false);
        messageLabel.setText("Innlogging vellykket!");

    }

    @FXML
    private void handleDeposit(){
        if (currentUser == null){
            messageLabel.setText("Du må logge inn først");
            return;
        }

        try{
            double amount = Double.parseDouble(amountField.getText());
            if (amount <= 0){
                throw new NumberFormatException("Du må legge inn et positivt tall");
            }
            currentUser.changeBalance(amount);
            updateUserBalance();
            messageLabel.setText("Innskudd fullført");
        }   catch (NumberFormatException e) {
            messageLabel.setText("Ugyldig beløp");
        }   catch (IllegalArgumentException e){
            messageLabel.setText("Ugyldig beløp: " + e.getMessage());
        }
    }

    @FXML
    private void handleWithdraw() {
        if (currentUser == null){
            messageLabel.setText("Du må logge inn først");
            return;
        }
        try{
            double amount = Double.parseDouble(amountField.getText());
            if (amount <= 0){
                throw new NumberFormatException();
            }
            currentUser.changeBalance(-amount);
            updateUserBalance();
            messageLabel.setText("Uttak fullført!");
        }   catch (NumberFormatException e){
            messageLabel.setText("Ugyldig beløp");
        }   catch (IllegalArgumentException e){
            messageLabel.setText("Ugyldig beløp:" + e.getMessage());
        }
    }



    private void updateUserBalance() {
        balanceLabel.setText(currentUser.getBalance() + " kr");
        propertiesData.updateFromUser(currentUser);
    }

    @FXML
    private void handleStartBlackjack() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/exampleproject/casinoProject/Blackjack.fxml"));
            Parent blackjackRoot = loader.load();

            BlackjackController controller = loader.getController();
            controller.initGame(currentUser, propertiesData);

            Stage stage = (Stage) startBlackjackButton.getScene().getWindow();
            Scene blackjackScene = new Scene(blackjackRoot);

            blackjackScene.getStylesheets().add(getClass().getResource("/exampleproject/casinoProject/styles/Blackjack.css").toExternalForm());
            
            stage.setScene(blackjackScene);
            stage.setFullScreen(true); 
        } catch (Exception e) {
            e.printStackTrace();
            messageLabel.setText("Feil ved lasting av Blackjack");
        }
    }
}
