package com.jit.studentmanagementsystem.dtos;

import com.jit.studentmanagementsystem.models.Course;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
public class StudentDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String sex;
    private Integer age;
//    private byte[] photo;
//    private List<Map<String, String>> courses;
}
