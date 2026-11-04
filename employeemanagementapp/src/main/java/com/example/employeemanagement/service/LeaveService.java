package com.example.employeemanagement.service;

import com.example.employeemanagement.dao.EmployeeDAO;
import com.example.employeemanagement.dao.LeaveDAO;
import com.example.employeemanagement.model.LeaveBalance;
import com.example.employeemanagement.model.LeaveRequest;

import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class LeaveService {

    // Fixed yearly allotments per leave type, used to compute balances.
    // A real HR system would store these per-employee; this keeps the
    // demo simple while still showing real usage pulled from the DB.
    private static final Map<String, Integer> ANNUAL_ALLOTMENT = new LinkedHashMap<>();
    static {
        ANNUAL_ALLOTMENT.put("Casual", 12);
        ANNUAL_ALLOTMENT.put("Sick", 8);
        ANNUAL_ALLOTMENT.put("Earned", 15);
    }

    private final LeaveDAO leaveDAO;

    private final EmployeeDAO employeeDAO;


    public LeaveService(
            LeaveDAO leaveDAO,
            EmployeeDAO employeeDAO) {

        this.leaveDAO = leaveDAO;
        this.employeeDAO = employeeDAO;
    }


    // APPLY FOR LEAVE
    public void applyLeave(
            LeaveRequest leave) {


        // Check whether employee exists
        if (employeeDAO.getEmployeeById(
                leave.getEmployeeId()) == null) {

            throw new RuntimeException(
                    "Employee not found"
            );
        }


        // Check dates
        if (leave.getStartDate()
                .isAfter(leave.getEndDate())) {

            throw new IllegalArgumentException(
                    "Start date cannot be after end date"
            );
        }


        if (leave.getLeaveType() == null ||
                leave.getLeaveType().isBlank()) {

            throw new IllegalArgumentException(
                    "Leave type cannot be empty"
            );
        }


        leaveDAO.applyLeave(leave);
    }


    // GET EMPLOYEE LEAVES
    public List<LeaveRequest>
    getEmployeeLeaves(int employeeId) {


        if (employeeDAO.getEmployeeById(
                employeeId) == null) {

            throw new RuntimeException(
                    "Employee not found"
            );
        }


        return leaveDAO.getLeavesByEmployeeId(
                employeeId
        );
    }


    // APPROVE
    public void approveLeave(
            int leaveId) {


        leaveDAO.updateLeaveStatus(
                leaveId,
                "APPROVED"
        );
    }


    // REJECT
    public void rejectLeave(
            int leaveId) {


        leaveDAO.updateLeaveStatus(
                leaveId,
                "REJECTED"
        );
    }


    // CALCULATE NUMBER OF DAYS
    public long calculateLeaveDays(
            LeaveRequest leave) {


        return ChronoUnit.DAYS.between(
                leave.getStartDate(),
                leave.getEndDate()
        ) + 1;
    }


    // BALANCE PER LEAVE TYPE, FOR THE OVERVIEW SCREEN
    public List<LeaveBalance> getLeaveBalances(int employeeId) {

        List<LeaveRequest> leaves = leaveDAO.getLeavesByEmployeeId(employeeId);

        List<LeaveBalance> balances = new java.util.ArrayList<>();

        for (Map.Entry<String, Integer> entry : ANNUAL_ALLOTMENT.entrySet()) {

            String type = entry.getKey();
            int total = entry.getValue();

            int used = leaves.stream()
                    .filter(l -> type.equalsIgnoreCase(l.getLeaveType()))
                    .filter(l -> "APPROVED".equalsIgnoreCase(l.getStatus()))
                    .mapToInt(l -> (int) calculateLeaveDays(l))
                    .sum();

            balances.add(new LeaveBalance(type, total, used));
        }

        return balances;
    }


    // MOST RECENT PENDING REQUEST, FOR THE OVERVIEW NOTICE
    public LeaveRequest getMostRecentPending(int employeeId) {

        List<LeaveRequest> leaves = leaveDAO.getLeavesByEmployeeId(employeeId);

        Optional<LeaveRequest> pending = leaves.stream()
                .filter(l -> "PENDING".equalsIgnoreCase(l.getStatus()))
                .max(Comparator.comparing(LeaveRequest::getLeaveId));

        return pending.orElse(null);
    }


    // FULL HISTORY, NEWEST FIRST
    public List<LeaveRequest> getHistorySortedByDate(int employeeId) {

        List<LeaveRequest> leaves = getEmployeeLeaves(employeeId);

        leaves.sort(
                Comparator.comparing(LeaveRequest::getStartDate).reversed()
        );

        return leaves;
    }
}