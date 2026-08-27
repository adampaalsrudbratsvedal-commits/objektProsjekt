package casinoProject.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserTest {

    @Test
    void getUsername_shouldReturnCorrectUsername() {
        User user = new User("kermit", 100);
        assertEquals("kermit", user.getUsername());
    }

    @Test
    void getBalance_shouldReturnInitialBalance() {
        User user = new User("kermit", 250.0);
        assertEquals(250.0, user.getBalance());
    }

    @Test
    void changeBalance_shouldAddMoney() {
        User user = new User("test", 100);
        user.changeBalance(50);
        assertEquals(150, user.getBalance());
    }

    @Test
    void changeBalance_shouldSubtractMoney() {
        User user = new User("test", 100);
        user.changeBalance(-40);
        assertEquals(60, user.getBalance());
    }

    @Test
    void changeBalance_shouldThrowExceptionWhenOverdrawn() {
        User user = new User("test", 20);
        assertThrows(IllegalArgumentException.class, () -> user.changeBalance(-30));
    }
}
