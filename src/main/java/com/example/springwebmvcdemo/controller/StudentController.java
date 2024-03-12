package com.example.springwebmvcdemo.controller;

import com.example.springwebmvcdemo.model.Student;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class StudentController {

    private final List<Student> students = new ArrayList<>();
    private int lastAssignedId = 0;

    @GetMapping("/student-details/{id}")
    public String getStudent(@PathVariable int id, Model model) {
        Student student = findStudentById(id);
        if (student != null) {
            model.addAttribute("student", student);
            return "student-details"; // Thymeleaf template name
        } else {
            // Handle case when student with the given ID is not found
            return "student-not-found"; // Thymeleaf template name for displaying an error message
        }
    }

    private Student findStudentById(int id) {
        for (Student student : students) {
            if (student.getId() == id) {
                return student;
            }
        }
        return null; // Return null if student with the given ID is not found
    }

    @GetMapping("/students")
    public String listStudents(Model model) {
        model.addAttribute("students", students);
        return "student-list"; // Thymeleaf template name
    }

    @GetMapping("/add-student")
    public String showAddStudentForm(Model model) {
        model.addAttribute("student", new Student());
        return "add-student"; // Thymeleaf template name
    }

    @PostMapping("/add-student")
    public String addStudent(Student student) {
        // Save the student to the database (in-memory list for simplicity)
        student.setId(++lastAssignedId);
        students.add(student);
        return "redirect:/students";
    }

    @GetMapping("/edit-student/{id}")
    public String showEditStudentForm(@PathVariable int id, Model model) {
        // Find the student with the given ID from the list or database
        Student student = findStudentById(id);
        model.addAttribute("student", student);
        return "edit-student"; // Thymeleaf template name
    }

    @PostMapping("/edit-student/{id}")
    public String updateStudent(@PathVariable int id, Student updatedStudent) {
        // Find the student with the given ID from the list or database
        Student student = findStudentById(id);
        // Update the student's properties with the values from the updatedStudent object
        assert student != null;
        student.setFirstname(updatedStudent.getFirstname());
        student.setLastname(updatedStudent.getLastname());
        return "redirect:/students";
    }
    @GetMapping("/delete-student/{id}")
    public String deleteStudent(@PathVariable int id) {
        // Find the student with the given ID from the list or database
        Student student = findStudentById(id);
        // Remove the student from the list or delete it from the database
        students.remove(student);
        return "redirect:/students";
    }
}
