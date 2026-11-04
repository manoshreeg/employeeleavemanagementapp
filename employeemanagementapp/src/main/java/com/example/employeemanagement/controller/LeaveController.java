package com.example.employeemanagement.controller;

import com.example.employeemanagement.model.LeaveRequest;
import com.example.employeemanagement.service.LeaveService;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/leaves")
public class LeaveController {

    private final LeaveService leaveService;


    public LeaveController(
            LeaveService leaveService) {

        this.leaveService = leaveService;
    }


    // APPLY LEAVE
    @PostMapping
    public ResponseEntity<String>
    applyLeave(
            @RequestBody LeaveRequest leave) {


        leaveService.applyLeave(leave);


        return ResponseEntity.ok(
                "Leave applied successfully"
        );
    }


    // GET EMPLOYEE LEAVES
    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<LeaveRequest>>
    getEmployeeLeaves(
            @PathVariable int employeeId) {


        return ResponseEntity.ok(
                leaveService.getEmployeeLeaves(
                        employeeId
                )
        );
    }


    // APPROVE
    @PutMapping("/{leaveId}/approve")
    public ResponseEntity<String>
    approveLeave(
            @PathVariable int leaveId) {


        leaveService.approveLeave(
                leaveId
        );


        return ResponseEntity.ok(
                "Leave approved successfully"
        );
    }


    // REJECT
    @PutMapping("/{leaveId}/reject")
    public ResponseEntity<String>
    rejectLeave(
            @PathVariable int leaveId) {


        leaveService.rejectLeave(
                leaveId
        );


        return ResponseEntity.ok(
                "Leave rejected successfully"
        );
    }
}