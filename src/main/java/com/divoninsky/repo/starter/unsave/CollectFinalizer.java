package com.divoninsky.repo.starter.unsave;

import lombok.SneakyThrows;
import org.apache.spark.sql.*;
import org.apache.spark.sql.types.DataType;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructField;
import org.springframework.stereotype.Component;

import javax.lang.model.type.ArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component("collect")
public class CollectFinalizer implements Finalizer {

    @Override
    @SneakyThrows
    public Object doAction(Dataset<Row> dataset, Class<?> modelClass, OrderedBag<?> orderedBag) {
        Encoder<?> encoder = Encoders.bean(modelClass);
//        List<String> listFieldNames = Arrays
//                .stream(encoder.schema().fields())
//                .filter(structField -> structField.dataType() instanceof ArrayType)
//                .map(StructField::name)
//                .collect(Collectors.toList());
//        for (String fieldName : listFieldNames) {
//            ParameterizedType genericType = (ParameterizedType) modelClass.getDeclaredField(fieldName).getGenericType();
//            Class c = (Class) genericType.getActualTypeArguments()[0];
//            dataset.withColumn(fieldName, functions.lit(null).cast(DataTypes.createStructType(Encoders.bean(c).schema().fields())));
//        }

        return dataset.as(encoder).collectAsList();
    }
}
