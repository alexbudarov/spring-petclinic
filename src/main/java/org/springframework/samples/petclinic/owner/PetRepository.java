package org.springframework.samples.petclinic.owner;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.Nullable;

import java.util.List;

public interface PetRepository extends JpaRepository<Pet, Integer> {

    @Nullable
    @Query("select p.id from Pet p join p.visits v where v.id = :visitId")
    Integer loadPetByVisit(int visitId);

    @Query("select p from Pet p, Owner o where o.id = :ownerId and p member of o.pets")
    List<Pet> loadByOwnerId(int ownerId, Sort sort);

	List<Pet> findByIdIn(Integer[] ids, Sort sort);
}
