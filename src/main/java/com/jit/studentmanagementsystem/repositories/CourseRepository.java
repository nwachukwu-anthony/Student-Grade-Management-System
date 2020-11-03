package com.jit.studentmanagementsystem.repositories;

import com.jit.studentmanagementsystem.models.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {
}
