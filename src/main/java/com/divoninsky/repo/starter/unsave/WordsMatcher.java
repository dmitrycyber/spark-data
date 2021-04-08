package com.divoninsky.repo.starter.unsave;

import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

public class WordsMatcher {
    public static String findAndRemoveMatchingPiecesIfExists(Set<String> options, List<String> pieces){
        StringBuilder match = new StringBuilder(pieces.remove(0));

        List<String> remainingOptions = options.stream()
                .filter(option -> option.toLowerCase(Locale.ROOT).startsWith(match.toString().toLowerCase(Locale.ROOT)))
                .collect(Collectors.toList());

        if (remainingOptions.isEmpty()){
            return "";
        }

        while (remainingOptions.size() > 1){
            match.append(pieces.remove(0));
            remainingOptions.removeIf(option -> !option.toLowerCase(Locale.ROOT).startsWith(match.toString().toLowerCase(Locale.ROOT)));
        }

        while (remainingOptions.get(0).equalsIgnoreCase(match.toString())){
            match.append(pieces.remove(0));
        }

        return match.toString();
    }
}
