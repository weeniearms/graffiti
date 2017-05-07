package com.github.weeniearms.graffiti.generator;

import net.sourceforge.plantuml.FileFormat;
import net.sourceforge.plantuml.FileFormatOption;
import net.sourceforge.plantuml.SourceStringReader;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.IOException;
import java.io.OutputStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PlantUmlGraphGeneratorTest {

    public static final String FORMATTED = "formatted";
    @Spy
    @InjectMocks
    private PlantUmlGraphGenerator generator;

    @Mock
    private PlantUmlSourceFormatter formatter;

    @Mock
    private SourceStringReader reader;

    @Before
    public void setUp() {
        when(generator.createSourceStringReader(anyString())).thenReturn(reader);
        when(formatter.format(anyString())).thenReturn(FORMATTED);
    }

    @Test
    public void shouldReturnGeneratedBytes() throws Exception {
        // Given
        byte[] image = new byte[1];
        when(reader.generateImage(any(OutputStream.class), any(FileFormatOption.class)))
                .thenAnswer(invocationOnMock -> {
                    invocationOnMock.getArgumentAt(0, OutputStream.class).write(image);
                    return "success";
                });

        // When
        byte[] result = generator.generateGraph("source", GraphGenerator.OutputFormat.SVG);

        // Then
        assertThat(result).isEqualTo(image);

        verify(generator).createSourceStringReader(FORMATTED);

        ArgumentCaptor<FileFormatOption> fileFormatOptionArgumentCaptor = ArgumentCaptor.forClass(FileFormatOption.class);
        verify(reader).generateImage(any(OutputStream.class), fileFormatOptionArgumentCaptor.capture());
        assertThat(fileFormatOptionArgumentCaptor.getValue().getFileFormat()).isEqualTo(FileFormat.SVG);
    }

    @Test(expected = RuntimeException.class)
    public void shouldThrowWhenReaderThrows() throws Exception {
        // Given
        when(reader.generateImage(any(OutputStream.class), any(FileFormatOption.class))).thenThrow(IOException.class);

        // When
        generator.generateGraph("source", GraphGenerator.OutputFormat.SVG);

        // Then
        fail();
    }

    @Test(expected = RuntimeException.class)
    public void shouldThrowWhenReaderReturnsNull() throws Exception {
        // Given
        when(reader.generateImage(any(OutputStream.class), any(FileFormatOption.class))).thenAnswer(invocationOnMock -> null);

        // When
        generator.generateGraph("source", GraphGenerator.OutputFormat.SVG);

        // Then
        fail();
    }
}