package com.divoninsky.repo.starter.unsave;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.functions;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("between")
public class BetweenFilter implements FilterTransformation {

    @Override
    public Dataset<Row> transform(Dataset<Row> dataset, List<String> fieldNames, List<Object> args) {
        dataset
                .filter(functions
                        .col(fieldNames.get(0))
                        .between(args.get(0), args.get(1)))

        return null;
    }
}
