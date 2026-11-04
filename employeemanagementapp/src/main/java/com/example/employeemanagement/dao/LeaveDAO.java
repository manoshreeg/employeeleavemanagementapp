package com.example.employeemanagement.dao;

import com.example.employeemanagement.model.LeaveRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;

import java.util.ArrayList;
import java.util.List;

@Repository
public class LeaveDAO {

    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;


    // APPLY FOR LEAVE
    public void applyLeave(
            LeaveRequest leave) {


        String sql =
                "INSERT INTO leave_requests " +
                        "(employee_id, leave_type, start_date, " +
                        "end_date, reason, status) " +
                        "VALUES (?, ?, ?, ?, ?, ?)";


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


            statement.setInt(
                    1,
                    leave.getEmployeeId()
            );


            statement.setString(
                    2,
                    leave.getLeaveType()
            );


            statement.setDate(
                    3,
                    Date.valueOf(
                            leave.getStartDate()
                    )
            );


            statement.setDate(
                    4,
                    Date.valueOf(
                            leave.getEndDate()
                    )
            );


            statement.setString(
                    5,
                    leave.getReason()
            );


            statement.setString(
                    6,
                    "PENDING"
            );


            statement.executeUpdate();


        } catch (SQLException e) {

            throw new RuntimeException(
                    "Error applying leave",
                    e
            );
        }
    }


    // GET LEAVES FOR AN EMPLOYEE
    public List<LeaveRequest>
    getLeavesByEmployeeId(int employeeId) {


        List<LeaveRequest> leaves =
                new ArrayList<>();


        String sql =
                "SELECT * FROM leave_requests " +
                        "WHERE employee_id = ?";


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


            statement.setInt(
                    1,
                    employeeId
            );


            try (
                    ResultSet resultSet =
                            statement.executeQuery()
            ) {


                while (resultSet.next()) {

                    LeaveRequest leave =
                            new LeaveRequest();


                    leave.setLeaveId(
                            resultSet.getInt(
                                    "leave_id"
                            )
                    );


                    leave.setEmployeeId(
                            resultSet.getInt(
                                    "employee_id"
                            )
                    );


                    leave.setLeaveType(
                            resultSet.getString(
                                    "leave_type"
                            )
                    );


                    leave.setStartDate(
                            resultSet
                                    .getDate("start_date")
                                    .toLocalDate()
                    );


                    leave.setEndDate(
                            resultSet
                                    .getDate("end_date")
                                    .toLocalDate()
                    );


                    leave.setReason(
                            resultSet.getString(
                                    "reason"
                            )
                    );


                    leave.setStatus(
                            resultSet.getString(
                                    "status"
                            )
                    );


                    leaves.add(leave);
                }
            }


        } catch (SQLException e) {

            throw new RuntimeException(
                    "Error retrieving leave requests",
                    e
            );
        }


        return leaves;
    }


    // UPDATE LEAVE STATUS
    public void updateLeaveStatus(
            int leaveId,
            String status) {


        String sql =
                "UPDATE leave_requests " +
                        "SET status = ? " +
                        "WHERE leave_id = ?";


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
                    status
            );


            statement.setInt(
                    2,
                    leaveId
            );


            statement.executeUpdate();


        } catch (SQLException e) {

            throw new RuntimeException(
                    "Error updating leave status",
                    e
            );
        }
    }
}