package org.springframework.samples.petclinic.owner;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface VisitRepository extends JpaRepository<Visit, Integer> {

	@Query("select v from Pet p join p.visits v" +
			" where p.id = :petId" +
			" order by v.date")
	List<Visit> findByPet(int petId);
}
