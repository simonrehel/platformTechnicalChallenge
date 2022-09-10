package challenge;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.concurrent.ConcurrentLinkedQueue;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class ColorExtractorTaskTest {
    private static final String IMAGE_URL = "an image url";
    private static final int[] COLOR_COUNTS = new int[]{1, 2, 3};

    private ColorExtractorTask colorExtractorTask;

    @Mock
    private ConcurrentLinkedQueue<ResultDto> queue;
    @Mock
    private ResultAssembler resultAssembler;
    @Mock
    private ColorCounter colorCounter;
    @Mock
    private ImageLoader imageLoader;
    @Mock
    private ResultDto result;
    @Mock
    private ResultDto emptyResult;
    @Mock
    private BufferedImage image;

    @Before
    public void setup() {
        colorExtractorTask = new ColorExtractorTask(queue, IMAGE_URL, resultAssembler, colorCounter, imageLoader);

        given(colorCounter.countColors(image)).willReturn(COLOR_COUNTS);

        given(resultAssembler.assembleResult(IMAGE_URL, COLOR_COUNTS)).willReturn(result);
        given(resultAssembler.assembleResult(IMAGE_URL)).willReturn(emptyResult);
    }

    @Test
    public void givenImageAvailable_WhenRunningTask_ThenAddResultToQueue() throws IOException {
        // Given
        given(imageLoader.loadImage(any())).willReturn(image);

        // When
        colorExtractorTask.run();

        // Then
        verify(queue).add(result);
    }

    @Test
    public void givenImageNotAvailable_WhenRunningTask_ThenAddEmptyResultToQueue() throws IOException {
        // Given
        given(imageLoader.loadImage(any())).willThrow(IOException.class);

        // When
        colorExtractorTask.run();

        // Then
        verify(queue).add(emptyResult);
    }

}
