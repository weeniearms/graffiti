package com.github.weeniearms.graffiti.generator;

import org.springframework.stereotype.Component;

@Component
public class PlantUmlSourceFormatter {
    public String format(String source) {
        return source.replaceAll(";\\s*", "\n");
    }
}
