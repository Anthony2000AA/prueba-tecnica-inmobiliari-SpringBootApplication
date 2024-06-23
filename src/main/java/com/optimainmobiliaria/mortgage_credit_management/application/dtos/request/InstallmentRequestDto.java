package com.optimainmobiliaria.mortgage_credit_management.application.dtos.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InstallmentRequestDto {

    @NotNull(message = "Mortgage credit id is required")
    private Double amount;

    @NotNull(message = "Payment date is required")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate paymentDate;

}
