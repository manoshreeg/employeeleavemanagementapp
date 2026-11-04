package com.example.employeemanagement.model;

/**
 * Not persisted. Computed on the fly by LeaveService to feed the
 * balance cards on the Leave Manager overview screen.
 */
public class LeaveBalance {

    private final String type;
    private final int totalDays;
    private final int usedDays;

    public LeaveBalance(String type, int totalDays, int usedDays) {
        this.type = type;
        this.totalDays = totalDays;
        this.usedDays = usedDays;
    }

    public String getType() {
        return type;
    }

    public int getTotalDays() {
        return totalDays;
    }

    public int getUsedDays() {
        return usedDays;
    }

    public int getRemainingDays() {
        return Math.max(0, totalDays - usedDays);
    }

    // Used to size the progress bar fill (0-100)
    public int getPercentRemaining() {
        if (totalDays <= 0) {
            return 0;
        }
        int percent = (int) Math.round((getRemainingDays() * 100.0) / totalDays);
        return Math.min(100, Math.max(0, percent));
    }
}
