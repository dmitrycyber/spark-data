package com.divoninsky.repo.starter.unsave;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Component("findBy")
@RequiredArgsConstructor
public class FilterTransformationSpider implements TransformationSpider {
    private final Map<String, FilterTransformation> transformationMap;

    @Override
    public SparkTransformation createTransformation(List<String> remainingWords, Set<String> fieldNames) {
        String fieldName = WordsMatcher.findAndRemoveMatchingPiecesIfExists(fieldNames, remainingWords);
        String filterName = WordsMatcher.findAndRemoveMatchingPiecesIfExists(transformationMap.keySet(), remainingWords);
        return transformationMap.get(fieldName);
    }
}
