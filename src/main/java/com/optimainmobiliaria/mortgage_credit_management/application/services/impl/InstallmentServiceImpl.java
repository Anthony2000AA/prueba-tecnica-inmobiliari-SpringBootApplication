package com.optimainmobiliaria.mortgage_credit_management.application.services.impl;

import com.optimainmobiliaria.mortgage_credit_management.application.dtos.response.InstallmentResponseDto;
import com.optimainmobiliaria.mortgage_credit_management.application.services.InstallmentService;
import com.optimainmobiliaria.mortgage_credit_management.domain.entities.Installment;
import com.optimainmobiliaria.mortgage_credit_management.infraestructure.InstallmentRepository;
import com.optimainmobiliaria.mortgage_credit_management.infraestructure.MortgageCreditRepository;
import com.optimainmobiliaria.shared.config.ModelMapperConfig;
import com.optimainmobiliaria.shared.exception.FileDownloadException;
import com.optimainmobiliaria.shared.exception.ResourceNotFoundException;
import com.optimainmobiliaria.shared.model.dto.api_response.ApiResponse;
import com.optimainmobiliaria.shared.model.dto.page_response.PageResponse;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class InstallmentServiceImpl implements InstallmentService {

    private final MortgageCreditRepository mortgageCreditRepository;
    private final InstallmentRepository installmentRepository;
    private final ModelMapperConfig modelMapperConfig;

    public InstallmentServiceImpl(MortgageCreditRepository mortgageCreditRepository, InstallmentRepository installmentRepository, ModelMapperConfig modelMapperConfig) {
        this.mortgageCreditRepository = mortgageCreditRepository;
        this.installmentRepository = installmentRepository;
        this.modelMapperConfig = modelMapperConfig;
    }

    public ApiResponse<?> registerInstallment(List<Installment>  installments) {
        installmentRepository.saveAll(installments);
        return new ApiResponse<>(true, "Installments registered", null);
    }

    public ApiResponse<PageResponse<InstallmentResponseDto>> getAllInstallmentsByCreditId(Pageable pageable, Long MortgageCreditId){
        var mortgageCredit = mortgageCreditRepository.findById(MortgageCreditId)
                .orElseThrow(() -> new ResourceNotFoundException("Mortgage Credit not found"));

        var installments = installmentRepository.findAllByMortgageCreditId(mortgageCredit.getId(), pageable);

        var installmentResponseDtos = installments.stream()
                .map(installment -> modelMapperConfig.modelMapper().map(installment, InstallmentResponseDto.class))
                .toList();

        PageResponse<InstallmentResponseDto> pageResponse = PageResponse.<InstallmentResponseDto>builder()
                .content(installmentResponseDtos)
                .pageNumber(pageable.getPageNumber())
                .pageSize(pageable.getPageSize())
                .totalPages(installments.getTotalPages())
                .totalElements(installments.getTotalElements())
                .build();

        return new ApiResponse<>(true, "Installments found", pageResponse);
    }

    public ApiResponse<?> uploadVoucher(MultipartFile file, Long installmentId) {
        var installment = installmentRepository.findById(installmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Installment not found"));

        try {
            String uploadDir = "uploads/installments/" + installmentId+"/";
            File dir = new File(uploadDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            // Verifica si el archivo existe y lo elimina, lo que significa que se está subiendo un nuevo archivo
            if (installment.getArchivePath() != null) {
                Path existingFilePath = Paths.get(installment.getArchivePath());
                Files.deleteIfExists(existingFilePath);
            }

            // Guarda el nuevo archivo
            String filePath = uploadDir + file.getOriginalFilename();
            Path path = Paths.get(filePath);
            Files.write(path, file.getBytes());

            // Actualiza la entidad Installment con la nueva ruta del archivo
            installment.setArchivePath(filePath);
            installmentRepository.save(installment);

            return new ApiResponse<>(true, "Voucher uploaded", null);
        } catch (IOException e) {
            return new ApiResponse<>(false, "Error", null);
        }
    }

    public ResponseEntity<Resource> downloadVoucher(Long installmentId) {
        var installment = installmentRepository.findById(installmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Installment not found"));
        try {
            Path path = Paths.get(installment.getArchivePath());
            if (!Files.exists(path)) {
                throw new ResourceNotFoundException("Voucher not found");
            }

            Resource resource = new UrlResource(path.toUri());

            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + resource.getFilename());
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(resource);
        } catch (IOException e) {
            throw new FileDownloadException("Error al descargar el archivo", e);
        }
    }
}
