package casinoProject.controller;

import casinoProject.data.PropertiesData;
import casinoProject.model.Blackjack;
import casinoProject.model.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class BlackjackController {

    @FXML private HBox dealerCards;
    @FXML private HBox playerCards;
    @FXML private TextField betField;
    @FXML private Button startButton;
    @FXML private Button hitButton;
    @FXML private Button standButton;
    @FXML private Button backButton;
    @FXML private Label statusLabel;
    @FXML private Label balanceLabel;

    private Blackjack game;
    private PropertiesData data;

    public void initGame(User user, PropertiesData data) {
        this.game = new Blackjack(user);
        this.data = data;
        updateBalance();
        setButtonsEnabled(false);
        backButton.setDisable(false);
    }    

    @FXML
    private void handleStartRound() {
        try {
            double bet = Double.parseDouble(betField.getText());
            if (bet<=0) {
                throw new IllegalArgumentException("Innats må være større enn null!");
            }

            String result = game.startRound(bet);      
            updateHands(result != null);
            updateBalance();

            if (result != null) {                      
                switch (result) {
                    case "player" -> statusLabel.setText("Blackjack! Du vant!");
                    case "draw"   -> statusLabel.setText("Uavgjort! Begge har blackjack.");
                }
                finishRound();               
                return;
            }
            
            backButton.setDisable(true);
            setButtonsEnabled(true);
            updateHands(false);
            updateBalance();
            statusLabel.setText("Spill i gang! Din tur.");
        } catch (NumberFormatException e) {
            statusLabel.setText("Ugyldig innsats.");
        } catch (IllegalArgumentException e) {
            statusLabel.setText(e.getMessage());
        }
    }

    @FXML
    private void handleHit() {
        game.playerHit();
        updateHands(false);

        if (game.isPlayerBusted()) {
            statusLabel.setText("Du bustet! Dealer vinner.");
            finishRound();
        }
    }

    @FXML
    private void handleStand() {
        String result = game.endRound(getBet());
        updateHands(true);
        switch (result) {
            case "player" -> statusLabel.setText("Du vant!");
            case "dealer" -> statusLabel.setText("Dealer vant.");
            case "draw" -> statusLabel.setText("Uavgjort!");
        }
        finishRound();
    }

    @FXML
    private void handleBackToMenu() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/exampleproject/casinoProject/Main-menu.fxml"));
            Parent root = loader.load();

            Scene menuScene = new Scene(root);

            menuScene.getStylesheets().add(getClass().getResource("/exampleproject/casinoProject/styles/Main-menu.css").toExternalForm());

            Stage stage = (Stage) backButton.getScene().getWindow();
            
            stage.setScene(menuScene);
            stage.setFullScreen(true); 
        } catch (Exception e) {
            e.printStackTrace();
    }
}


private void updateHands(boolean roundOver) {
    dealerCards.getChildren().clear();
    playerCards.getChildren().clear();

    if (!roundOver) {
        dealerCards.getChildren().add(new Label(game.getDealerHand().get(0)));
        dealerCards.getChildren().add(new Label("❓"));
    } else {
        for (String card : game.getDealerHand()) {
            dealerCards.getChildren().add(new Label(" " + card));
        }
    }

    for (String card : game.getPlayerHand()) {
        playerCards.getChildren().add(new Label(" " + card));
    }
}

    private double getBet() {
        try {
            return Double.parseDouble(betField.getText());
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    private void finishRound() {
        setButtonsEnabled(false);
        backButton.setDisable(false);
        data.updateFromUser(game.getUser());
        updateBalance();
    }
    

    private void updateBalance() {
        double saldo = game.getUser().getBalance();
        balanceLabel.setText("Saldo: " + saldo + " kr");
    }

    private void setButtonsEnabled(boolean playing) {
        hitButton.setDisable(!playing);
        standButton.setDisable(!playing);
    }
}
