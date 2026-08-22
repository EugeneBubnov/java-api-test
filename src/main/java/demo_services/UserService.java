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

    public Response getAllUsers() {
        return step("Получить список всех пользователей. Отправить запрос: [GET] /api/user/all", () -> {
            return given()
                    .contentType(ContentType.JSON)
                    .log().all()
                    .when()
                    .get(BASE_URL + "/api/user/all")
                    .prettyPeek();
        });
    }

    public Response getUserProfile(User user) {
        return step("Получить информацию из профиля. Отправить запрос: [GET] /api/user/" + user.getUuid(), () -> {
            return given()
                    .contentType(ContentType.JSON)
                    .header("Authorization", "Basic " + user.getBasicToken())
                    .log().all()
                    .when()
                    .get(BASE_URL + "/api/user/" + user.getUuid())
                    .prettyPeek();
        });
    }

    public Response changeUserPassword(User user, String newPassword) {
        return step("Обновить пароль. Отправить запрос: [PUT] /api/user/password", () -> {
            JSONObject payload = new JSONObject();
            payload.put("old_password", user.getPassword());
            payload.put("new_password1", newPassword);
            payload.put("new_password2", newPassword);

            return given()
                    .contentType(ContentType.JSON)
                    .header("Authorization", "Basic " + user.getBasicToken())
                    .body(payload.toString())
                    .log().all()
                    .when()
                    .put(BASE_URL + "/api/user/password")
                    .prettyPeek();
        });
    }

    public Response updateUserProfile(User user, JSONObject payload) {
        return step("Обновить профиль. Отправить запрос: [PATCH] /api/user/update/profile/" + user.getUuid(),
                () -> {
                    return given()
                            .contentType(ContentType.JSON)
                            .header("Authorization", "Basic " + user.getBasicToken())
                            .body(payload.toString())
                            .log().all()
                            .when()
                            .patch(BASE_URL + "/api/user/update/profile/" + user.getUuid())
                            .prettyPeek();
                });
    }

    public Response deleteUser(User user) {
        return step("Удалить пользователя. Отправить запрос: [DELETE] /api/user/delete", () -> {
            return given()
                    .contentType(ContentType.JSON)
                    .header("Authorization", "Basic " + user.getBasicToken())
                    .log().all()
                    .when()
                    .delete(BASE_URL + "/api/user/delete")
                    .prettyPeek();
        });
    }
}
