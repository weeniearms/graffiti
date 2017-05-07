package com.github.weeniearms.graffiti;

import com.github.weeniearms.graffiti.generator.GraphGenerator;
import com.github.weeniearms.graffiti.generator.GraphGeneratorFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.servlet.http.HttpServletRequest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GraphResourceTest {

    private static final String SOURCE = "source";
    private static final byte[] IMAGE = new byte[2];

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private GraphGeneratorFactory factory;

    @Mock
    private HttpServletRequest request;

    @InjectMocks
    private GraphResource graphResource;

    @Before
    public void setUp() {
        when(request.getQueryString()).thenReturn(SOURCE);
        when(factory.create(SOURCE).generateGraph(eq(SOURCE), any(GraphGenerator.OutputFormat.class))).thenReturn(IMAGE);
    }

    @Test
    public void shouldGenerateSvgGraph() throws Exception {
        // When
        byte[] svg = graphResource.svg(request);

        // Then
        assertThat(svg).isEqualTo(IMAGE);
        verify(factory.create(SOURCE)).generateGraph(SOURCE, GraphGenerator.OutputFormat.SVG);
    }

    @Test
    public void shouldGeneratePngGraph() throws Exception {
        // When
        byte[] png = graphResource.png(request);

        // Then
        assertThat(png).isEqualTo(IMAGE);
        verify(factory.create(SOURCE)).generateGraph(SOURCE, GraphGenerator.OutputFormat.PNG);
    }
}