package com.example.employeemanagement.controller;

import com.example.employeemanagement.model.Employee;
import com.example.employeemanagement.service.EmployeeService;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService employeeService;


    public EmployeeController(
            EmployeeService employeeService) {

        this.employeeService = employeeService;
    }


    // ADD EMPLOYEE
    @PostMapping
    public ResponseEntity<String> addEmployee(
            @RequestBody Employee employee) {


        employeeService.addEmployee(
                employee
        );


        return ResponseEntity.ok(
                "Employee added successfully"
        );
    }


    // GET ALL
    @GetMapping
    public ResponseEntity<List<Employee>>
    getAllEmployees() {


        return ResponseEntity.ok(
                employeeService.getAllEmployees()
        );
    }


    // GET ONE
    @GetMapping("/{id}")
    public ResponseEntity<Employee>
    getEmployee(
            @PathVariable int id) {


        return ResponseEntity.ok(
                employeeService.getEmployeeById(id)
        );
    }


    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<String>
    deleteEmployee(
            @PathVariable int id) {


        employeeService.deleteEmployee(id);


        return ResponseEntity.ok(
                "Employee deleted successfully"
        );
    }
}