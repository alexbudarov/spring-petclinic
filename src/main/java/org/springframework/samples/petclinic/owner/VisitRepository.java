package org.springframework.samples.petclinic.owner;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface VisitRepository extends JpaRepository<Visit, Integer> {

	@Query("select v from Visit v, Owner o, Pet p" +
		" where v member of p.visits and p member of o.pets and o.id = ?1 and p.id = ?2" +
		" order by v.date")
	List<Visit> findByOwnerAndPet(int ownerId, int petId);

}
