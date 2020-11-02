package com.jit.studentmanagementsystem.controllers;

import com.jit.studentmanagementsystem.dtos.CourseDTO;
import com.jit.studentmanagementsystem.models.Course;
import com.jit.studentmanagementsystem.services.CourseService;
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

@RestController
@RequestMapping("/api/v1/courses")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @GetMapping
    @ResponseBody
    public List<CourseDTO> getAllCourse() {
        return courseService.getAllCourse();
    }

    @GetMapping
    @RequestMapping("{id}")
    public CourseDTO get(@PathVariable Long id) {
        return courseService.getOneCourse(id);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public ResponseEntity delete(@PathVariable Long id) {
        return courseService.delete(id);
    }

    @PostMapping
    public ResponseEntity create(@RequestBody final Course course) {
        return courseService.create(course);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public ResponseEntity update(@PathVariable Long id, @RequestBody Course course) {
        return courseService.update(id, course);
    }
}
