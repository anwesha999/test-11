package com.reliaquest.api.controller;

import com.reliaquest.api.model.CreateEmployeeInput;
import com.reliaquest.api.model.Employee;
import com.reliaquest.api.service.EmployeeService;
import com.reliaquest.api.util.EmployeeUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for employee operations.
 * Implements facade pattern to provide unified interface to employee services.
 */
@RestController
@RequestMapping("/api/v1/employee")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Employee Management", description = "APIs for managing employee data")
@SecurityRequirement(name = "basicAuth")
public class EmployeeController implements IEmployeeController<Employee, CreateEmployeeInput> {
    private final EmployeeService employeeService;

    @Operation(summary = "Get all employees", description = "Retrieves all employees from the system")
    @ApiResponses(
            value = {
                @ApiResponse(responseCode = "200", description = "Successfully retrieved employees"),
                @ApiResponse(responseCode = "401", description = "Authentication required")
            })
    @Override
    public ResponseEntity<List<Employee>> getAllEmployees() {
        log.info("Getting all employees");
        return ResponseEntity.ok(employeeService.getAllEmployees());
    }

    @Operation(summary = "Search employees by name", description = "Searches employees by name fragment")
    @ApiResponses(
            value = {
                @ApiResponse(responseCode = "200", description = "Successfully retrieved matching employees"),
                @ApiResponse(responseCode = "401", description = "Authentication required")
            })
    @Override
    public ResponseEntity<List<Employee>> getEmployeesByNameSearch(
            @Parameter(description = "Name fragment to search for") String searchString) {
        log.info("Searching employees: {}", searchString);
        return ResponseEntity.ok(EmployeeUtils.searchByName(employeeService.getAllEmployees(), searchString));
    }

    /**
     * Retrieves a single employee by ID.
     * @param id the employee identifier
     * @return ResponseEntity containing the employee
     */
    @Override
    public ResponseEntity<Employee> getEmployeeById(String id) {
        log.info("Getting employee: {}", id);
        return ResponseEntity.ok(employeeService.getEmployeeById(id));
    }

    /**
     * Finds the highest salary using stream reduction (O(n) complexity).
     * @return ResponseEntity containing the maximum salary
     */
    @Override
    public ResponseEntity<Integer> getHighestSalaryOfEmployees() {
        log.info("Getting highest salary");
        return ResponseEntity.ok(EmployeeUtils.findMaxSalary(employeeService.getAllEmployees()));
    }

    /**
     * Gets top 10 highest earning employees using partial sorting (O(n log k) where k=10).
     * Uses priority queue internally for optimal performance.
     * @return ResponseEntity containing list of top 10 employee names
     */
    @Override
    public ResponseEntity<List<String>> getTopTenHighestEarningEmployeeNames() {
        log.info("Getting top 10 earners");
        return ResponseEntity.ok(EmployeeUtils.getTopKEarners(employeeService.getAllEmployees(), 10));
    }

    @Operation(summary = "Create employee", description = "Creates a new employee (Admin only)")
    @ApiResponses(
            value = {
                @ApiResponse(responseCode = "200", description = "Employee created successfully"),
                @ApiResponse(responseCode = "400", description = "Invalid input data"),
                @ApiResponse(responseCode = "401", description = "Authentication required"),
                @ApiResponse(responseCode = "403", description = "Admin role required")
            })
    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Employee> createEmployee(
            @Parameter(description = "Employee data to create") @Valid CreateEmployeeInput employeeInput) {
        log.info("Creating employee: {}", employeeInput.getName());
        return ResponseEntity.ok(employeeService.createEmployee(employeeInput));
    }

    @Operation(summary = "Delete employee", description = "Deletes an employee by ID (Admin only)")
    @ApiResponses(
            value = {
                @ApiResponse(responseCode = "200", description = "Employee deleted successfully"),
                @ApiResponse(responseCode = "401", description = "Authentication required"),
                @ApiResponse(responseCode = "403", description = "Admin role required"),
                @ApiResponse(responseCode = "404", description = "Employee not found")
            })
    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteEmployeeById(@Parameter(description = "Employee ID to delete") String id) {
        log.info("Deleting employee: {}", id);
        try {
            Employee employee = employeeService.getEmployeeById(id);
            String employeeName = employee.getName();
            employeeService.deleteEmployee(employeeName);
            log.info("Successfully deleted employee: {}", employeeName);
            return ResponseEntity.ok(employeeName);
        } catch (Exception e) {
            log.error("Failed to delete employee with id: {}", id, e);
            throw e;
        }
    }
}
