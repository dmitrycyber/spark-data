package com.divoninsky.repo.springdatabuilder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Source("data/criminals.csv")
public class Criminal {
    private long id;
    private String name;
    private int number;
}
