package com.divoninsky.repo.starter.unsave;

import java.util.List;
import java.util.Set;

public interface TransformationSpider {

    SparkTransformation createTransformation(List<String> remainingWords, Set<String> fieldNames);
}
