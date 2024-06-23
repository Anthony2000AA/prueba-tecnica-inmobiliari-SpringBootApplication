package com.optimainmobiliaria.mortgage_credit_management.application.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InstallmentResponseDto {

        private Long id;
        private Long mortgageCreditId;
        private Double amount;
        private Boolean paid;
        private String paymentDate;
        private String archivePath;

}
