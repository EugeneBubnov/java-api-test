package models;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Objects;

public class User extends AbsUser {
    private Integer uuid;
    private String country;
    private String city;
    private String familyStatus;
    private String gender;

    public User(String username, String password, String firstName, String lastName, String birthDate, String email) {
        super(username, password, firstName, lastName, birthDate, email);
    }

    public Integer getUuid() {
        return uuid;
    }

    public void setUuid(Integer uuid) {
        this.uuid = uuid;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getFamilyStatus() {
        return familyStatus;
    }

    public void setFamilyStatus(String familyStatus) {
        this.familyStatus = familyStatus;
    }

    public String getBasicToken() {
        String prepareTokenData = getUsername() + ":" + getPassword();
        return Base64.getEncoder().encodeToString(prepareTokenData.getBytes(StandardCharsets.UTF_8));
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
