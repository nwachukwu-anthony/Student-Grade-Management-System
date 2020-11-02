package com.jit.studentmanagementsystem.services;

import com.jit.studentmanagementsystem.dtos.CourseDTO;
import com.jit.studentmanagementsystem.models.AssignCourseToStudent;
import com.jit.studentmanagementsystem.models.Course;
import com.jit.studentmanagementsystem.models.Teacher;
import com.jit.studentmanagementsystem.repositories.AssignCourseToStudentRepository;
import com.jit.studentmanagementsystem.repositories.CourseRepository;
import com.jit.studentmanagementsystem.repositories.TeacherRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CourseService {
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private AssignCourseToStudentRepository assignCourseToStudentRepository;
    @Autowired
    private TeacherRepository teacherRepository;

    public List<CourseDTO> getAllCourse() {
        return courseRepository
                .findAll()
                .stream()
                .map(this::courseToCourseDTO)
                .collect(Collectors.toList());
    }

    public CourseDTO getOneCourse(Long id) {
        return courseToCourseDTO(courseRepository
                .getOne(id));
    }

    public ResponseEntity delete(Long id) {
        Optional<Course> existingCourse = courseRepository.findById(id);
        boolean existingStudentCourse = assignCourseToStudentRepository
                .findAll()
                .stream()
                .map(AssignCourseToStudent::getStudentid)
                .collect(Collectors.toSet())
                .contains(id);

        if (!existingStudentCourse && existingCourse.isPresent()) {
            courseRepository.deleteById(id);
            return ResponseEntity.ok("success");
        }
        return ResponseEntity.badRequest().body("Course Assigned to a student or Course doesn't exist");
    }

    public ResponseEntity create(final Course course) {
        Optional<Course> existingCourse = courseRepository.findById(course.getId());
        if (existingCourse.isPresent()) {
            return ResponseEntity.badRequest().body("Course with id=" + course.getId() + " exists");
        }
        return ResponseEntity.ok(courseRepository.saveAndFlush(course));
    }

    public ResponseEntity update(Long id, Course course) {
        Optional<Course> existingCourse = courseRepository.findById(id);
        if (existingCourse.isPresent()) {
            BeanUtils.copyProperties(course, existingCourse, "id");
            return ResponseEntity.ok(courseRepository.saveAndFlush(course));
        }
        return ResponseEntity.badRequest().body(Map.of("msg", "entity not found"));
    }


    private CourseDTO courseToCourseDTO(Course course) {
        CourseDTO courseDTO = new CourseDTO();
        courseDTO.setId(course.getId());
        courseDTO.setName(course.getName() != null ? course.getName() : "noData");
        courseDTO.setCreditUnit(course.getCreditUnit() != null ? course.getCreditUnit() : -1);

        Teacher teacher = course.getTeacher();
        courseDTO.setTeacher(teacher == null
                ? courseDTO.getTeacher()
                : Map.of(
                "id", teacher.getId().toString(),
                "surName", teacher.getLastName() != null ? teacher.getLastName() : "noData",
                "firstName ", teacher.getFirstName() != null ? teacher.getFirstName() : "noData",
                "sex", teacher.getSex() != null ? teacher.getSex() : "noData",
                "birthDate", teacher.getDateOfBirth() != null ? teacher.getDateOfBirth().toString() : "noData"));

        List<Map<String, String>> student = course.getStudents() != null
                ? course.getStudents().stream()
                .map(x -> Map.of(
                        "id", x.getId().toString(),
                        "firstName", x.getFirstName() != null ? x.getFirstName() : "noData",
                        "lastName", x.getLastName() != null ? x.getLastName() : "noData",
                        "sex", x.getSex() != null ? x.getSex() : "noData",
                        "DateOfBirth", x.getDateOfBirth() != null ? x.getDateOfBirth().toString() : "noData"))
                .collect(Collectors.toList())
                : courseDTO.getEnrolledStudents();
        courseDTO.setEnrolledStudents(student);

        return courseDTO;
    }
}
