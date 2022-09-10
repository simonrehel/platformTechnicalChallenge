package challenge;

import org.junit.Test;

import static com.google.common.truth.Truth.assertThat;

public class ResultDtoTest {
    @Test
    public void givenResultWithColors_WhenToString_ThenReturnStringWithUrlAndColorHexCodes() {
        // Given
        ResultDto resultDto = new ResultDto("test", 1, 2, 3);

        // When
        String string = resultDto.toString();

        // Then
        assertThat(string).isEqualTo("test,#000001,#000002,#000003");
    }

    @Test
    public void givenResultWithoutColors_WhenToString_ThenReturnStringWithUrlAndErrorMessage() {
        // Given
        ResultDto resultDto = new ResultDto("test");

        // When
        String string = resultDto.toString();

        // Then
        assertThat(string).isEqualTo("test,cannot load image");
    }
}