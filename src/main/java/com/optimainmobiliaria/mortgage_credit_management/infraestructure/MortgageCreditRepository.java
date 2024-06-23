package com.optimainmobiliaria.mortgage_credit_management.infraestructure;

import com.optimainmobiliaria.mortgage_credit_management.domain.entities.MortgageCredit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface MortgageCreditRepository extends JpaRepository<MortgageCredit, Long> {

    Page<MortgageCredit> findAllByDateBetweenAndProjectNameContaining(LocalDate date, LocalDate date2, String projectName, Pageable pageable);
}
