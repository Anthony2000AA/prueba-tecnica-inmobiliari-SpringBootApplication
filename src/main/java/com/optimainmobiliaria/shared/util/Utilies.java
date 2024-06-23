package com.optimainmobiliaria.shared.util;

import com.optimainmobiliaria.mortgage_credit_management.domain.entities.Installment;
import com.optimainmobiliaria.mortgage_credit_management.domain.entities.MortgageCredit;
import com.optimainmobiliaria.mortgage_credit_management.infraestructure.InstallmentRepository;
import com.optimainmobiliaria.mortgage_credit_management.infraestructure.MortgageCreditRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@Component
public class Utilies implements CommandLineRunner {

    private final InstallmentRepository installmentRepository;

    private final MortgageCreditRepository mortgageCreditRepository;

    public Utilies(InstallmentRepository installmentRepository, MortgageCreditRepository mortgageCreditRepository) {
        this.installmentRepository = installmentRepository;
        this.mortgageCreditRepository = mortgageCreditRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        initializeData();//GENERAR DATOS DE PRUEBA
    }

    private void initializeData() {
        if (mortgageCreditRepository.count() == 0) {
            Random random = new Random();
            for (int i = 0; i < 15; i++) {
                MortgageCredit mortgageCredit = new MortgageCredit();
                mortgageCredit.setClientName("CLIENTE " + i);
                mortgageCredit.setProjectName("PROYECTO " + i);

                LocalDate startDate = LocalDate.now().minusYears(1);
                LocalDate endDate = LocalDate.now();
                long startEpochDay = startDate.toEpochDay();
                long endEpochDay = endDate.toEpochDay();
                long randomEpochDay = ThreadLocalRandom.current().nextLong(startEpochDay, endEpochDay);
                LocalDate randomDate = LocalDate.ofEpochDay(randomEpochDay);
                mortgageCredit.setDate(randomDate);

                mortgageCredit.setPartialAmount(1000.0);
                mortgageCredit.setTotalAmount(2000.0);
                mortgageCredit.setMonthsRequested((int) (Math.random() * 11) + 10);
                mortgageCredit.setActive(true);
                mortgageCreditRepository.save(mortgageCredit);


                for (int j = 0; j < mortgageCredit.getMonthsRequested(); j++) {
                    Installment installment = new Installment();
                    installment.setPaymentDate(mortgageCredit.getDate().plusMonths(j+1));
                    installment.setPaid(false); // No pagado por defecto
                    installment.setAmount(100.0);
                    installment.setArchivePath(null); // Sin archivo adjunto por defecto
                    installment.setMortgageCredit(mortgageCredit);

                    mortgageCredit.getInstallments().add(installment);

                    installmentRepository.save(installment);
                }


            }
    }
    }
}
