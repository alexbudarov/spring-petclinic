package org.springframework.samples.petclinic.visit;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.owner.Visit;

import java.time.LocalDate;

public interface VisitRepository extends JpaRepository<Visit, Integer> {

    @Query("select (count(v) > 0) from Visit v where v.assignedVet.id = :id and v.date = :date")
    boolean existVisitsByDate(@Param("id") Integer vetId, @Param("date") LocalDate date);
}