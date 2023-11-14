package org.springframework.samples.petclinic.owner;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PetTypeRepository extends JpaRepository<PetType, Integer> {

    List<PetType> findByIdIn(Integer[] ids);

    Page<PetType> findByNameStartsWithIgnoreCase(String name, Pageable pageable);
}