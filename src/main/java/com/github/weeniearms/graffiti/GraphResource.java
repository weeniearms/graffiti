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

@RestController
public class GraphResource {

    private final GraphService graphService;

    @Autowired
    public GraphResource(GraphService graphService) {
        this.graphService = graphService;
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

        return graphService.generateGraph(source, format);
    }

    @ExceptionHandler
    void handleIllegalArgumentException(IllegalArgumentException exception, HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.BAD_REQUEST.value());
    }
}
