package org.springframework.samples.petclinic.owner;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.Nullable;

public interface PetRepository extends JpaRepository<Pet, Integer> {

    @Nullable
    @Query("select p.id from Pet p join p.visits v where v.id = :visitId")
    Integer loadPetByVisit(int visitId);

	Page<Pet> findByIdIn(Object[] ids, Pageable pageable);
}
