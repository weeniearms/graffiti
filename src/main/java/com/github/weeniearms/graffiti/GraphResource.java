package com.github.weeniearms.graffiti;

import com.github.weeniearms.graffiti.generator.GraphGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.Arrays;

@RestController
public class GraphResource {

    private final GraphGenerator[] generators;

    @Autowired
    public GraphResource(GraphGenerator[] generators) {
        this.generators = generators;
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

        GraphGenerator generator =
                Arrays.stream(generators)
                        .filter(g -> g.isSourceSupported(source))
                        .findFirst()
                        .orElseThrow(() -> new IllegalArgumentException("No matching generator found for source"));

        return generator.generateGraph(source, format);
    }

    @ExceptionHandler
    void handleIllegalArgumentException(IllegalArgumentException exception, HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.BAD_REQUEST.value());
    }
}
