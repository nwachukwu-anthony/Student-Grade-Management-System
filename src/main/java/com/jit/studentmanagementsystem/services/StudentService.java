package com.jit.studentmanagementsystem.services;

import com.jit.studentmanagementsystem.dtos.StudentDTO;
import com.jit.studentmanagementsystem.models.AssignCourseToStudent;
import com.jit.studentmanagementsystem.models.Student;
import com.jit.studentmanagementsystem.repositories.AssignCourseToStudentRepository;
import com.jit.studentmanagementsystem.repositories.StudentRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private AssignCourseToStudentRepository assignCourseToStudentRepository;

    public List<StudentDTO> getAllStudentsService() {
        return studentRepository
                .findAll()
                .stream()
                .map(this::studentToStudentDTO)
                .collect(Collectors.toList());
    }

    public StudentDTO getOneStudentService(Long id) {
        return studentToStudentDTO(studentRepository
                .getOne(id));
    }

    public ResponseEntity deleteService(Long id) {
        Optional<Student> existingStudent = studentRepository.findById(id);
        boolean existingStudentCourse = assignCourseToStudentRepository
                .findAll()
                .stream()
                .map(AssignCourseToStudent::getStudentid)
                .collect(Collectors.toSet())
                .contains(id);

        if (!existingStudentCourse && existingStudent.isPresent()) {
            studentRepository.deleteById(id);
            return ResponseEntity.ok("success");
        }
        return ResponseEntity.badRequest().body("Student enrolled to a course or Student doesn't exist");
    }

    public ResponseEntity createService(final Student student) {
        Optional<Student> existingStudent = studentRepository.findById(student.getId());
        if (existingStudent.isPresent()) {
            return ResponseEntity.badRequest().body("Student with id=" + student.getId() + " exists");
        }
        return ResponseEntity.ok(studentRepository.saveAndFlush(student));
    }

    public ResponseEntity updateService(Long id, Student student) {
        Optional<Student> existingStudent = studentRepository.findById(id);
        if (existingStudent.isPresent()) {
            BeanUtils.copyProperties(student, existingStudent, "id");
            return ResponseEntity.ok(studentRepository.saveAndFlush(student));
        }
        return ResponseEntity.badRequest().body(Map.of("msg", "entity not found"));
    }

    private StudentDTO studentToStudentDTO(Student student) {
        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setId(student.getId());
        studentDTO.setFirstName(student.getFirstName() != null ? student.getFirstName() : "noData");
        studentDTO.setLastName(student.getLastName() != null ? student.getLastName() : "noData");
        studentDTO.setSex(student.getSex() != null ? student.getSex() : "noDate");
        studentDTO.setAge(student.getDateOfBirth() != null ? student.getDateOfBirth().until(LocalDate.now()).getYears() : -1);
        studentDTO.setPhoto(student.getPhoto());

        List<Map<String, String>> course = student.getCourses() != null
                ? student.getCourses().stream()
                .map(x -> Map.of(
                        "id", x.getId().toString(),
                        "name", x.getName() != null ? x.getName() : "noData",
                        "creditUnit", x.getCreditUnit() != null ? x.getCreditUnit().toString() : "noData",
                        "assignedTeacher", x.getTeacher() != null ? x.getTeacher().getId().toString() : "noData"
                ))
                .collect(Collectors.toList())
                : studentDTO.getCourses();
        studentDTO.setCourses(course);

        return studentDTO;
    }
}
