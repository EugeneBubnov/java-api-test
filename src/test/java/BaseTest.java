import com.github.javafaker.Faker;
import demo_services.AuthService;
import demo_services.UserService;
import models.User;
import org.junit.jupiter.api.BeforeEach;

import java.util.Locale;

public class BaseTest {
    protected AuthService authService = new AuthService();
    protected UserService userService = new UserService();

    protected User physical;

    @BeforeEach
    void setUp() {
        Faker faker = new Faker(Locale.of("ru"));
        physical = new User(
                faker.name().username(),
                faker.animal().name(),
                faker.name().firstName(),
                faker.name().lastName(),
                String.valueOf(faker.date().birthday(0, 100)),
                faker.internet().emailAddress()
        );
    }
}
