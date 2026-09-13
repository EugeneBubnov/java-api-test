import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
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
        step("Зарегистрировать нового пользователя", () -> {
            Response registrationResponse = userService.register(testUser);
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
            testUser.setId((Integer) currentUser.get("id"));
        });
        step("Проверить данные в профиле нового пользователя", () -> {
            Response userProfileResponse = userService.getUserProfile(testUser);
            assertEquals(200, userProfileResponse.getStatusCode());

            Map<String, Object> userProfileMap = userProfileResponse.jsonPath().getMap("$");
            assertAll("Ответ: /api/user/" + testUser.getId(),
                    () -> assertEquals(
                            testUser.getId(), userProfileMap.get("id"),
                            "Не соответствует id"
                    ),
                    () -> assertEquals(
                            testUser.getUsername(), userProfileMap.get("username"),
                            "Не соответствует username"
                    ),
                    () -> assertEquals(
                            "", userProfileMap.get("email"),
                            "В email должна быть пустая строка"
                    ),
                    () -> assertEquals(
                            "", userProfileMap.get("first_name"),
                            "В first_name должна быть пустая строка"
                    ),
                    () -> assertEquals(
                            "", userProfileMap.get("last_name"),
                            "В last_name должна быть пустая строка"
                    ),
                    () -> {
                        LocalDate date = LocalDate.now();
                        assertTrue(
                                userProfileMap.get("date_joined").toString().contains(date.toString()),
                                "Не соответствует дата в date_joined"
                        );
                    },
                    () -> assertNull(
                            userProfileMap.get("birth_date"),
                            "birth_date должен быть null"
                    ),
                    () -> assertEquals(
                            "", userProfileMap.get("city"),
                            "В city должна быть пустая строка"
                    ),
                    () -> assertEquals(
                            "", userProfileMap.get("country"),
                            "В country должна быть пустая строка"
                    ),
                    () -> assertEquals(
                            "q", userProfileMap.get("family_status"),
                            "В family_status должно быть значение: q"
                    ),
                    () -> assertEquals(
                            "N", userProfileMap.get("gender"),
                            "В family_status должно быть значение: N"
                    )
            );
        });
        step("Обновить информацию в профиле пользователя", () -> {
            String newUsername = defaultFaker.name().username();
            String newEmail = defaultFaker.internet().emailAddress();

            testUser.setCountry("Russia");
            testUser.setCity("Moscow");
            testUser.setEmail(newEmail);
            testUser.setFamilyStatus("a");
            testUser.setGender("M");
            testUser.setBirthDate("2012-08-24");

            JSONObject updatePayload = new JSONObject();
            updatePayload.put("username", newUsername);
            updatePayload.put("country", testUser.getCountry());
            updatePayload.put("city", testUser.getCity());
            updatePayload.put("first_name", testUser.getFirstName());
            updatePayload.put("last_name", testUser.getLastName());
            updatePayload.put("email", testUser.getEmail());
            updatePayload.put("family_status", testUser.getFamilyStatus());
            updatePayload.put("birth_date", testUser.getBirthDate());
            updatePayload.put("gender", testUser.getGender());

            Response updateResponse = userService.updateUserProfile(testUser, updatePayload);
            assertEquals(200, updateResponse.getStatusCode());

            /*
             * Обновляем username после update.
             * P.S. если его обновить до отправки запроса, то upload упадёт c 401 из-за невалидного токена,
             *      который состоит из закодированных в base64: username:password
             */
            testUser.setUsername(newUsername);
            
            Map<String, Object> updMap = updateResponse.jsonPath().getMap("$");
            assertAll("Ответ: /api/user/update/profile/" + testUser.getId(),
                    () -> assertEquals(
                            testUser.getCountry(), updMap.get("country"),
                            "Не обновилось поле country"
                    ),
                    () -> assertEquals(
                            testUser.getFamilyStatus(), updMap.get("family_status"),
                            "Не обновилось поле family_status"
                    ),
                    () -> assertEquals(
                            testUser.getGender(), updMap.get("gender"),
                            "Не обновилось поле gender"
                    ),
                    () -> assertEquals(
                            testUser.getCity(), updMap.get("city"),
                            "Не обновилось поле city"
                    ),
                    () -> assertEquals(
                            testUser.getBirthDate(), updMap.get("birth_date"),
                            "Не обновилось поле birth_date"
                    ),
                    () -> assertEquals(
                            testUser.getLastName(), updMap.get("last_name"),
                            "Не обновилось поле last_name"
                    ),
                    () -> assertEquals(
                            testUser.getFirstName(), updMap.get("first_name"),
                            "Не обновилось поле first_name"
                    ),
                    () -> assertEquals(
                            testUser.getEmail(), updMap.get("email"),
                            "Не обновилось поле email"
                    ),
                    () -> assertEquals(
                            testUser.getUsername(), updMap.get("username"),
                            "Не обновилось поле username"
                    )
            );
        });
        step("Удалить пользователя", () -> {
            Response deleteResponse = userService.deleteUser(testUser);
            assertEquals(204, deleteResponse.getStatusCode());
            testUser.setDeleted(true);
        });
    }
}
