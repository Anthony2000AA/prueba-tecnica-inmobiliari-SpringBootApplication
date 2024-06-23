package com.optimainmobiliaria.mortgage_credit_management.application.services;

import com.optimainmobiliaria.mortgage_credit_management.application.dtos.request.MortgageCreditRequestDto;
import com.optimainmobiliaria.mortgage_credit_management.application.dtos.response.MortgageCreditResponseDto;
import com.optimainmobiliaria.mortgage_credit_management.domain.entities.Installment;
import com.optimainmobiliaria.shared.model.dto.api_response.ApiResponse;
import com.optimainmobiliaria.shared.model.dto.page_response.PageResponse;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

public interface MortgageCreditService {

    ApiResponse<MortgageCreditResponseDto> registerMortgageCredit(MortgageCreditRequestDto mortgageCreditRequestDto);

    ApiResponse<PageResponse<MortgageCreditResponseDto>> getAllMortgageCredits(Pageable pageable);

    ApiResponse<PageResponse<MortgageCreditResponseDto>> getMortgageCreditsByDateAndProjectName(String startDate, String endDate, String projectName, Pageable pageable);

    void registerInstallments(List<Installment> installments);
}
