package org.springframework.samples.petclinic.owner;

import jakarta.persistence.*;
import org.springframework.samples.petclinic.model.NamedEntity;

@Entity
@Table(name = "medicine")
public class Medicine extends NamedEntity {

    @Column(name = "safe_for_children")
    private Boolean safeForChildren;

    public Boolean getSafeForChildren() {
        return safeForChildren;
    }

    public void setSafeForChildren(Boolean safeForChildren) {
        this.safeForChildren = safeForChildren;
    }
}