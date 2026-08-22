package demo_services;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import models.User;
import org.json.JSONObject;
import utils.ProjectProps;

import static io.qameta.allure.Allure.*;
import static io.restassured.RestAssured.given;

public class UserService {
    private final String BASE_URL = ProjectProps.getBaseUrl();

    public Response register(User user) {
        return step("Зарегистрировать нового пользователя. Отправить запрос: [POST] /api/auth/reg", () -> {
            JSONObject payload = new JSONObject();
            payload.put("username", user.getUsername());
            payload.put("password", user.getPassword());

            return given()
                    .contentType(ContentType.JSON)
                    .body(payload.toString())
                    .post(BASE_URL + "/api/auth/reg");
        });
    }

    public Response getAllUsers() {
        return step("Получить список всех пользователей. Отправить запрос: [GET] /api/user/all",
                () -> given()
                        .contentType(ContentType.JSON)
                        .when()
                        .get(BASE_URL + "/api/user/all")
        );
    }

    public Response getUserProfile(User user) {
        return step("Получить информацию из профиля. Отправить запрос: [GET] /api/user/" + user.getUuid(),
                () -> given()
                        .contentType(ContentType.JSON)
                        .header("Authorization", "Basic " + user.getBasicToken())
                        .when()
                        .get(BASE_URL + "/api/user/" + user.getUuid())
        );
    }

    public Response updateUserProfile(User user, JSONObject payload) {
        return step("Обновить профиль. Отправить запрос: [PATCH] /api/user/update/profile/" + user.getUuid(),
                () -> given()
                        .contentType(ContentType.JSON)
                        .header("Authorization", "Basic " + user.getBasicToken())
                        .body(payload.toString())
                        .when().patch(BASE_URL + "/api/user/update/profile/" + user.getUuid())
        );
    }

    public Response deleteUser(User user) {
        return step("Удалить пользователя. Отправить запрос: [DELETE] /api/user/delete",
                () -> given()
                        .contentType(ContentType.JSON)
                        .header("Authorization", "Basic " + user.getBasicToken())
                        .when()
                        .delete(BASE_URL + "/api/user/delete")
        );
    }
}
