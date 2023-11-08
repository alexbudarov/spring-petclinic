package org.springframework.samples.petclinic.owner;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;
import java.util.Objects;

/**
 * DTO for {@link Visit}
 */
public final class VisitDto {
    private Integer id;
    private LocalDate date;
    private @NotBlank String description;
    private Integer petId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPetId() {
        return petId;
    }

    public void setPetId(Integer petId) {
        this.petId = petId;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (VisitDto) obj;
        return Objects.equals(this.id, that.id) &&
                Objects.equals(this.date, that.date) &&
                Objects.equals(this.description, that.description) &&
                Objects.equals(this.petId, that.petId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, date, description, petId);
    }

    @Override
    public String toString() {
        return "VisitDto[" +
                "id=" + id + ", " +
                "date=" + date + ", " +
                "description=" + description + ", " +
                "petId=" + petId + ']';
    }


    public Integer id() {
        return id;
    }

    public Integer petId() {
        return petId;
    }
}