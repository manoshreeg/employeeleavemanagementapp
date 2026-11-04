package com.example.employeemanagement;

import com.example.employeemanagement.dao.EmployeeDAO;
import com.example.employeemanagement.dao.LeaveDAO;
import com.example.employeemanagement.model.LeaveRequest;
import com.example.employeemanagement.service.LeaveService;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LeaveServiceTest {


    @Test
    void calculateLeaveDays_shouldReturnCorrectNumber() {


        LeaveDAO leaveDAO = null;

        EmployeeDAO employeeDAO = null;


        LeaveService leaveService =
                new LeaveService(
                        leaveDAO,
                        employeeDAO
                );


        LeaveRequest leave =
                new LeaveRequest();


        leave.setStartDate(
                LocalDate.of(
                        2026,
                        9,
                        10
                )
        );


        leave.setEndDate(
                LocalDate.of(
                        2026,
                        9,
                        12
                )
        );


        long result =
                leaveService.calculateLeaveDays(
                        leave
                );


        assertEquals(
                3,
                result
        );
    }
}