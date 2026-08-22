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
        step("Получить список всех пользователей. Отправить запрос: [GET] /api/user/all");

        return given()
                .contentType(ContentType.JSON)
                .when()
                .get(BASE_URL + "/api/user/all")
                .prettyPeek();
    }

    public Response getUserProfile(User user) {
        step("Получить информацию из профиля пользователя. Отправить запрос: [GET] /api/user/" + user.getUuid()
        );

        return given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Basic " + user.getToken())
                .log().all()
                .when()
                .get(BASE_URL + "/api/user/" + user.getUuid())
                .prettyPeek();
    }

    public Response changeUserPassword(User user, String newPassword) {
        step("Обновить пароль пользователя. Отправить запрос: [PUT] /api/user/password");

        JSONObject payload = new JSONObject();
        payload.put("old_password", user.getPassword());
        payload.put("new_password", newPassword);
        payload.put("new_password1", newPassword);

        return given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Basic " + user.getToken())
                .body(payload.toString())
                .when()
                .put(BASE_URL + "/api/user/password")
                .prettyPeek();
    }

    public Response updateUserProfile(User user, JSONObject payload) {
        step("Обновить профиль пользователя. Отправить запрос: [PATCH] /api/user/update/profile/" + user.getUuid());

        return given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Basic " + user.getToken())
                .body(payload.toString())
                .when()
                .patch(BASE_URL + "api/user/update/profile/" + user.getUuid())
                .prettyPeek();
    }

    public Response deleteUser(User user) {
        step("Удалить пользователя. Отправить запрос: [DELETE] /api/user/delete");

        return given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Basic " + user.getToken())
                .when()
                .patch(BASE_URL + "/api/user/delete")
                .prettyPeek();

    }
}
