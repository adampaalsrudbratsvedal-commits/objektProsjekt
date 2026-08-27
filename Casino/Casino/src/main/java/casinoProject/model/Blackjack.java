package casinoProject.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Blackjack implements Game {
    
    private List<String> deck;
    private List<String> playerHand;
    private List<String> dealerHand;
    private final User user;

    
    public Blackjack(User user){
        this.user = user;
    }

    public String startRound(double bet) {
        resetGame();
        user.changeBalance(-bet);

        if ( isBlackjack(playerHand)) {
            String result = isBlackjack(dealerHand) ? "draw" : "player";
            handleBlackjack(result, bet);
            return result;
        }
        return null;
    }

    public void handleBlackjack(String result, double bet){
        switch (result) {
            case "player" -> user.changeBalance(bet * 2.5);
            case "draw"  -> user.changeBalance(bet); 
        }

    }

    public String endRound(double bet){
        dealerTurn();
        String result = determineWinner();

        switch (result) {
            case "player" -> user.changeBalance(bet * 2);
            case "draw" -> user.changeBalance(bet);
        }

        return result;
    }

    private void resetGame(){
        this.deck = generateDeck();
        this.playerHand = new ArrayList<>();
        this.dealerHand = new ArrayList<>();

        playerHand.add(drawCard());
        playerHand.add(drawCard());

        dealerHand.add(drawCard());
        dealerHand.add(drawCard());
    }

    private List<String> generateDeck(){
        List<String> newDeck = new ArrayList<>();
        String[] ranks = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A"};
        for (String rank : ranks) {
            for (int i = 0; i < 4; i++) {
                newDeck.add(rank);
            }
        }
        Collections.shuffle(newDeck);

        return newDeck;
    }

    private String drawCard() {
        return deck.remove(0);
    }

    public void playerHit() {
        playerHand.add(drawCard());
    }

    public void dealerTurn() {
        while (calculateHandValue(dealerHand) < 17){
            dealerHand.add(drawCard());
        }
    }

    public int calculateHandValue(List<String> hand) {
        int value = 0;
        int aceCount = 0;
    
        for (String card : hand) {
            switch (card) {
                case "A" -> {
                    value += 11;
                    aceCount++;
                }
                case "K", "Q", "J" -> value += 10;
                default -> value += Integer.parseInt(card);
            }
        }
        
        while (value > 21 && aceCount > 0) {
            value -= 10;
            aceCount--;
        }
    
        return value;
    }
    

    public boolean isPlayerBusted(){
       return calculateHandValue(playerHand) > 21; 
    }

    public boolean isDealerBusted(){
        return calculateHandValue(dealerHand) > 21;
    }

    public String determineWinner(){
        int playerValue = calculateHandValue(playerHand);
        int dealerValue = calculateHandValue(dealerHand);

        if (isPlayerBusted()){
            return "dealer";
        }
        if (isDealerBusted()){
            return "player";
        }
        if (playerValue > dealerValue){
            return "player";
        }
        if (playerValue < dealerValue){
            return "dealer";
        }
        return "draw";
    }

    public List<String> getPlayerHand() {
        return playerHand;
    }

    public List<String> getDealerHand() {
        return dealerHand;
    }

    public User getUser() {
        return user;
    }

    public boolean isBlackjack(List<String> hand) {
        int value = calculateHandValue(hand);
        if (hand.size() == 2 && value == 21){
            return true;
        }
        else{
            return false;
        }
    }

}
