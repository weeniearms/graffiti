package com.github.weeniearms.graffiti.generator.plantuml;

import com.github.weeniearms.graffiti.generator.GraphGenerator;
import lombok.extern.slf4j.Slf4j;
import net.sourceforge.plantuml.FileFormat;
import net.sourceforge.plantuml.FileFormatOption;
import net.sourceforge.plantuml.SourceStringReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Slf4j
@Component
public class PlantUmlGraphGenerator implements GraphGenerator {

    private final PlantUmlSourceFormatter sourceFormatter;

    @Autowired
    public PlantUmlGraphGenerator(PlantUmlSourceFormatter sourceFormatter) {
        this.sourceFormatter = sourceFormatter;
    }

    @Override
    public boolean isSourceSupported(String source) {
        return source.trim().startsWith("@startuml");
    }

    @Override
    public byte[] generateGraph(String source, OutputFormat format) {
        String formattedSource = sourceFormatter.format(source);
        SourceStringReader reader = createSourceStringReader(formattedSource);
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            String desc = reader.generateImage(outputStream, new FileFormatOption(FileFormat.valueOf(format.name())));
            if (desc == null) {
                log.error("Failed to generate graph - null from reader returned");
                throw new RuntimeException("Failed to generate graph - null from reader returned");
            }
            return outputStream.toByteArray();
        } catch (IOException e) {
            log.error("Failed to generate graph", e);
            throw new RuntimeException("Failed to generate graph", e);
        }
    }

    protected SourceStringReader createSourceStringReader(String formattedSource) {
        return new SourceStringReader(formattedSource);
    }
}
