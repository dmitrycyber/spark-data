package com.divoninsky.repo.starter.unsave;

import lombok.Builder;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.springframework.context.ConfigurableApplicationContext;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

@Builder
public class SparkInvocationHandlerImpl implements SparkInvocationHandler {
    private Class<?> modelClass;
    private String pathToData;
    private DataExtractor dataExtractor;
    private Map<Method, List<SparkTransformation>> transformationChain;
    private Map<Method, Finalizer> finalizerMap;

    private ConfigurableApplicationContext context;

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Dataset<Row> dataset = dataExtractor.readData(pathToData, context);

        List<SparkTransformation> sparkTransformations = transformationChain.get(method);
        for (SparkTransformation transformation : sparkTransformations) {
            dataset = transformation.transform(dataset);
        }

        Finalizer finalizer = finalizerMap.get(method);
        Object retVal = finalizer.doAction(dataset);

        return retVal;
    }
}