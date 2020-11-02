package com.jit.studentmanagementsystem.controllers;

import com.jit.studentmanagementsystem.models.Teacher;
import com.jit.studentmanagementsystem.repositories.TeacherRepository;
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
@RequestMapping("/api/v1/teachers")
public class TeacherController {
    @Autowired
    private TeacherRepository teacherRepository;

    @GetMapping
    public List<Teacher> list() {
        return teacherRepository.findAll();
    }

    @GetMapping("{id}")
    public Teacher get(@PathVariable Long id) {
        return teacherRepository.getOne(id);
    }

    @PostMapping
    public Teacher create(@RequestBody final Teacher teacher) {
        return teacherRepository.saveAndFlush(teacher);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable Long id) {
        teacherRepository.deleteById(id);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public ResponseEntity update(@PathVariable Long id, @RequestBody Teacher teacher) {
        Optional<Teacher> existingTeacher = teacherRepository.findById(id);
        if (existingTeacher.isPresent()) {
            BeanUtils.copyProperties(teacher, existingTeacher, "id");
            return ResponseEntity.ok(teacherRepository.saveAndFlush(existingTeacher.get()));
        }
        return ResponseEntity.badRequest().body(Map.of("msg", "entity not found"));
    }
}
