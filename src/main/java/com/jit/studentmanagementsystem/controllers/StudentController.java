package com.jit.studentmanagementsystem.controllers;

import com.jit.studentmanagementsystem.dtos.CourseDTO;
import com.jit.studentmanagementsystem.dtos.StudentDTO;
import com.jit.studentmanagementsystem.models.Course;
import com.jit.studentmanagementsystem.models.Student;
import com.jit.studentmanagementsystem.models.Teacher;
import com.jit.studentmanagementsystem.repositories.StudentRepository;
import com.jit.studentmanagementsystem.services.CourseService;
import com.jit.studentmanagementsystem.services.StudentService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/students")
public class StudentController {
    @Autowired
    private StudentService studentService;

    @GetMapping
    @ResponseBody
    public List<StudentDTO> list() {
        return studentService.getAllStudentsService();
    }

    @GetMapping
    @RequestMapping("{id}")
    public StudentDTO get(@PathVariable Long id) {
        return studentService.getOneStudentService(id);
    }

    @PostMapping
    public ResponseEntity create(@RequestBody final Student student) {
        return studentService.createService(student);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public ResponseEntity delete(@PathVariable Long id) {
        return studentService.deleteService(id);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public ResponseEntity update(@PathVariable Long id, @RequestBody Student student) {
        return studentService.updateService(id, student);
    }
}

