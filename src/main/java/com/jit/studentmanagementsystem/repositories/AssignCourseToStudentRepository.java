package com.jit.studentmanagementsystem.repositories;

import com.jit.studentmanagementsystem.models.AssignCourseToStudent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssignCourseToStudentRepository extends JpaRepository<AssignCourseToStudent, Long> {
}
