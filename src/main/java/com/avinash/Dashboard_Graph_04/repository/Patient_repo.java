package com.avinash.Dashboard_Graph_04.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.avinash.Dashboard_Graph_04.dto.PatientCountByDate;
import com.avinash.Dashboard_Graph_04.model.Patient_model;

public interface Patient_repo extends JpaRepository<Patient_model, Long> {

    List<Patient_model> findByDate(LocalDate date); // Find patients by date
    @Query("SELECT new com.avinash.Dashboard_Graph_04.dto.PatientCountByDate(p.date, COUNT(p)) FROM Patient_model p GROUP BY p.date")
    List<PatientCountByDate> countPatientsByDate();
    
    @Query("SELECT new com.avinash.Dashboard_Graph_04.dto.PatientCountByDate(p.date, COUNT(p)) " +
            "FROM Patient_model p WHERE p.date BETWEEN :startDate AND :endDate " +
            "GROUP BY p.date ORDER BY p.date ASC")
     List<PatientCountByDate> findPatientCountByDateRange(@Param("startDate") LocalDate startDate,
                                                          @Param("endDate") LocalDate endDate);
}
