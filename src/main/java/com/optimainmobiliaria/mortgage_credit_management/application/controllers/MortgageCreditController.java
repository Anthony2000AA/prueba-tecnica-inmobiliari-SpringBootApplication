package com.optimainmobiliaria.mortgage_credit_management.application.controllers;

import com.optimainmobiliaria.mortgage_credit_management.application.dtos.request.MortgageCreditRequestDto;
import com.optimainmobiliaria.mortgage_credit_management.application.services.MortgageCreditService;
import com.optimainmobiliaria.shared.model.dto.api_response.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/v1/mortgage-credit")
public class MortgageCreditController {

    private final MortgageCreditService mortgageCreditService;

    public MortgageCreditController(MortgageCreditService mortgageCreditService) {
        this.mortgageCreditService = mortgageCreditService;
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<?>> registerMortgageCredit(@Valid @RequestBody MortgageCreditRequestDto mortgageCreditRequestDto) {
        var response = mortgageCreditService.registerMortgageCredit( mortgageCreditRequestDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/findAll")
    public ResponseEntity<ApiResponse<?>> getAllMortgageCredits( @RequestParam int page, @RequestParam int size) {
        Pageable pageable = Pageable.ofSize(size).withPage(page);
        var response = mortgageCreditService.getAllMortgageCredits(pageable);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/findByDateAndProjectName")
    public ResponseEntity<ApiResponse<?>> getMortgageCreditsByDateAndProjectName(@RequestParam(required = false) String startDate, @RequestParam(required = false) String endDate, @RequestParam(required = false) String projectName, @RequestParam int page, @RequestParam int size) {
        Pageable pageable = Pageable.ofSize(size).withPage(page);
        var response = mortgageCreditService.getMortgageCreditsByDateAndProjectName(startDate, endDate, projectName, pageable);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
