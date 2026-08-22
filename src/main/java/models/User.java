package models;

import java.util.Objects;

public class User extends AbsUser {
    private String uuid;
    private String token;

    public User(String username, String password, String firstName, String lastName, String birthDate, String email) {
        super(username, password, firstName, lastName, birthDate, email);
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public void showInfo() {
        System.out.println(this);
    }

    @Override
    public String toString() {
        return "User{firstName='" + getFirstName() +
                "', lastName='" + getLastName() +
                "', username='" + getUsername() +
                "', email='" + getEmail() +
                "', birthDate='" + getBirthDate() +
                "', uuid='" + getUuid() +
                "'}";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        User user = (User) obj;
        return Objects.equals(getUsername(), user.getUsername()) && Objects.equals(getEmail(), user.getEmail());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUsername(), getEmail());
    }
}
