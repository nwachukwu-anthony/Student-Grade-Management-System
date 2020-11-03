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
import java.util.Map;

@RestController
@RequestMapping("/api/v1/courses")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @GetMapping
    //@ResponseBody
    public List<CourseDTO> getAllCourse() {
        return courseService.getAllCourseService();
    }

    @GetMapping
    @RequestMapping("{id}")
    public ResponseEntity get(@PathVariable Long id) {
        CourseDTO response = courseService.getOneCourseService(id);
        return response != null
                ? ResponseEntity.ok(response)
                : ResponseEntity.badRequest().body(Map.of("msg", "No course with id=" + id + "."));
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public ResponseEntity delete(@PathVariable Long id) {
        return courseService.deleteService(id)
                ? ResponseEntity.ok(Map.of("msg", "success"))
                : ResponseEntity.badRequest().body(Map.of("msg", "Course Assigned to a student or Course doesn't exist"));
    }

    @PostMapping
    public ResponseEntity create(@RequestBody final Course course) {
        int response = courseService.createService(course);
        if (response == 1) {
            return ResponseEntity.badRequest().body(Map.of("msg", "Course with id=" + course.getId() + " exists"));
        } else if (response == 2) {
            return ResponseEntity.badRequest().body(Map.of("msg", "Teacher with id=" + course.getTeacher().getId() + " doesn't exists"));
        } else {
            return ResponseEntity.ok(Map.of("msg", "success!!!"));
        }
    }

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public ResponseEntity update(@PathVariable Long id, @RequestBody Course course) {
        int response = courseService.updateService(id, course);
        if (response == 3) {
            return ResponseEntity.badRequest().body(Map.of("msg", "Course with id=" + id + " doesn't exists."));
        } else if (response == 2) {
            return ResponseEntity.badRequest().body(Map.of("msg", "Teacher with id=" + course.getTeacher().getId() + " doesn't exists"));
        } else {
            return ResponseEntity.ok(Map.of("msg", "success!!!"));
        }
    }
}
