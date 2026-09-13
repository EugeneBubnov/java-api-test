package models;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Objects;

public class User {
    private String username;
    private final String password;
    private final String firstName;
    private final String lastName;
    private String birthDate;
    private String email;
    private Integer id;
    private String country;
    private String city;
    private String familyStatus;
    private String gender;
    private boolean deleted;

    public User(String username, String password, String firstName, String lastName, String birthDate, String email) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public String getBasicToken() {
        String prepareTokenData = getUsername() + ":" + getPassword();
        return Base64.getEncoder().encodeToString(prepareTokenData.getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public String toString() {
        return "User{firstName='" + getFirstName() +
                "', lastName='" + getLastName() +
                "', username='" + getUsername() +
                "', email='" + getEmail() +
                "', birthDate='" + getBirthDate() +
                "', uuid='" + getId() +
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
