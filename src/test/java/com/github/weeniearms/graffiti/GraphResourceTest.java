package com.github.weeniearms.graffiti;

import com.github.weeniearms.graffiti.generator.GraphGenerator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.servlet.http.HttpServletRequest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GraphResourceTest {

    private static final String SOURCE = "source";
    private static final byte[] IMAGE = new byte[2];

    @Mock
    private GraphGenerator generator;

    @Mock
    private HttpServletRequest request;

    @InjectMocks
    private GraphResource graphResource;

    @Before
    public void setUp() {
        when(request.getQueryString()).thenReturn(SOURCE);
        when(generator.generateGraph(eq(SOURCE), any(GraphGenerator.OutputFormat.class))).thenReturn(IMAGE);
        when(generator.isSourceSupported(anyString())).thenReturn(true);

        graphResource = new GraphResource(new GraphGenerator[] { generator });
    }

    @Test
    public void shouldGenerateSvgGraph() throws Exception {
        // When
        byte[] svg = graphResource.svg(request);

        // Then
        assertThat(svg).isEqualTo(IMAGE);
        verify(generator).generateGraph(SOURCE, GraphGenerator.OutputFormat.SVG);
    }

    @Test
    public void shouldGeneratePngGraph() throws Exception {
        // When
        byte[] png = graphResource.png(request);

        // Then
        assertThat(png).isEqualTo(IMAGE);
        verify(generator).generateGraph(SOURCE, GraphGenerator.OutputFormat.PNG);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIfNoMatchingGeneratorFound() throws Exception {
        // Given
        when(generator.isSourceSupported(anyString())).thenReturn(false);

        // When
        graphResource.png(request);

        // Then
        fail();
    }
}