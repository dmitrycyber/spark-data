package com.divoninsky.repo.starter.unsave;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.functions;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("sort")
public class SortTransformation implements SparkTransformation {

    @Override
    public Dataset<Row> transform(Dataset<Row> dataset, List<String> fieldNames, OrderedBag<Object> args) {
//        dataset
//                .orderBy(functions
//                        .col(fieldNames.remove(0))
//                        , fieldNames
//                                .stream()
//                                .skip(1)
//                                .toArray(String[]::new))

        return null;
    }
}
