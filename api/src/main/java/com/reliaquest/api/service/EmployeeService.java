package com.reliaquest.api.service;

import com.reliaquest.api.model.ApiResponse;
import com.reliaquest.api.model.CreateEmployeeInput;
import com.reliaquest.api.model.Employee;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class EmployeeService {
    private static final Logger log = LoggerFactory.getLogger(EmployeeService.class);
    private static final String BASE_URL = "http://localhost:8112/api/v1/employee";
    private final RestTemplate restTemplate = new RestTemplate();

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

    public void deleteEmployee(String name) {
        log.info("Deleting employee: {}", name);
        restTemplate.delete(BASE_URL + "/" + name);
    }
}
