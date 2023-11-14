package org.springframework.samples.petclinic.vet;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SpecialtyRepository extends JpaRepository<Specialty, Integer>,
        JpaSpecificationExecutor<Specialty> {

    List<Specialty> findByIdIn(Integer[] ids);

    /*@Query("select s from Vet v join v.specialties s where v.id = :vetId")
    Page<Specialty> findByVetId(Integer vetId, Pageable pageable);*/

}