package org.springframework.samples.petclinic.vet;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SpecialtyRepository extends JpaRepository<Specialty, Integer> {

    Page<Specialty> findByIdIn(Integer[] ids, Pageable pageable);

    @Query("select s from Vet v join v.specialties s where v.id = :vetId")
    Page<Specialty> findByVetId(Integer vetId, Pageable pageable);

}