package com.github.henriquemb.data.dto;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.github.henriquemb.serializer.GenderSerializer;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@JsonPropertyOrder({"id", "first_name", "last_name", "birthdate", "address", "gender"})
//@JsonFilter("PersonFilter")
public class PersonDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;

    @JsonProperty("first_name")
    private String firstName;

    @JsonProperty("last_name")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String lastName;

    @JsonProperty("birthdate")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date birthDate;

    @JsonProperty("phone_number")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String phoneNumber;

    private String address;

    /*
     * Devido a um conflito de versões e uso do Dozer o JsonSerializer não funciona.
     */
    //@JsonIgnore
    //@JsonSerialize(using = GenderSerializer.class)
    private String gender;

    public PersonDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof PersonDTO personDTO)) return false;
        return Objects.equals(getId(), personDTO.getId()) && Objects.equals(getFirstName(), personDTO.getFirstName()) && Objects.equals(getLastName(), personDTO.getLastName()) && Objects.equals(getBirthDate(), personDTO.getBirthDate()) && Objects.equals(getPhoneNumber(), personDTO.getPhoneNumber()) && Objects.equals(getAddress(), personDTO.getAddress()) && Objects.equals(getGender(), personDTO.getGender());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getFirstName(), getLastName(), getBirthDate(), getPhoneNumber(), getAddress(), getGender());
    }
}
