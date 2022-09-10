package challenge;

import org.junit.Test;

import static com.google.common.truth.Truth.assertThat;

public class ResultAssemblerTest {
    private static final int NUM_COLORS = 16777216;
    private static final String IMAGE_URL = "an image url";

    private ResultAssembler resultAssembler = new ResultAssembler();

    @Test
    public void givenImageUrlAndColorCountsArray_WhenAssemblingResult_ThenReturnImageUrlAnd3MostPrevalentColors() {
        // Given
        int[] colorCounts = new int[NUM_COLORS];
        colorCounts[0] = 1;
        colorCounts[1] = 0;
        colorCounts[2] = 3;
        colorCounts[3] = 4;
        colorCounts[4] = 9;
        colorCounts[5] = 1;
        colorCounts[6] = 4;
        colorCounts[7] = 1;
        colorCounts[8] = 7;
        colorCounts[9] = 1;
        colorCounts[10] = 3;
        colorCounts[11] = 8;
        colorCounts[12] = 1;

        // When
        ResultDto result = resultAssembler.assembleResult(IMAGE_URL, colorCounts);

        // Then
        assertThat(result.getImageUrl()).isEqualTo(IMAGE_URL);
        assertThat(result.getColor1()).isEqualTo(4);
        assertThat(result.getColor2()).isEqualTo(11);
        assertThat(result.getColor3()).isEqualTo(8);
    }

    @Test
    public void givenImageUrlAndNoColorCountsArray_WhenAssembleResult_ThenReturnImageUrlOnly() {
        // When
        ResultDto result = resultAssembler.assembleResult(IMAGE_URL);

        // Then
        assertThat(result.getImageUrl()).isEqualTo(IMAGE_URL);
        assertThat(result.getColor1()).isNull();
        assertThat(result.getColor2()).isNull();
        assertThat(result.getColor3()).isNull();
    }
}
