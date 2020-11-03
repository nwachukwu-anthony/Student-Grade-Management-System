package com.jit.studentmanagementsystem.dtos;

import com.jit.studentmanagementsystem.models.Student;
import com.jit.studentmanagementsystem.models.Teacher;
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
    private TeacherDTO teacher;
    private List<StudentDTO> enrolledStudents;
}
