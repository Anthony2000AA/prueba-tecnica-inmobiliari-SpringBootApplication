package com.optimainmobiliaria.mortgage_credit_management.application.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MortgageCreditResponseDto {

    private Long id;
    private String clientName;
    private String projectName;
    private LocalDate date;
    private Double partialAmount;
    private Double totalAmount;
    private Integer monthsRequested;
    private Integer monthsPaid;
    private Boolean active;
}
