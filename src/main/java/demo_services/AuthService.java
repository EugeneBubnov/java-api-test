package demo_services;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import models.User;
import org.json.JSONObject;
import utils.ProjectProps;

import static io.qameta.allure.Allure.*;
import static io.restassured.RestAssured.given;

public class AuthService {
    private final String BASE_URL = ProjectProps.getBaseUrl();

    public Response register(User user) {
        return step("Зарегистрироваться. Отправить запрос: [POST] ", () -> {
            JSONObject payload = new JSONObject();
            payload.put("username", user.getUsername());
            payload.put("password", user.getPassword());

            return given()
                    .contentType(ContentType.JSON)
                    .body(payload.toString())
                    .log().all()
                    .post(BASE_URL + "/api/auth/reg")
                    .prettyPeek();
        });
    }

    public Response getAuthToken(User user) {
        return step("Получить токен авторизации. Отправить запрос: [POST] /api/auth/token", () -> {
            JSONObject payload = new JSONObject();
            payload.put("username", user.getUsername());
            payload.put("password", user.getPassword());

            return given()
                    .contentType(ContentType.JSON)
                    .body(payload.toString())
                    .log().all()
                    .post(BASE_URL + "/api/auth/token")
                    .prettyPeek();
        });
    }
}