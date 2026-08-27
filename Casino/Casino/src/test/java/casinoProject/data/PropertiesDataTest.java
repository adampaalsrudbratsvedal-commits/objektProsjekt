package casinoProject.data;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class PropertiesDataTest {

    private PropertiesData data;
    private final String testUser = "unittest_user";

    @BeforeEach
    void setup() {
        data = new PropertiesData();
        if (data.getUser(testUser)) {
            data.removeUser(testUser); // Rydd opp
        }
    }

    @Test
    void shouldAddAndRetrieveUser() {
        data.addUser(testUser);
        assertTrue(data.getUser(testUser));
        assertEquals(0.0, data.getBalance(testUser)); // forventet startsaldo
    }

    @Test
    void shouldUpdateAndRetrieveBalance() {
        data.addUser(testUser);
        data.updateBalance(testUser, 250.0);
        assertEquals(250.0, data.getBalance(testUser));
    }

    @Test
    void shouldReflectUserInNewInstanceAfterSave() {
        data.addUser(testUser);
        data.updateBalance(testUser, 300.0);

        PropertiesData newData = new PropertiesData();
        assertEquals(300.0, newData.getBalance(testUser));
    }

    @AfterEach
    void cleanup() {
        data.removeUser(testUser); // valgfritt for opprydding
    }
}
