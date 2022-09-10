package challenge;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.awt.image.BufferedImage;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class ColorCounterTest {
    private static final int NUM_COLORS = 16777216;

    private static final int RED = 16711680;
    private static final int GREEN = 65280;
    private static final int BLUE = 255;
    private static final int BLUE_OTHER_ALPHA = -16776961;
    private static final int RED_OTHER_ALPHA = -65536;

    private ColorCounter colorCounter;

    @Mock
    private BufferedImage image;
    @Mock
    private ColorAlphaNormalizer colorAlphaNormalizer;

    @Before
    public void setup() {
        colorCounter = new ColorCounter(colorAlphaNormalizer);

        given(colorAlphaNormalizer.normalizeAlpha(RED)).willReturn(RED);
        given(colorAlphaNormalizer.normalizeAlpha(GREEN)).willReturn(GREEN);
        given(colorAlphaNormalizer.normalizeAlpha(BLUE)).willReturn(BLUE);

        given(colorAlphaNormalizer.normalizeAlpha(BLUE_OTHER_ALPHA)).willReturn(BLUE);
        given(colorAlphaNormalizer.normalizeAlpha(RED_OTHER_ALPHA)).willReturn(RED);
    }

    @Test
    public void givenImage_WhenCountColors_ThenReturnArrayWithCorrectCounts() {
        // Given
        given(image.getWidth()).willReturn(4);
        given(image.getHeight()).willReturn(3);
        given(image.getRGB(0, 0)).willReturn(0);
        given(image.getRGB(0, 1)).willReturn(BLUE);
        given(image.getRGB(0, 2)).willReturn(RED);
        given(image.getRGB(1, 0)).willReturn(GREEN);
        given(image.getRGB(1 ,1)).willReturn(1);
        given(image.getRGB(1, 2)).willReturn(2);
        given(image.getRGB(2, 0)).willReturn(RED);
        given(image.getRGB(2 ,1)).willReturn(BLUE);
        given(image.getRGB(2, 2)).willReturn(BLUE);
        given(image.getRGB(3, 0)).willReturn(3);
        given(image.getRGB(3, 1)).willReturn(4);
        given(image.getRGB(3, 2)).willReturn(5);

        // When
        int[] colorCounts = colorCounter.countColors(image);

        // Then
        assertThat(colorCounts[RED]).isEqualTo(2);
        assertThat(colorCounts[GREEN]).isEqualTo(1);
        assertThat(colorCounts[BLUE]).isEqualTo(3);
        for (int i = 0; i < NUM_COLORS; i++) {
            if (i != RED && i != GREEN && i != BLUE) {
                assertThat(colorCounts[i]).isEqualTo(0);
            }
        }
    }

    @Test
    public void givenImageWithColorsHavingDifferentAlphaValues_WhenCountColors_ThenReturnArrayWithCorrectCounts() {
        // Given
        given(image.getWidth()).willReturn(4);
        given(image.getHeight()).willReturn(3);
        given(image.getRGB(0, 0)).willReturn(0);
        given(image.getRGB(0, 1)).willReturn(BLUE);
        given(image.getRGB(0, 2)).willReturn(RED);
        given(image.getRGB(1, 0)).willReturn(GREEN);
        given(image.getRGB(1 ,1)).willReturn(1);
        given(image.getRGB(1, 2)).willReturn(2);
        given(image.getRGB(2, 0)).willReturn(RED_OTHER_ALPHA);
        given(image.getRGB(2 ,1)).willReturn(BLUE_OTHER_ALPHA);
        given(image.getRGB(2, 2)).willReturn(BLUE_OTHER_ALPHA);
        given(image.getRGB(3, 0)).willReturn(3);
        given(image.getRGB(3, 1)).willReturn(4);
        given(image.getRGB(3, 2)).willReturn(5);

        // When
        int[] colorCounts = colorCounter.countColors(image);

        // Then
        assertThat(colorCounts[RED]).isEqualTo(2);
        assertThat(colorCounts[GREEN]).isEqualTo(1);
        assertThat(colorCounts[BLUE]).isEqualTo(3);
        for (int i = 0; i < NUM_COLORS; i++) {
            if (i != RED && i != GREEN && i != BLUE) {
                assertThat(colorCounts[i]).isEqualTo(0);
            }
        }
    }
}
