package com.optimainmobiliaria.mortgage_credit_management.application.controllers;


import com.optimainmobiliaria.mortgage_credit_management.application.services.InstallmentService;
import com.optimainmobiliaria.shared.model.dto.api_response.ApiResponse;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/api/v1/installment")
public class InstallmentController {

    private final InstallmentService installmentService;

    public InstallmentController(InstallmentService installmentService) {
        this.installmentService = installmentService;
    }

    @GetMapping("/findByMortgageCreditId/{mortgageCreditId}")
    public ResponseEntity<ApiResponse<?>> getAllInstallmentsByCreditId( @PathVariable Long mortgageCreditId, @RequestParam int page, @RequestParam int size) {
        Pageable pageable = Pageable.ofSize(size).withPage(page);
        var response = installmentService.getAllInstallmentsByCreditId(pageable, mortgageCreditId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/{Id}/upload-voucher")
    public ResponseEntity<ApiResponse<?>> uploadVoucher(@RequestParam("file") MultipartFile file, @PathVariable Long Id) {
        var response = installmentService.uploadVoucher(file, Id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{Id}/download-voucher")
    public ResponseEntity<Resource> downloadVoucher(@PathVariable Long Id) {
        return installmentService.downloadVoucher(Id);
    }
}
