package com.divoninsky.repo.springdatabuilder.spark;

import com.divoninsky.repo.springdatabuilder.Criminal;

import java.util.List;

public interface CriminalRepository extends SparkRepository<Criminal> {

    List<Criminal> findByNumberBetween(int min, int max);
}
