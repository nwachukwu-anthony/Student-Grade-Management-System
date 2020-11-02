package com.jit.studentmanagementsystem.dtos;

import com.jit.studentmanagementsystem.models.Student;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;


@Data
@NoArgsConstructor
public class CourseDTO {
    private Long id;
    private String name;
    private Integer creditUnit;
    private Map<String, String> teacher;
    private List<Map<String, String>> enrolledStudents;
}
