package org.springframework.samples.petclinic.owner;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PetRepository extends JpaRepository<Pet, Integer>, JpaSpecificationExecutor<Pet> {
	Slice<Pet> findByNameStartsWithIgnoreCase(String name, Pageable pageable);

	Slice<Pet> findByOwner_Id(Integer id, Pageable pageable);

	Slice<Pet> findSlicedBy(Pageable pageable);

	// returns PageImpl anyway
	Slice<Pet> findSlicedBy(Specification<Pet> spec, Pageable pageable);
}
