package com.avinash.Dashboard_Graph_04.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.avinash.Dashboard_Graph_04.dto.DoctorCountByDate;
import com.avinash.Dashboard_Graph_04.model.Doctor_model;

public interface Doctor_repo extends JpaRepository<Doctor_model, Long> {

    List<Doctor_model> findByDate(LocalDate date); // Find doctors by date
    
    @Query("SELECT new com.avinash.Dashboard_Graph_04.dto.DoctorCountByDate(d.date, COUNT(d)) FROM Doctor_model d GROUP BY d.date")
    List<DoctorCountByDate> countDoctorsByDate();
    
    @Query("SELECT new com.avinash.Dashboard_Graph_04.dto.DoctorCountByDate(d.date, COUNT(d)) " +
            "FROM Doctor_model d WHERE d.date BETWEEN :startDate AND :endDate " +
            "GROUP BY d.date ORDER BY d.date ASC")
     List<DoctorCountByDate> findDoctorCountByDateRange(@Param("startDate") LocalDate startDate,
                                                        @Param("endDate") LocalDate endDate);
}
