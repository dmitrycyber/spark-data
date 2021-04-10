package com.divoninsky.repo.springdatabuilder;

import com.divoninsky.repo.springdatabuilder.spark.CriminalRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.List;

@SpringBootApplication
public class SpringDataBuilderApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(SpringDataBuilderApplication.class, args);

        CriminalRepository criminalRepository = context.getBean(CriminalRepository.class);

        List<Criminal> criminals = criminalRepository.findByNumberBetween(20, 50);

        for (Criminal criminal : criminals) {
            System.out.println("criminal = " + criminal);
        }
        System.out.println();
        System.out.println("-------------------");
        System.out.println();

        List<Criminal> byNumberGreaterThan = criminalRepository.findByNumberGreaterThan(20);

        for (Criminal criminal : byNumberGreaterThan) {
            System.out.println("greater = " + criminal);
        }
    }
}