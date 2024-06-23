package com.optimainmobiliaria.mortgage_credit_management.application.services;

import com.optimainmobiliaria.mortgage_credit_management.application.dtos.response.InstallmentResponseDto;
import com.optimainmobiliaria.mortgage_credit_management.domain.entities.Installment;
import com.optimainmobiliaria.shared.model.dto.api_response.ApiResponse;
import com.optimainmobiliaria.shared.model.dto.page_response.PageResponse;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface InstallmentService {
    ApiResponse<?> registerInstallment(List<Installment> installments);

    ApiResponse<PageResponse<InstallmentResponseDto>> getAllInstallmentsByCreditId(Pageable pageable, Long MortgageCreditId);

    ApiResponse<?> uploadVoucher(MultipartFile file, Long installmentId);

    ResponseEntity<Resource> downloadVoucher(Long installmentId);
}
