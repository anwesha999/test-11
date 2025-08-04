package com.reliaquest.api.controller;

import com.reliaquest.api.model.CreateEmployeeInput;
import com.reliaquest.api.model.Employee;
import com.reliaquest.api.service.EmployeeService;
import com.reliaquest.api.util.EmployeeUtils;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
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
public class EmployeeController implements IEmployeeController<Employee, CreateEmployeeInput> {
    private final EmployeeService employeeService;

    /**
     * Retrieves all employees from the system.
     * @return ResponseEntity containing list of all employees
     */
    @Override
    public ResponseEntity<List<Employee>> getAllEmployees() {
        log.info("Getting all employees");
        return ResponseEntity.ok(employeeService.getAllEmployees());
    }

    /**
     * Searches employees by name fragment using Boyer-Moore-like filtering.
     * @param searchString the name fragment to search for
     * @return ResponseEntity containing filtered list of employees
     */
    @Override
    public ResponseEntity<List<Employee>> getEmployeesByNameSearch(String searchString) {
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

    /**
     * Creates a new employee using the builder pattern internally.
     * @param employeeInput the employee data to create
     * @return ResponseEntity containing the created employee
     */
    @Override
    public ResponseEntity<Employee> createEmployee(CreateEmployeeInput employeeInput) {
        log.info("Creating employee: {}", employeeInput.getName());
        return ResponseEntity.ok(employeeService.createEmployee(employeeInput));
    }

    /**
     * Deletes an employee by ID using two-phase commit pattern.
     * @param id the employee identifier to delete
     * @return ResponseEntity containing the deleted employee's name
     */
    @Override
    public ResponseEntity<String> deleteEmployeeById(String id) {
        log.info("Deleting employee: {}", id);
        Employee employee = employeeService.getEmployeeById(id);
        employeeService.deleteEmployee(employee.getName());
        return ResponseEntity.ok(employee.getName());
    }
}
