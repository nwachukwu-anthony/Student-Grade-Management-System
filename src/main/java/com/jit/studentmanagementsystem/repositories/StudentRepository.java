package com.jit.studentmanagementsystem.repositories;

import com.jit.studentmanagementsystem.models.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
}
