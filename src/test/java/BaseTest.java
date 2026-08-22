import com.github.javafaker.Faker;
import demo_services.AuthService;
import demo_services.UserService;
import models.User;
import org.junit.jupiter.api.BeforeEach;

import java.util.Locale;

public class BaseTest {
    protected AuthService authService = new AuthService();
    protected UserService userService = new UserService();

    protected User testUser;

    protected Faker defaultFaker;

    @BeforeEach
    void setUp() {
        defaultFaker = new Faker();
        Faker ruFaker = new Faker(Locale.of("ru"));

        String username = new StringBuilder()
                .append(defaultFaker.name().username())
                .append(defaultFaker.number().randomDigit())
                .toString();

        testUser = new User(
                username,
                ruFaker.animal().name(),
                ruFaker.name().firstName(),
                ruFaker.name().lastName(),
                String.valueOf(ruFaker.date().birthday(0, 100)),
                defaultFaker.internet().emailAddress()
        );
    }
}
