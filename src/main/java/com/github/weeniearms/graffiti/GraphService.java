package com.github.weeniearms.graffiti;

import com.github.weeniearms.graffiti.config.CacheConfiguration;
import com.github.weeniearms.graffiti.generator.GraphGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;

@Service
public class GraphService {

    private final GraphGenerator[] generators;

    @Autowired
    public GraphService(GraphGenerator[] generators) {
        this.generators = generators;
    }

    @Cacheable(CacheConfiguration.GRAPH)
    public byte[] generateGraph(String source, GraphGenerator.OutputFormat format) throws IOException {
        GraphGenerator generator =
                Arrays.stream(generators)
                        .filter(g -> g.isSourceSupported(source))
                        .findFirst()
                        .orElseThrow(() -> new IllegalArgumentException("No matching generator found for source"));

        return generator.generateGraph(source, format);
    }
}
