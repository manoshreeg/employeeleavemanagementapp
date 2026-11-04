package com.example.employeemanagement.controller;

import com.example.employeemanagement.model.Employee;
import com.example.employeemanagement.model.LeaveRequest;
import com.example.employeemanagement.service.EmployeeService;
import com.example.employeemanagement.service.LeaveService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Renders the Leave Manager HTML page (Thymeleaf) and handles the
 * apply-leave form submission. This is separate from LeaveController,
 * which stays a JSON REST API. There's no login screen here, so the
 * employeeId query param stands in for "the signed-in employee".
 */
@Controller
public class LeaveViewController {

    private final LeaveService leaveService;
    private final EmployeeService employeeService;

    public LeaveViewController(LeaveService leaveService, EmployeeService employeeService) {
        this.leaveService = leaveService;
        this.employeeService = employeeService;
    }

    @GetMapping("/")
    public String home() {
        return "redirect:/leave?employeeId=1";
    }

    @GetMapping("/leave")
    public String viewLeavePage(
            @RequestParam(defaultValue = "1") int employeeId,
            @RequestParam(defaultValue = "overview") String tab,
            Model model) {

        Employee employee = employeeService.getEmployeeById(employeeId);

        model.addAttribute("employee", employee);
        model.addAttribute("tab", tab);
        model.addAttribute("initials", initialsOf(employee.getName()));
        model.addAttribute("balances", leaveService.getLeaveBalances(employeeId));
        model.addAttribute("pendingLeave", leaveService.getMostRecentPending(employeeId));
        model.addAttribute("history", leaveService.getHistorySortedByDate(employeeId));

        if (!model.containsAttribute("leaveRequest")) {
            LeaveRequest blank = new LeaveRequest();
            blank.setEmployeeId(employeeId);
            model.addAttribute("leaveRequest", blank);
        }

        return "leave";
    }

    @PostMapping("/leave/apply")
    public String applyLeave(
            @ModelAttribute("leaveRequest") LeaveRequest leaveRequest,
            RedirectAttributes redirectAttributes) {

        try {
            leaveService.applyLeave(leaveRequest);
            redirectAttributes.addFlashAttribute(
                    "successMessage", "Leave request submitted."
            );
        } catch (RuntimeException exception) {
            redirectAttributes.addFlashAttribute(
                    "errorMessage", exception.getMessage()
            );
        }

        return "redirect:/leave?employeeId=" + leaveRequest.getEmployeeId() + "&tab=apply";
    }

    private String initialsOf(String name) {
        if (name == null || name.isBlank()) {
            return "?";
        }
        String[] parts = name.trim().split("\\s+");
        String first = parts[0].substring(0, 1);
        String second = parts.length > 1 ? parts[1].substring(0, 1) : "";
        return (first + second).toUpperCase();
    }
}
