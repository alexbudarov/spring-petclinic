package org.springframework.samples.petclinic.model;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import org.springframework.samples.petclinic.owner.Owner;

import java.util.List;
import java.util.Objects;

/**
 * DTO for {@link Owner}
 */
public final class MyOwnerDto {
    private final Integer id;
    private final @NotBlank String firstName;
    private final @NotBlank String lastName;
    private final @NotBlank String address;
    private @NotBlank String city;
    private final @Digits(integer = 10, fraction = 0) @NotBlank String telephone;
    private final List<Integer> petIds;

    public MyOwnerDto(Integer id, String firstName, String lastName, String address, String telephone, List<Integer> petIds) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.telephone = telephone;
        this.petIds = petIds;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Integer getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public String getTelephone() {
        return telephone;
    }

    public List<Integer> getPetIds() {
        return petIds;
    }
}