package casinoProject.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BlackjackTest {

    private User user;
    private Blackjack game;

    @BeforeEach
    void setup() {
        user = new User("test", 100);
        game = new Blackjack(user);
    }

    @Test
    void startRound_shouldDeductBetFromBalance() {
        game.startRound(50);
        double balance = user.getBalance();
        assertTrue(balance == 50 || balance == 175);
    }

    @Test
    void playerHit_shouldAddCardToPlayerHand() {
        game.startRound(10);
        int initialSize = game.getPlayerHand().size();
        game.playerHit();
        assertEquals(initialSize + 1, game.getPlayerHand().size());
    }

    @Test
    void dealerTurn_shouldStopAt17OrMore() {
        game.startRound(10);
        game.dealerTurn();
        int value = game.calculateHandValue(game.getDealerHand());
        assertTrue(value >= 17);
    }

    @Test
    void determineWinner_shouldReturnPlayerWhenDealerBusts() {
        game.startRound(10);
        // Fake dealer hand so it busts
        game.getDealerHand().clear();
        game.getDealerHand().add("K");
        game.getDealerHand().add("Q");
        game.getDealerHand().add("5"); // 25 = bust
        assertEquals("player", game.determineWinner());
    }

}
