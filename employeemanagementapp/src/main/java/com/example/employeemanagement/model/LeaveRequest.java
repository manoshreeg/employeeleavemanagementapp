package com.example.employeemanagement.model;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class LeaveRequest {

    private int leaveId;

    private int employeeId;

    private String leaveType;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate startDate;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate endDate;

    private String reason;

    private String status;


    public LeaveRequest() {
    }


    public LeaveRequest(
            int employeeId,
            String leaveType,
            LocalDate startDate,
            LocalDate endDate,
            String reason) {

        this.employeeId = employeeId;
        this.leaveType = leaveType;
        this.startDate = startDate;
        this.endDate = endDate;
        this.reason = reason;

        this.status = "PENDING";
    }


    public int getLeaveId() {
        return leaveId;
    }


    public void setLeaveId(int leaveId) {
        this.leaveId = leaveId;
    }


    public int getEmployeeId() {
        return employeeId;
    }


    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }


    public String getLeaveType() {
        return leaveType;
    }


    public void setLeaveType(String leaveType) {
        this.leaveType = leaveType;
    }


    public LocalDate getStartDate() {
        return startDate;
    }


    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }


    public LocalDate getEndDate() {
        return endDate;
    }


    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }


    public String getReason() {
        return reason;
    }


    public void setReason(String reason) {
        this.reason = reason;
    }


    public String getStatus() {
        return status;
    }


    public void setStatus(String status) {
        this.status = status;
    }
}