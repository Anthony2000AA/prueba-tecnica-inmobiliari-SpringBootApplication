package com.optimainmobiliaria.mortgage_credit_management.domain.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "mortgage_credit")
public class MortgageCredit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //nombre del cliente
    @Column(name = "client_name", nullable = false)
    private String clientName;

    @Column(name = "project_name", nullable = false)
    private String projectName;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "partial_amount", nullable = false)
    private Double partialAmount;

    @Column(name = "total_amount", nullable = false)
    private Double totalAmount;

    @Column(name = "months_requested", nullable = false)
    private Integer monthsRequested;

    @Column(name = "active", nullable = false)
    private Boolean active;

    @OneToMany(mappedBy = "mortgageCredit",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Installment> installments = new ArrayList<>();

}
