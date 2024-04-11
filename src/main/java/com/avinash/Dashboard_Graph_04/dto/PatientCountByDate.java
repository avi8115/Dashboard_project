package com.avinash.Dashboard_Graph_04.dto;

import java.time.LocalDate;

public class PatientCountByDate {

    private LocalDate date;
    private Long count;

    // Constructor, getters, and setters

    public PatientCountByDate(LocalDate date, Long count) {
        this.date = date;
        this.count = count;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }
}
