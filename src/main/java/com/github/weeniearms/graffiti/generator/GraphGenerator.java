package com.github.weeniearms.graffiti.generator;

public interface GraphGenerator {

    boolean isSourceSupported(String source);

    byte[] generateGraph(String source, OutputFormat format);

    enum OutputFormat {
        SVG,
        PNG
    }
}
