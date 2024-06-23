package com.optimainmobiliaria.mortgage_credit_management.application.services.impl;

import com.optimainmobiliaria.mortgage_credit_management.application.dtos.request.MortgageCreditRequestDto;
import com.optimainmobiliaria.mortgage_credit_management.application.dtos.response.MortgageCreditResponseDto;
import com.optimainmobiliaria.mortgage_credit_management.application.services.MortgageCreditService;
import com.optimainmobiliaria.mortgage_credit_management.domain.entities.Installment;
import com.optimainmobiliaria.mortgage_credit_management.domain.entities.MortgageCredit;
import com.optimainmobiliaria.mortgage_credit_management.infraestructure.MortgageCreditRepository;
import com.optimainmobiliaria.shared.config.ModelMapperConfig;
import com.optimainmobiliaria.shared.model.dto.api_response.ApiResponse;
import com.optimainmobiliaria.shared.model.dto.page_response.PageResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class MortgageCreditServiceImpl implements MortgageCreditService {

    private final MortgageCreditRepository mortgageCreditRepository;
    private final InstallmentServiceImpl installmentService;
    private final ModelMapperConfig modelMapperConfig;


    public MortgageCreditServiceImpl(MortgageCreditRepository mortgageCreditRepository, InstallmentServiceImpl installmentService, ModelMapperConfig modelMapperConfig) {
        this.mortgageCreditRepository = mortgageCreditRepository;
        this.installmentService = installmentService;
        this.modelMapperConfig = modelMapperConfig;
    }

    @Override
    public ApiResponse<MortgageCreditResponseDto> registerMortgageCredit(MortgageCreditRequestDto mortgageCreditRequestDto) {

        var mortgageCredit = modelMapperConfig.modelMapper().map(mortgageCreditRequestDto, MortgageCredit.class);
        mortgageCredit.setActive(true);

        var mortgageSave = mortgageCreditRepository.save(mortgageCredit);

        List<Installment> installments = new ArrayList<>();
        for(int i = 0; i < mortgageSave.getMonthsRequested() ; i++){
            Installment installment = new Installment();
            installment.setMortgageCredit(mortgageSave);
            installment.setPaid(false);
            installment.setAmount(mortgageSave.getTotalAmount() / mortgageSave.getMonthsRequested());
            installment.setArchivePath(null);
            installment.setPaymentDate(mortgageSave.getDate().plusMonths(i+1));
            installments.add(installment);
        }

        installmentService.registerInstallment(installments);

        return new ApiResponse<>(true, "Mortgage Credit registered", null);

    }

    @Override
    public ApiResponse<PageResponse<MortgageCreditResponseDto>> getAllMortgageCredits(Pageable pageable) {

          var mortgageCredits = mortgageCreditRepository.findAll(pageable);

            var mortgageCreditResponseDtos = mortgageCredits.stream()
                    .map(mortgageCredit -> modelMapperConfig.modelMapper().map(mortgageCredit, MortgageCreditResponseDto.class))
                    .toList();

            PageResponse<MortgageCreditResponseDto> pageResponse = PageResponse.<MortgageCreditResponseDto>builder()
                    .content(mortgageCreditResponseDtos)
                    .currentPage(mortgageCredits.getNumber())
                    .pageSize(mortgageCredits.getSize())
                    .totalElements(mortgageCredits.getTotalElements())
                    .totalPages(mortgageCredits.getTotalPages())
                    .pageNumber(mortgageCredits.getNumber())
                    .build();

            return new ApiResponse<>(true, "Mortgage Credits list", pageResponse);

    }

    @Override
    public ApiResponse<PageResponse<MortgageCreditResponseDto>> getMortgageCreditsByDateAndProjectName(String startDate, String endDate, String projectName, Pageable pageable){

        LocalDate date = LocalDate.parse(startDate);
        LocalDate date2 = LocalDate.parse(endDate);

        var mortgageCredits = mortgageCreditRepository.findAllByDateBetweenAndProjectNameContaining(date, date2, projectName, pageable);


        var mortgageCreditResponseDtos = mortgageCredits.stream()
                .map(mortgageCredit -> modelMapperConfig.modelMapper().map(mortgageCredit, MortgageCreditResponseDto.class))
                .toList();

        PageResponse<MortgageCreditResponseDto> pageResponse = PageResponse.<MortgageCreditResponseDto>builder()
                .content(mortgageCreditResponseDtos)
                .currentPage(mortgageCredits.getNumber())
                .pageSize(mortgageCredits.getSize())
                .totalElements(mortgageCredits.getTotalElements())
                .totalPages(mortgageCredits.getTotalPages())
                .pageNumber(mortgageCredits.getNumber())
                .build();

        return new ApiResponse<>(true, "Mortgage Credits list", pageResponse);
    }

    @Override
    public void registerInstallments(List<Installment> installments) {
        installmentService.registerInstallment(installments);
    }
}
