package com.github.weeniearms.graffiti;

import com.github.weeniearms.graffiti.generator.GraphGenerator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GraphServiceTest {

    @Mock
    private GraphGenerator generator;

    private GraphService graphService;

    @Before
    public void setUp() {
        graphService = new GraphService(new GraphGenerator[] { generator });
    }

    @Test
    public void shouldGenerateGraph() throws Exception {
        // Given
        String source = "graph {}";
        when(generator.isSourceSupported(anyString())).thenReturn(true);
        byte[] bytes = new byte[2];
        when(generator.generateGraph(anyString(), any(GraphGenerator.OutputFormat.class))).thenReturn(bytes);

        // When
        byte[] png = graphService.generateGraph(source, GraphGenerator.OutputFormat.PNG);

        // Then
        assertThat(png).isEqualTo(bytes);
        verify(generator).generateGraph(source, GraphGenerator.OutputFormat.PNG);
    }


    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIfNoMatchingGeneratorFound() throws Exception {
        // Given
        String source = "graph {}";
        when(generator.isSourceSupported(anyString())).thenReturn(false);

        // When
        graphService.generateGraph(source, GraphGenerator.OutputFormat.PNG);

        // Then
        fail();
    }
}