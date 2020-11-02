package com.jit.studentmanagementsystem.controllers;

import com.jit.studentmanagementsystem.models.Student;
import com.jit.studentmanagementsystem.models.Teacher;
import com.jit.studentmanagementsystem.repositories.StudentRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/students")
public class StudentController {
    @Autowired
    private StudentRepository studentRepository;

    @GetMapping
    public List<Student> list() {
        return studentRepository.findAll();
    }

    @GetMapping
    @RequestMapping("{id}")
    public Student get(@PathVariable Long id) {
        return studentRepository.getOne(id);
    }

    @PostMapping
    public Student create(@RequestBody final Student student) {
        return studentRepository.saveAndFlush(student);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable Long id) {
        studentRepository.deleteById(id);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public ResponseEntity update(@PathVariable Long id, @RequestBody Student student) {
        Optional<Student> existingStudent = studentRepository.findById(id);
        if (existingStudent.isPresent()) {
            BeanUtils.copyProperties(student, existingStudent, "id");
            return ResponseEntity.ok(studentRepository.saveAndFlush(existingStudent.get()));
        }
        return ResponseEntity.badRequest().body(Map.of("msg", "entity not found"));
    }
}

