package com.github.weeniearms.graffiti;

import com.github.weeniearms.graffiti.generator.GraphGenerator;
import com.github.weeniearms.graffiti.generator.GraphGeneratorFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URLDecoder;

@RestController
public class GraphResource {

    private final GraphGeneratorFactory graphGeneratorFactory;

    @Autowired
    public GraphResource(GraphGeneratorFactory graphGeneratorFactory) {
        this.graphGeneratorFactory = graphGeneratorFactory;
    }

    @RequestMapping(value = "/svg", method = RequestMethod.GET, produces = "image/svg+xml")
    public byte[] svg(HttpServletRequest request) throws Exception {
        return generateGraph(request, GraphGenerator.OutputFormat.SVG);
    }

    @RequestMapping(value = "/png", method = RequestMethod.GET, produces = "image/png")
    public byte[] png(HttpServletRequest request) throws Exception {
        return generateGraph(request, GraphGenerator.OutputFormat.PNG);
    }

    private byte[] generateGraph(HttpServletRequest request, GraphGenerator.OutputFormat format) throws IOException {
        String source = URLDecoder.decode(request.getQueryString(), "UTF-8");
        return graphGeneratorFactory.create(source).generateGraph(source, format);
    }
}
