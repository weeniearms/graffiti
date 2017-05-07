package com.github.weeniearms.graffiti.generator;

public interface GraphGenerator {

    byte[] generateGraph(String source, OutputFormat format);

    enum OutputFormat {
        SVG,
        PNG
    }
}
