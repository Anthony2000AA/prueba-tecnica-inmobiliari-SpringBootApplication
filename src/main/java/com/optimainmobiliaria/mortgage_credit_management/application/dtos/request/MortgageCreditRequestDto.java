package com.optimainmobiliaria.mortgage_credit_management.application.dtos.request;


import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MortgageCreditRequestDto {

    @NotBlank(message = "Client name is required")
    private String clientName;

    @NotBlank(message = "Project name is required")
    private String projectName;

    @NotNull(message = "Date is required")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    @NotNull(message = "Partial amount is required")
    private Double partialAmount;

    @NotNull(message = "Total amount is required")
    private Double totalAmount;

    @NotNull(message = "Months requested is required")
    private Integer monthsRequested;
}
