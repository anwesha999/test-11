package com.reliaquest.api.controller;

import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.reliaquest.api.model.CreateEmployeeInput;
import com.reliaquest.api.model.Employee;
import com.reliaquest.api.security.SecurityConfig;
import com.reliaquest.api.service.EmployeeService;
import java.util.Arrays;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(EmployeeController.class)
@Import(SecurityConfig.class)
class EmployeeControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService employeeService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getAllEmployees() throws Exception {
        Employee emp = new Employee();
        emp.setId("1");
        emp.setName("John");
        when(employeeService.getAllEmployees()).thenReturn(Arrays.asList(emp));

        mockMvc.perform(get("/api/v1/employee").with(httpBasic("admin", "secure123")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("1"));
    }

    @Test
    void createEmployee() throws Exception {
        CreateEmployeeInput input = new CreateEmployeeInput();
        input.setName("John");
        input.setSalary(50000);
        input.setAge(25);
        input.setTitle("Developer");
        Employee emp = new Employee();
        emp.setName("John");
        when(employeeService.createEmployee(any())).thenReturn(emp);

        mockMvc.perform(post("/api/v1/employee")
                        .with(httpBasic("admin", "secure123"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isOk());
    }
}
