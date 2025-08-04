package com.reliaquest.api.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
@Schema(description = "Employee creation request")
public class CreateEmployeeInput {
    @Schema(description = "Employee name", example = "John Doe")
    @NotBlank(message = "Name is required")
    private String name;

    @Schema(description = "Employee salary", example = "75000")
    @NotNull(message = "Salary is required") @Positive(message = "Salary must be positive") private Integer salary;

    @Schema(description = "Employee age", example = "30")
    @NotNull(message = "Age is required") @Min(value = 16, message = "Age must be at least 16")
    @Max(value = 75, message = "Age must be at most 75")
    private Integer age;

    @Schema(description = "Employee job title", example = "Software Developer")
    @NotBlank(message = "Title is required")
    private String title;
}
