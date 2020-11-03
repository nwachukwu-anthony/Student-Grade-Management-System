package com.jit.studentmanagementsystem.services;

import com.jit.studentmanagementsystem.dtos.CourseDTO;
import com.jit.studentmanagementsystem.dtos.StudentDTO;
import com.jit.studentmanagementsystem.dtos.TeacherDTO;
import com.jit.studentmanagementsystem.models.AssignCourseToStudent;
import com.jit.studentmanagementsystem.models.Course;
import com.jit.studentmanagementsystem.models.Student;
import com.jit.studentmanagementsystem.models.Teacher;
import com.jit.studentmanagementsystem.repositories.AssignCourseToStudentRepository;
import com.jit.studentmanagementsystem.repositories.CourseRepository;
import com.jit.studentmanagementsystem.repositories.TeacherRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
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

    public List<CourseDTO> getAllCourseService() {
        return courseRepository
                .findAll()
                .stream()
                .map(this::courseToCourseDTO)
                .collect(Collectors.toList());
    }

    public CourseDTO getOneCourseService(Long id) {
        Optional<Course> existingCourse = courseRepository.findById(id);
        return existingCourse.isPresent()
                ? courseToCourseDTO(courseRepository.getOne(id))
                : null;
    }

    public boolean deleteService(Long id) {
        Optional<Course> existingCourse = courseRepository.findById(id);
        boolean existingStudentCourse = assignCourseToStudentRepository
                .findAll()
                .stream()
                .map(AssignCourseToStudent::getCourseid)
                .collect(Collectors.toSet())
                .contains(id);

        if (!existingStudentCourse && existingCourse.isPresent()) {
            courseRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public int createService(final Course course) {
        Optional<Course> existingCourse = courseRepository.findById(course.getId());
        if (existingCourse.isPresent()) {
            return 1;
        } else if (course.getTeacher() != null && teacherRepository.findById(course.getTeacher().getId()).isEmpty()) {
            return 2;
        } else {
            courseRepository.saveAndFlush(course);
            return 3;
        }
    }

    public int updateService(Long id, Course course) {
        Optional<Course> existingCourseO = courseRepository.findById(id);
        if (existingCourseO.isPresent()) {
            var existingCourse = existingCourseO.get();
            if (course.getTeacher() != null && teacherRepository.findById(course.getTeacher().getId()).isPresent()) {
                existingCourse.setTeacher(course.getTeacher());
            } else {
                return 2;
            }
            if (course.getName() != null) {
                existingCourse.setName(course.getName());
            }
            if (course.getCreditUnit() != null) {
                existingCourse.setCreditUnit(course.getCreditUnit());
            }
            courseRepository.saveAndFlush(existingCourse);
            return 1;
        }
        return 3;

    }

    private CourseDTO courseToCourseDTO(Course course) {
        CourseDTO courseDTO = new CourseDTO();

        courseDTO.setTeacher(teacherToTeacherDTO(course));

        List<StudentDTO> student = course.getStudents() != null
                ? course.getStudents().stream()
                .map(this::studentToStudentDTO)
                .collect(Collectors.toList())
                : courseDTO.getEnrolledStudents();
        courseDTO.setEnrolledStudents(student);

        BeanUtils.copyProperties(course, courseDTO);

        return courseDTO;
    }

    private TeacherDTO teacherToTeacherDTO(Course course) {
        TeacherDTO courseDTOTeacher = new TeacherDTO();
        Teacher teacher = course.getTeacher();

        if (teacher != null) {
            BeanUtils.copyProperties(teacher, courseDTOTeacher);
        }
        return courseDTOTeacher;
    }

    private StudentDTO studentToStudentDTO(Student student) {
        StudentDTO studentDTO = new StudentDTO();

        if (student != null) {
            if (student.getDateOfBirth() != null) {
                studentDTO.setAge(student.getDateOfBirth().until(LocalDate.now()).getYears());
            }
            BeanUtils.copyProperties(student, studentDTO);
        }
        return studentDTO;
    }
}
