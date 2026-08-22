import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static io.qameta.allure.Allure.step;
import static org.junit.jupiter.api.Assertions.*;

public class DemoTest extends BaseTest {
    @Test
    @Tag("api")
    @Epic("Demo service")
    @Feature("CRUD")
    @DisplayName("Проверить жизненный цикл пользователя по api")
    void testUserLifecycle() {
        step("Зарегистрироваться под ролью: Новый пользователь", () -> {
            Response registrationResponse = authService.register(testUser);
            assertEquals(201, registrationResponse.getStatusCode());

            Map<String, Object> registrationMap = registrationResponse.jsonPath().getMap("$");
            assertAll("Ответ: /api/auth/reg",
                    () -> assertEquals(
                            testUser.getUsername(), registrationMap.get("username"),
                            "username не совпадает с пользовательским"
                    ),
                    () -> assertNotNull(
                            registrationMap.get("password"),
                            "password не должен быть пустым"
                    )
            );
        });
        step("Получить id пользователя из общего списка", () -> {
            Response allUsersResponse = userService.getAllUsers();
            assertEquals(200, allUsersResponse.getStatusCode());

            List<Map<String, Object>> allUsersList = allUsersResponse.jsonPath().getList("$");
            assertFalse(allUsersList.isEmpty(), "Список не должен быть пустым");

            Map<String, Object> currentUser = allUsersList
                    .stream()
                    .filter(map -> testUser.getUsername().equals(map.get("username")))
                    .findFirst()
                    .orElseThrow(() -> new AssertionError("User not found in list"));

            assertAll("Ответ: /api/user/all",
                    () -> assertNotNull(
                            currentUser.get("id"),
                            "id не может быть пустым"
                    ),
                    () -> assertEquals(
                            testUser.getUsername(), currentUser.get("username"),
                            "username не соответствует пользовательскому"
                    ),
                    () -> assertEquals(
                            "", currentUser.get("email"),
                            "В email должна быть пустая строка"
                    ),
                    () -> assertEquals(
                            "", currentUser.get("first_name"),
                            "В first_name должна быть пустая строка"
                    ),
                    () -> assertEquals(
                            "", currentUser.get("last_name"),
                            "В last_name должна быть пустая строка"
                    )
            );
            testUser.setUuid((Integer) currentUser.get("id"));
        });
        step("Проверить данные в профиле нового пользователя", () -> {
            Response userProfileResponse = userService.getUserProfile(testUser);
            assertEquals(200, userProfileResponse.getStatusCode());

            Map<String, Object> userProfileMap = userProfileResponse.jsonPath().getMap("$");
            assertAll("Ответ: /api/user/" + testUser.getUuid(),
                    () -> assertEquals(testUser.getUuid(), userProfileMap.get("id"), ""), //TODO
                    ()-> assertEquals(testUser.getUsername(), userProfileMap.get("username"), "")
            );
        });
    }
}
