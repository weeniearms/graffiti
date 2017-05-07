package com.github.weeniearms.graffiti.generator;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class GraphGeneratorFactoryTest {

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private ApplicationContext context;

    @InjectMocks
    private GraphGeneratorFactory factory;

    @Test
    public void shouldCreatePlantUmlGeneratorForPlantUmlSource() {
        // Given
        String source = "@startuml; [Test component]; @enduml";
        PlantUmlGraphGenerator plantUmlGenerator = mock(PlantUmlGraphGenerator.class);
        when(context.getAutowireCapableBeanFactory().autowire(any(Class.class), anyInt(), anyBoolean())).thenReturn(plantUmlGenerator);

        // When
        GraphGenerator generator = factory.create(source);

        // Then
        verify(context.getAutowireCapableBeanFactory()).autowire(PlantUmlGraphGenerator.class, AutowireCapableBeanFactory.AUTOWIRE_CONSTRUCTOR, false);
        assertThat(generator).isEqualTo(plantUmlGenerator);

    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowForUnknownSource() {
        // Given
        String source = "unknown source";

        // When
        factory.create(source);

        // Then
        fail();
    }
}