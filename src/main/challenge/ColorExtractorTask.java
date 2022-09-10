package challenge;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ColorExtractorTask implements Runnable {
    private ConcurrentLinkedQueue<ResultDto> queue;
    private String imageUrl;
    private ResultAssembler resultAssembler;
    private ColorCounter colorCounter;
    private ImageLoader imageLoader;

    public ColorExtractorTask(ConcurrentLinkedQueue<ResultDto> queue,
                              String imageUrl,
                              ResultAssembler resultAssembler,
                              ColorCounter colorCounter,
                              ImageLoader imageLoader) {
        this.queue = queue;
        this.imageUrl = imageUrl;
        this.resultAssembler = resultAssembler;
        this.colorCounter = colorCounter;
        this.imageLoader = imageLoader;
    }

    public void run() {
        try {
            BufferedImage image = imageLoader.loadImage(imageUrl);
            int[] colorCounts = colorCounter.countColors(image);
            queue.add(resultAssembler.assembleResult(imageUrl, colorCounts));
        } catch (IOException e) {
            queue.add(resultAssembler.assembleResult(imageUrl));
        }
    }
}
