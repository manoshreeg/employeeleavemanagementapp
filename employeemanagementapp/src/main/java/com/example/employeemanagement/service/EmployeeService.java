package com.example.employeemanagement.service;

import com.example.employeemanagement.dao.EmployeeDAO;
import com.example.employeemanagement.model.Employee;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    private final EmployeeDAO employeeDAO;


    public EmployeeService(
            EmployeeDAO employeeDAO) {

        this.employeeDAO = employeeDAO;
    }


    // ADD EMPLOYEE
    public void addEmployee(
            Employee employee) {


        if (employee.getName() == null ||
                employee.getName().isBlank()) {

            throw new IllegalArgumentException(
                    "Employee name cannot be empty"
            );
        }


        if (employee.getDepartment() == null ||
                employee.getDepartment().isBlank()) {

            throw new IllegalArgumentException(
                    "Department cannot be empty"
            );
        }


        if (employee.getEmail() == null ||
                employee.getEmail().isBlank()) {

            throw new IllegalArgumentException(
                    "Email cannot be empty"
            );
        }


        employeeDAO.addEmployee(employee);
    }


    // GET ALL EMPLOYEES
    public List<Employee> getAllEmployees() {

        return employeeDAO.getAllEmployees();
    }


    // GET ONE EMPLOYEE
    public Employee getEmployeeById(
            int id) {


        Employee employee =
                employeeDAO.getEmployeeById(id);


        if (employee == null) {

            throw new RuntimeException(
                    "Employee not found"
            );
        }


        return employee;
    }


    // DELETE
    public void deleteEmployee(
            int id) {


        // Check if employee exists
        getEmployeeById(id);


        employeeDAO.deleteEmployee(id);
    }
}