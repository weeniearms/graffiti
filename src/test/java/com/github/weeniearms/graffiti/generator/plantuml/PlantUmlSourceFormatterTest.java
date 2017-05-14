package com.github.weeniearms.graffiti.generator.plantuml;

import com.github.weeniearms.graffiti.generator.plantuml.PlantUmlSourceFormatter;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PlantUmlSourceFormatterTest {

    private final PlantUmlSourceFormatter formatter = new PlantUmlSourceFormatter();

    @Test
    public void shouldReplaceSemicolonAndSpaceWithNewLine() {
        // Given
        String input = "@startuml; [Test component]; @enduml";

        // When
        String result = formatter.format(input);

        // Then
        assertThat(result).isEqualTo("@startuml\n[Test component]\n@enduml");
    }
}