package com.reliaquest.api.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import com.reliaquest.api.model.ApiResponse;
import com.reliaquest.api.model.Employee;
import java.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {
    @Mock
    private RestTemplate restTemplate;

    private EmployeeService employeeService;

    @BeforeEach
    void setUp() throws Exception {
        employeeService = new EmployeeService();
        java.lang.reflect.Field field = EmployeeService.class.getDeclaredField("restTemplate");
        field.setAccessible(true);
        field.set(employeeService, restTemplate);
    }

    @Test
    void getAllEmployees() {
        ApiResponse<java.util.List<Employee>> response = new ApiResponse<>();
        Employee emp = new Employee();
        emp.setName("John");
        response.setData(Arrays.asList(emp));

        when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), any(), any(ParameterizedTypeReference.class)))
                .thenReturn(ResponseEntity.ok(response));

        assertEquals(1, employeeService.getAllEmployees().size());
    }
}
