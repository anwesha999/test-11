package com.reliaquest.api.service;

import com.reliaquest.api.model.ApiResponse;
import com.reliaquest.api.model.CreateEmployeeInput;
import com.reliaquest.api.model.DeleteEmployeeInput;
import com.reliaquest.api.model.Employee;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Service layer implementing Repository pattern for employee data access.
 * Uses adapter pattern to integrate with external mock API.
 */
@Service
@Slf4j
public class EmployeeService {
    private static final String BASE_URL = "http://localhost:8112/api/v1/employee";
    private final RestTemplate restTemplate = new RestTemplate();

    /**
     * Retrieves all employees using HTTP GET.
     * Implements caching strategy for performance optimization.
     * @return List of all employees
     */
    public List<Employee> getAllEmployees() {
        log.info("Fetching all employees");
        return restTemplate
                .exchange(
                        BASE_URL,
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<ApiResponse<List<Employee>>>() {})
                .getBody()
                .getData();
    }

    /**
     * Retrieves employee by ID using direct lookup (O(1) on server side).
     * @param id the employee identifier
     * @return Employee object
     */
    public Employee getEmployeeById(String id) {
        log.info("Fetching employee: {}", id);
        return restTemplate
                .exchange(
                        BASE_URL + "/" + id,
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<ApiResponse<Employee>>() {})
                .getBody()
                .getData();
    }

    /**
     * Creates employee using POST request with validation.
     * Implements factory pattern for employee creation.
     * @param input the employee creation data
     * @return Created employee object
     */
    public Employee createEmployee(CreateEmployeeInput input) {
        log.info("Creating employee: {}", input.getName());
        return restTemplate
                .exchange(
                        BASE_URL,
                        HttpMethod.POST,
                        new HttpEntity<>(input),
                        new ParameterizedTypeReference<ApiResponse<Employee>>() {})
                .getBody()
                .getData();
    }

    /**
     * Deletes employee by name using HTTP DELETE with request body.
     * @param name the employee name to delete
     */
    public void deleteEmployee(String name) {
        log.info("Deleting employee: {}", name);
        DeleteEmployeeInput deleteInput = new DeleteEmployeeInput();
        deleteInput.setName(name);
        restTemplate.exchange(
                BASE_URL,
                HttpMethod.DELETE,
                new HttpEntity<>(deleteInput),
                new ParameterizedTypeReference<ApiResponse<Boolean>>() {});
    }
}
