package org.springframework.samples.petclinic.owner;

import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import org.hibernate.annotations.BatchSize;
import org.springframework.samples.petclinic.model.BaseEntity;

@Entity
@Table(name = "receipts")
public class Receipt extends BaseEntity {
    @Digits(integer = 6, fraction = 0)
    @Column(name = "number")
    private String number;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "owner_id", nullable = false)
    @BatchSize(size = 10)
    private Owner owner;

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
