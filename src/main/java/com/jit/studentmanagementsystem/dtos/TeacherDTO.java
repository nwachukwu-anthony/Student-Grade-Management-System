package com.jit.studentmanagementsystem.dtos;

import com.jit.studentmanagementsystem.models.Teacher;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TeacherDTO{
    private Long id;
    private String lastName;
    private String firstName;
    private String Sex;
}
