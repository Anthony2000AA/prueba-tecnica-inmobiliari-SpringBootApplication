package com.optimainmobiliaria.mortgage_credit_management.infraestructure;

import com.optimainmobiliaria.mortgage_credit_management.domain.entities.Installment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InstallmentRepository extends JpaRepository<Installment, Long>{

    Page<Installment> findAllByMortgageCreditId(Long mortgageCreditId, Pageable pageable);
}
