package org.springframework.samples.petclinic.vet;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface SpecialtyRepository extends JpaRepository<Specialty, Integer>, /* should be added now */ JpaSpecificationExecutor<Specialty> {
}