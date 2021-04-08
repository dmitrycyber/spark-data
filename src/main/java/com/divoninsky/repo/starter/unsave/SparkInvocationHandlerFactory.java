package com.divoninsky.repo.starter.unsave;

import com.divoninsky.repo.springdatabuilder.Source;
import com.divoninsky.repo.springdatabuilder.spark.SparkRepository;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.*;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class SparkInvocationHandlerFactory {
    private final DataExtractorResolver extractorResolver;
    private final Map<String, TransformationSpider> spiderMap;
    private final Map<String, Finalizer> finalizerMap;

    @Setter
    private ConfigurableApplicationContext realContext;

    public SparkInvocationHandler create(Class<? extends SparkRepository> repoInterface) {
        Class<?> modelClass = getModelClass(repoInterface);
        Set<String> fieldNames = getFieldsNames(modelClass);
        String pathToData = modelClass.getAnnotation(Source.class).value();
        DataExtractor dataExtractor = extractorResolver.resolve(pathToData);

        Map<Method, List<SparkTransformation>> transformationChain = new HashMap<>();
        Map<Method, Finalizer> method2Finalizer = new HashMap<>();

        Method[] methods = repoInterface.getMethods();
        for (Method method : methods) {
            TransformationSpider currentSpider = null;
            List<SparkTransformation> transformations = new ArrayList<>();
            List<String> methodWords = new ArrayList(Arrays.asList(method.getName().split("(?=\\p{Upper})")));

            while (methodWords.size() > 1){
                String strategyName = WordsMatcher.findAndRemoveMatchingPiecesIfExists(spiderMap.keySet(), methodWords);
                if (!strategyName.isEmpty()){
                    currentSpider = spiderMap.get(strategyName);
                }
                transformations.add(currentSpider.createTransformation(methodWords));
            }

            String finalizerName = "collect";
            if (methodWords.size() == 1){
                finalizerName = methodWords.get(0);
            }
            transformationChain.put(method, transformations);
            method2Finalizer.put(method, method2Finalizer.get(finalizerName));
        }

        return SparkInvocationHandlerImpl.builder()
                .modelClass(modelClass)
                .pathToData(pathToData)
                .finalizerMap(method2Finalizer)
                .transformationChain(transformationChain)
                .dataExtractor(dataExtractor)
                .context(realContext)
                .build();
    }

    private Class<?> getModelClass(Class<? extends SparkRepository> repoInterface) {
        ParameterizedType genericInterface = (ParameterizedType) repoInterface.getGenericInterfaces()[0];
        Class<?> modelClass = (Class<?>) genericInterface.getActualTypeArguments()[0];
        return modelClass;
    }

    private Set<String> getFieldsNames(Class<?> modelClass) {
        return Arrays.stream(modelClass.getDeclaredFields())
                .filter(field -> !field.isAnnotationPresent(Transient.class))
                .filter(field -> !Collection.class.isAssignableFrom(field.getType()))
                .map(Field::getName)
                .collect(Collectors.toSet());
    }
}
