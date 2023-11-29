package org.springframework.samples.petclinic.owner;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PetTypeRepository extends JpaRepository<PetType, Integer>, JpaSpecificationExecutor<PetType> {
}