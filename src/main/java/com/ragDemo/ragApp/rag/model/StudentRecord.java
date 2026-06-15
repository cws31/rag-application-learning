package com.ragDemo.ragApp.rag.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentRecord {

    private String rollNumber;

    private String section;

    private Double attendancePercentage;

    private String attendanceRange;

    private Integer classesAttended;
}