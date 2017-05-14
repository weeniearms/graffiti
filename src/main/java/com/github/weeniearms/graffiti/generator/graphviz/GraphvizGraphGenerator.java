package com.github.weeniearms.graffiti.generator.graphviz;

import com.github.weeniearms.graffiti.generator.GraphGenerator;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class GraphvizGraphGenerator implements GraphGenerator {

    private final String executable;
    private final int timeout;

    public GraphvizGraphGenerator(@Value("${graphviz.executable}") String executable, @Value("${graphviz.timeout:2000}") int timeout) {
        this.executable = executable;
        this.timeout = timeout;
    }

    @Override
    public boolean isSourceSupported(String source) {
        return source.trim().startsWith("graph") || source.trim().startsWith("digraph");
    }

    @Override
    public byte[] generateGraph(String source, OutputFormat format) {
        File sourceFile = writeSourceToFile(source);

        try {
            File outputFile = File.createTempFile("graph_", "." + format.name());

            try {
                String[] args = {
                        executable,
                        "-T" + format.name().toLowerCase(),
                        sourceFile.getAbsolutePath(),
                        "-o",
                        outputFile.getAbsolutePath()
                };
                Process dotProcess = Runtime.getRuntime().exec(args);
                dotProcess.waitFor(timeout, TimeUnit.MILLISECONDS);


                switch (format) {
                    case PNG:
                        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                        ImageIO.write(ImageIO.read(outputFile), format.name(), outputStream);
                        return outputStream.toByteArray();
                    default:
                        return FileUtils.readFileToByteArray(outputFile);
                }
            } finally {
                deleteTemporaryFile(outputFile);
            }
        } catch (Exception e) {
            log.error("Failed to generate graph", e);
            throw new RuntimeException("Failed to generate graph", e);
        } finally {
            deleteTemporaryFile(sourceFile);
        }
    }

    private void deleteTemporaryFile(File temporaryFile) {
        if (!temporaryFile.delete()) {
            log.warn("Failed to delete temporary file {}", temporaryFile.getAbsolutePath());
        }
    }

    private File writeSourceToFile(String source) {
        try {
            File sourceFile = File.createTempFile("graph_", ".dot");

            try (FileWriter fileWriter = new FileWriter(sourceFile)) {
                IOUtils.write(source, fileWriter);
            }

            return sourceFile;
        } catch (IOException e) {
            log.error("Failed to write source to temporary file", e);
            throw new RuntimeException("Failed to write source to temporary file", e);
        }
    }
}
