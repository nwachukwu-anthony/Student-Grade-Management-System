package com.jit.studentmanagementsystem.controllers;

import com.jit.studentmanagementsystem.models.AssignCourseToStudent;
import com.jit.studentmanagementsystem.models.Course;
import com.jit.studentmanagementsystem.models.Student;
import com.jit.studentmanagementsystem.repositories.AssignCourseToStudentRepository;
import com.jit.studentmanagementsystem.repositories.CourseRepository;
import com.jit.studentmanagementsystem.repositories.StudentRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
public class AssignCourseToStudentController {
    @Autowired
    private AssignCourseToStudentRepository assignCourseToStudentRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private CourseRepository courseRepository;

    @PostMapping
    @RequestMapping("{courseId}/assignTostudent/{studentId}")
    public ResponseEntity create(@RequestBody final AssignCourseToStudent assignCourseToStudent) {
        Long studentIdFromWeb = assignCourseToStudent.getStudentid();
        Long courseIdFromWeb = assignCourseToStudent.getCourseid();

        Optional<Student> existingStudent = studentRepository.findById(studentIdFromWeb);
        Optional<Course> existingCourse = courseRepository.findById(courseIdFromWeb);

        if (existingStudent.isPresent() && existingCourse.isPresent() &&
                !existingStudent.get().getCourses()
                        .stream()
                        .map(Course::getId)
                        .collect(Collectors.toSet()).contains(courseIdFromWeb)) {
            assignCourseToStudentRepository.save(assignCourseToStudent);
            return ResponseEntity.ok("success");
        }
        return ResponseEntity.badRequest().body(Map.of("msg", "Course already assigned to student or" +
                "course or student not found"));
    }
}
