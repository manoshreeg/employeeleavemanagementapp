package com.example.employeemanagement.dao;

import com.example.employeemanagement.model.Employee;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

@Repository
public class EmployeeDAO {

    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;


    // ADD EMPLOYEE
    public void addEmployee(Employee employee) {

        String sql =
                "INSERT INTO employees " +
                        "(name, department, email) " +
                        "VALUES (?, ?, ?)";


        try (
                Connection connection =
                        DriverManager.getConnection(
                                url,
                                username,
                                password
                        );

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setString(
                    1,
                    employee.getName()
            );

            statement.setString(
                    2,
                    employee.getDepartment()
            );

            statement.setString(
                    3,
                    employee.getEmail()
            );


            statement.executeUpdate();


        } catch (SQLException e) {

            throw new RuntimeException(
                    "Error adding employee",
                    e
            );
        }
    }


    // GET ALL EMPLOYEES
    public List<Employee> getAllEmployees() {

        List<Employee> employees =
                new ArrayList<>();


        String sql =
                "SELECT * FROM employees";


        try (
                Connection connection =
                        DriverManager.getConnection(
                                url,
                                username,
                                password
                        );

                PreparedStatement statement =
                        connection.prepareStatement(sql);

                ResultSet resultSet =
                        statement.executeQuery()
        ) {


            while (resultSet.next()) {

                Employee employee =
                        new Employee();


                employee.setId(
                        resultSet.getInt("id")
                );


                employee.setName(
                        resultSet.getString("name")
                );


                employee.setDepartment(
                        resultSet.getString("department")
                );


                employee.setEmail(
                        resultSet.getString("email")
                );


                employees.add(employee);
            }


        } catch (SQLException e) {

            throw new RuntimeException(
                    "Error retrieving employees",
                    e
            );
        }


        return employees;
    }


    // GET EMPLOYEE BY ID
    public Employee getEmployeeById(int id) {

        String sql =
                "SELECT * FROM employees " +
                        "WHERE id = ?";


        try (
                Connection connection =
                        DriverManager.getConnection(
                                url,
                                username,
                                password
                        );

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {


            statement.setInt(1, id);


            try (
                    ResultSet resultSet =
                            statement.executeQuery()
            ) {


                if (resultSet.next()) {

                    Employee employee =
                            new Employee();


                    employee.setId(
                            resultSet.getInt("id")
                    );


                    employee.setName(
                            resultSet.getString("name")
                    );


                    employee.setDepartment(
                            resultSet.getString("department")
                    );


                    employee.setEmail(
                            resultSet.getString("email")
                    );


                    return employee;
                }
            }


        } catch (SQLException e) {

            throw new RuntimeException(
                    "Error finding employee",
                    e
            );
        }


        return null;
    }


    // DELETE EMPLOYEE
    public void deleteEmployee(int id) {

        String sql =
                "DELETE FROM employees " +
                        "WHERE id = ?";


        try (
                Connection connection =
                        DriverManager.getConnection(
                                url,
                                username,
                                password
                        );

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {


            statement.setInt(1, id);


            statement.executeUpdate();


        } catch (SQLException e) {

            throw new RuntimeException(
                    "Error deleting employee",
                    e
            );
        }
    }
}