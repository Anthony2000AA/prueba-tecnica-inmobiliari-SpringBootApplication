package com.optimainmobiliaria.mortgage_credit_management.domain.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "installments")
public class Installment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "payment_date")
    private LocalDate paymentDate;

    @Column(name = "archive_path", nullable = true)
    private String archivePath;

    @Column(name = "paid")
    private Boolean paid;

    @Column(name = "amount")
    private Double amount;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "mortgage_credit_id", nullable = false)
    private MortgageCredit mortgageCredit;
}
